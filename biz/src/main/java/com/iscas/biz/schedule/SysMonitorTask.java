package com.iscas.biz.schedule;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.iscas.biz.model.monitor.sys.*;
import com.iscas.biz.service.MonitorService;
import com.iscas.biz.util.MathUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/1/18 14:15
 * @since jdk1.8
 */
@Component
public class SysMonitorTask {
    private static final String winPath = "C:\\Users\\x\\Desktop\\clientInfo.txt";
    private static final String linuxPath = "/opt/tmp/clientInfo.txt";
    //上次收集数据时间
    public static volatile Date lastCollectTime;
    //上次收集的部分需要的数据缓存
    public static volatile Map lastCollectData = new HashMap();
    //是否是首次上报采集数据
    public static volatile boolean isFirst = true;

    @Autowired
    private MonitorService monitorService;

    /**
     * 定时采集的任务
     */
    public void monitor() {

        //计算统计时间间隔
        long range = getRange();
        //采集数据
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        CentralProcessor processor = hardware.getProcessor();
        GlobalMemory globalMemory = hardware.getMemory();
        List<NetworkIF> networkIFs = hardware.getNetworkIFs();
        //解析数据，保留数据详情，后期可以需要
        Cpu cpu = getCpu(processor, range);
        Memory memory = getMemory(globalMemory);
        List<Disk> disks = getDisk(operatingSystem);
        List<DiskStore> diskStores = getDiskStore(hardware, range);
        List<NetworkInterface> networkInterfaces = getNetworkInterfaces(networkIFs, range);

        if (range <= 0L) {
            return;
        }

        //****要缓存的数据汇总****
        Map<Class, Object> data = new ConcurrentHashMap<>();
        Map<Class, Object> fixData = new ConcurrentHashMap<>();

        //构建需要上报的系统监控数据
        SysMonitor sysMonitor = buildSysMonitor(cpu, memory, disks, diskStores, networkInterfaces);
        //系统监控的动态数据
        data.put(SysMonitor.class, sysMonitor);

        //JVM的监控数据

        if (isFirst) {
            //一些只需要启动后初始化一次的数据，存入内存缓存
            isFirst = false;
            SysFixMonitor sysFixMonitor = buildFixSysMonitor(cpu, memory, disks);
            fixData.put(SysFixMonitor.class, sysFixMonitor);
        }



        //将采集数据存入缓存
        monitorService.saveData(data, fixData);
    }

    private SysFixMonitor buildFixSysMonitor(Cpu cpu, Memory memory, List<Disk> disks) {
        SysFixMonitor sysFixMonitor = new SysFixMonitor();
        Long totalDisk = disks.stream().collect(Collectors.summingLong(Disk::getTotal));

        sysFixMonitor.setProcessorCount(cpu.getProcessorCount())
                .setTotalMemory(convertFileSizeGB(memory.getTotal(), "B"))
                .setTotalDisk(convertFileSizeGB(totalDisk, "B"));

        return sysFixMonitor;
    }

    /**
     * 采集任务执行时间间隔
     */
    private long getRange() {
        long range = 0L;
        Date currentTime = new Date();
        if (lastCollectTime != null) {
            range = (currentTime.getTime() - lastCollectTime.getTime()) / 1000;
        }
        lastCollectTime = currentTime;
        return range;
    }

    private SysMonitor buildSysMonitor(Cpu cpu, Memory memory, List<Disk> disks, List<DiskStore> diskStores, List<NetworkInterface> networkInterfaces) {
//        recordToFile("cpu",cpu);
//        recordToFile("memory",memory);
//        recordToFile("disks",disks);
//        recordToFile("diskStore",diskStores);
//        recordToFile("networkInterfaces",networkInterfaces);

        SysMonitor sysMonitor = new SysMonitor();
        //磁盘 计算平均值
        String[] diskUseRateAvg = calculateDiskUseRateAvg(disks);
        //磁盘吞吐 计算和
        Long[] diskStoreSum = calculateDiskStoreSum(diskStores);
        //网络 计算和
        Long[] networkInterfaceBytes = calculateNetworkInterface(networkInterfaces);

        sysMonitor.setCollectTime(lastCollectTime)
                .setSystemCpuLoad(cpu.getSystemCpuLoad())
                .setProcessorCpuLoad(cpu.getProcessorCpuLoad())
                .setMemoryUseRate(memory.getMemoryUseRate())
                .setSpaceUseRate(diskUseRateAvg[0])
                .setInodesUseRate(diskUseRateAvg[1])
                .setIops(diskStoreSum[0])
                .setThrought(convertFileSizeMB(diskStoreSum[1], "b"))
                .setNetworkRecv(convertFileSizeKB(networkInterfaceBytes[0], "bps"))
                .setNetworkSend(convertFileSizeKB(networkInterfaceBytes[1], "bps"));

        return sysMonitor;
    }

    private Long[] calculateNetworkInterface(List<NetworkInterface> networkInterfaces) {
        //long[0] recv , long[1] send
        return networkInterfaces.stream().map(networkInterface -> new Long[]{networkInterface.getBytesRecv(), networkInterface.getBytesSent()}).reduce(new Long[]{0L, 0L}, accumulatorLongSum);
    }

    private Long[] calculateDiskStoreSum(List<DiskStore> diskStores) {
        //每个disk累加 Long[0]累加读写次数iops，Long[1]累加读写字节数Throught
        return diskStores.stream().map(diskStore -> new Long[]{diskStore.getIops(), diskStore.getThrought()}).reduce(new Long[]{0L, 0L}, accumulatorLongSum);
    }

    private String[] calculateDiskUseRateAvg(List<Disk> disks) {
        //String[0] 空间使用率平均值，String[1] inodes使用率平均值
        return disks.stream().map(disk -> new Long[]{disk.getTotal(), disk.getUsed(), disk.getTotalInodes(), disk.getUsedInodes()}
        ).collect(Collectors.collectingAndThen(Collectors.reducing(new Long[]{0L, 0L, 0L, 0L}, accumulatorLongSum), r -> {
            String surPercent = MathUtils.double2Percent((double) r[1] / r[0], 2);
            String isrPercent = MathUtils.double2Percent((double) r[3] / r[2], 2);
            return new String[]{surPercent, isrPercent};
        }));
    }

    /**
     * CPU信息
     */
    private Cpu getCpu(CentralProcessor processor, long range) {
        //cpu使用情况 /proc/stat
        Cpu cpu = new Cpu();
        long[] prevTicks = (long[]) lastCollectData.get("systemCpuLoadTicks");
        long[][] preProcessorCpuLoadTicks = (long[][]) lastCollectData.get("processorCpuLoadTicks");
        long[] ticks = processor.getSystemCpuLoadTicks();
        lastCollectData.put("systemCpuLoadTicks", ticks);
        lastCollectData.put("processorCpuLoadTicks", processor.getProcessorCpuLoadTicks());
        if (ArrayUtils.isEmpty(prevTicks)) {
            return null;
        }
        //系统cpu
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long system = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + system + idle + iowait + irq + softirq + steal;

        double systemCpuLoad = (double) (totalCpu - idle - iowait) / totalCpu;
        String scl = MathUtils.double2Percent(systemCpuLoad, 2);

        // 核心cpu0 cpu1
        double[] processorCpuLoadBetweenTicks = processor.getProcessorCpuLoadBetweenTicks(preProcessorCpuLoadTicks);
        OptionalDouble average = Arrays.stream(processorCpuLoadBetweenTicks).average();
        double processorCpuLoad = average.orElse(0.0);
        String pcl = MathUtils.double2Percent(processorCpuLoad, 2);

        return cpu.setProcessorCount(processor.getLogicalProcessorCount())
                .setTotal(totalCpu / range)
                .setUser(user / range)
                .setNice(nice / range)
                .setSystem(system / range)
                .setIdle(idle / range)
                .setIowait(iowait / range)
                .setIrq(irq / range)
                .setSoftirq(softirq / range)
                .setSteal(steal / range)
                .setSystemCpuLoad(scl)
                .setProcessorCpuLoad(pcl);
    }

    /**
     * Memory信息
     */
    private Memory getMemory(GlobalMemory globalMemory) {
        Memory memory = new Memory();

        long total = globalMemory.getTotal();
        long free = globalMemory.getAvailable();
        long used = total - free;

        return memory.setTotal(total)
                .setFree(free)
                .setUsed(used)
                .setMemoryUseRate(MathUtils.double2Percent((double) used / total, 2));
    }


    /**
     * Disk信息
     */
    private List<Disk> getDisk(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        return fileStores.stream().map(fs -> {
            //space
            long freeSpace = fs.getUsableSpace();
            long totalSpace = fs.getTotalSpace();
            long usedSpace = totalSpace - freeSpace;
            //inodes
            long totalInodes = fs.getTotalInodes();
            long freeInodes = fs.getFreeInodes();
            long usedInodes = totalInodes - freeInodes;
            //disk
            Disk disk = new Disk();
            return disk.setFileSystem(fs.getType())
                    .setType(fs.getName())
                    .setTotal(totalSpace)
                    .setUsed(usedSpace)
                    .setFree(freeSpace)
                    .setSpaceUseRate(MathUtils.double2Percent((double) usedSpace / totalSpace, 2))
                    .setMountedOn(fs.getMount())
                    .setTotalInodes(totalInodes)
                    .setFreeInodes(freeInodes)
                    .setUsedInodes(usedInodes)
                    .setInodesUseRate(MathUtils.double2Percent((double) usedInodes / totalInodes, 2));
        }).collect(Collectors.toList());

    }

    /**
     * 测试时记录日志
     */
    private void recordToFile(String key, Object obj) {
        PlatformEnum platform = SystemInfo.getCurrentPlatformEnum();
        String filePath = linuxPath;
        switch (platform) {
            case LINUX:
                break;
            case WINDOWS:
                filePath = winPath;
                break;
        }
        FileUtil.appendUtf8String(key + ": " + JSONUtil.toJsonStr(obj), filePath);
    }

    public String convertFileSizeKB(long size, String unit) {
        long kb = 1024;
        float f = (float) size / kb;
        return String.format("%.0f K" + unit, f);
    }

    public String convertFileSizeMB(long size, String unit) {
        long kb = 1024;
        long mb = kb * 1024;
        float f = (float) size / mb;
        return String.format("%.1f M" + unit, f);
    }

    public String convertFileSizeGB(long size, String unit) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        float f = (float) size / gb;
        return String.format("%.1f G" + unit, f);
    }

    /**
     * 字节转换
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    private static List<NetworkInterface> getNetworkInterfaces(List<NetworkIF> networkIFs, long range) {
        List<NetworkIF> preNetworkIFs = (List<NetworkIF>) lastCollectData.get("networkInterface");
        lastCollectData.put("networkInterface", networkIFs);
        if (CollectionUtils.isEmpty(preNetworkIFs)) {
            return null;
        }
        Map<String, List<NetworkIF>> preNetworkIFsMap = preNetworkIFs.stream().collect(Collectors.groupingBy(networkIF -> networkIF.getName()));
        Map<String, List<NetworkIF>> networkIFsMap = networkIFs.stream().collect(Collectors.groupingBy(networkIF -> networkIF.getName()));

        return networkIFsMap.keySet().stream().map(name -> {
            NetworkInterface networkInterface = new NetworkInterface();
            NetworkIF network = networkIFsMap.get(name).get(0);
            long bytesRecv = network.getBytesRecv();
            long bytesSent = network.getBytesSent();
            long packetsRecv = network.getPacketsRecv();
            long packetsSent = network.getPacketsSent();
            if (preNetworkIFsMap.containsKey(name)) {
                NetworkIF preNetwork = preNetworkIFsMap.get(name).get(0);
                bytesRecv -= preNetwork.getBytesRecv();
                bytesSent -= preNetwork.getBytesSent();
                packetsRecv -= preNetwork.getPacketsRecv();
                packetsSent -= preNetwork.getPacketsSent();
            }
            return networkInterface.setBytesRecv(bytesRecv / range)
                    .setBytesSent(bytesSent / range)
                    .setPacketsRecv(packetsRecv / range)
                    .setPacketsSent(packetsSent / range)
                    .setName(network.getName())
                    .setDisplayName(network.getDisplayName())
                    .setMtu(network.getMTU())
                    .setMac(network.getMacaddr())
                    .setIpv4(network.getIPv4addr())
                    .setSubnetMasks(network.getSubnetMasks())
                    .setIpv6(network.getIPv6addr());
        }).collect(Collectors.toList());

    }

    /**
     * IOPS和数据吞吐量
     */
    private List<DiskStore> getDiskStore(HardwareAbstractionLayer hardware, long range) {
        List<HWDiskStore> preHwDiskStores = (List<HWDiskStore>) lastCollectData.get("diskStore");
        List<HWDiskStore> hwDiskStores = hardware.getDiskStores();
        lastCollectData.put("diskStore", hwDiskStores);
        if (CollectionUtils.isEmpty(preHwDiskStores)) {
            return null;
        }
        return IntStream.range(0, hwDiskStores.size()).mapToObj(i -> {
            DiskStore diskStore = new DiskStore();
            HWDiskStore hwDiskStore = hwDiskStores.get(i);
            HWDiskStore preHwDiskStore = preHwDiskStores.get(i);

            long readBytes = (hwDiskStore.getReadBytes() - preHwDiskStore.getReadBytes()) / range;
            long writeBytes = (hwDiskStore.getWriteBytes() - preHwDiskStore.getWriteBytes()) / range;
            long reads = (hwDiskStore.getReads() - preHwDiskStore.getReads()) / range;
            long writes = (hwDiskStore.getWrites() - preHwDiskStore.getWrites()) / range;
            return diskStore.setName(hwDiskStore.getName())
                    .setReadBytes(readBytes)
                    .setWriteBytes(writeBytes)
                    .setReads(reads)
                    .setWrites(writes)
                    .setIops(reads + writes)
                    .setThrought(readBytes + writeBytes);
        }).collect(Collectors.toList());
    }


    /**
     * 计算每个数组对应项的和
     */
    BinaryOperator<Long[]> accumulatorLongSum = (t0, t1) -> {
        IntStream.range(0, t0.length).forEach(i -> t0[i] = t0[i] + t1[i]);
        return t0;
    };
}
