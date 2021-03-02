package com.iscas.biz.util;

import com.iscas.biz.model.monitor.jvm.JvmMemory;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.util.List;
import java.util.Optional;

public class JVMMemoryUtils {
    static private MemoryMXBean memoryMXBean;
    static private MemoryPoolMXBean edenSpaceMxBean;
    static private MemoryPoolMXBean survivorSpaceMxBean;
    static private MemoryPoolMXBean oldGenMxBean;
    static private MemoryPoolMXBean permGenMxBean;
    static private MemoryPoolMXBean codeCacheMxBean;

    static {
        memoryMXBean = ManagementFactory.getMemoryMXBean();

        List<MemoryPoolMXBean> memoryPoolMXBeanList = ManagementFactory.getMemoryPoolMXBeans();
        Optional.ofNullable(memoryPoolMXBeanList)
                .filter(mxBeans -> mxBeans.size() > 0)
                .ifPresent(mxBeans -> mxBeans.stream()
                        .filter(mxBean -> StringUtils.isNotBlank(mxBean.getName()))
                        .forEach(mxBean -> {
                            String poolName = mxBean.getName();
                            // 官方JVM(HotSpot)提供的MemoryPoolMXBean
                            // JDK1.7/1.8 Eden区内存池名称： "Eden Space" 或  "PS Eden Space"、 “G1 Eden Space”(和垃圾收集器有关)
                            // JDK1.7/1.8 Survivor区内存池名称："Survivor Space" 或 "PS Survivor Space"、“G1 Survivor Space”(和垃圾收集器有关)
                            // JDK1.7  老区内存池名称： "Tenured Gen"
                            // JDK1.8  老区内存池名称："Old Gen" 或 "PS Old Gen"、“G1 Old Gen”(和垃圾收集器有关)
                            // JDK1.7  方法/永久区内存池名称： "Perm Gen" 或 "PS Perm Gen"(和垃圾收集器有关)
                            // JDK1.8  方法/永久区内存池名称："Metaspace"(注意：不在堆内存中)
                            // JDK1.7/1.8  CodeCache区内存池名称： "Code Cache"
                            //TODO JDK11 等的适配
                            if (edenSpaceMxBean == null && poolName.endsWith("Eden Space")) {
                                edenSpaceMxBean = mxBean;
                            } else if (survivorSpaceMxBean == null && poolName.endsWith("Survivor Space")) {
                                survivorSpaceMxBean = mxBean;
                            } else if (oldGenMxBean == null && (poolName.endsWith("Tenured Gen") || poolName.endsWith("Old Gen"))) {
                                oldGenMxBean = mxBean;
                            } else if (permGenMxBean == null && (poolName.endsWith("Perm Gen") || poolName.endsWith("Metaspace"))) {
                                permGenMxBean = mxBean;
                            } else if (codeCacheMxBean == null && poolName.endsWith("Code Cache")) {
                                codeCacheMxBean = mxBean;
                            }
                        }));
    }


    /**
     * 获取堆内存情况
     */
    static public JvmMemory getHeapMemoryUsage() {
        return Optional.of(memoryMXBean)
                .map(MemoryMXBean::getHeapMemoryUsage)
                .map(usage -> new JvmMemory(usage))
                .orElse(new JvmMemory());
    }

    /**
     * 获取堆外内存情况
     */
    static public JvmMemory getNonHeapMemoryUsage() {
        return Optional.of(memoryMXBean)
                .map(MemoryMXBean::getNonHeapMemoryUsage)
                .map(usage -> new JvmMemory(usage))
                .orElse(new JvmMemory());
    }

    /**
     * 获取Eden区内存情况
     */
    static public JvmMemory getEdenSpaceMemoryUsage() {
        return getMemoryPoolUsage(edenSpaceMxBean);
    }

    /**
     * 获取Eden区内存峰值（从启动或上一次重置开始统计），并重置
     */
    static public JvmMemory getAndResetEdenSpaceMemoryPeakUsage() {
        return getAndResetMemoryPoolPeakUsage(edenSpaceMxBean);
    }

    /**
     * 获取Survivor区内存情况
     */
    static public JvmMemory getSurvivorSpaceMemoryUsage() {
        return getMemoryPoolUsage(survivorSpaceMxBean);
    }

    /**
     * 获取Survivor区内存峰值（从启动或上一次重置开始统计），并重置
     */
    static public JvmMemory getAndResetSurvivorSpaceMemoryPeakUsage() {
        return getAndResetMemoryPoolPeakUsage(survivorSpaceMxBean);
    }

    /**
     * 获取老区内存情况
     */
    static public JvmMemory getOldGenMemoryUsage() {
        return getMemoryPoolUsage(oldGenMxBean);
    }

    /**
     * 获取老区内存峰值（从启动或上一次重置开始统计），并重置
     */
    static public JvmMemory getAndResetOldGenMemoryPeakUsage() {
        return getAndResetMemoryPoolPeakUsage(oldGenMxBean);
    }

    /**
     * 获取永久区/方法区内存情况
     */
    static public JvmMemory getPermGenMemoryUsage() {
        return getMemoryPoolUsage(permGenMxBean);
    }

    /**
     * 获取永久区/方法区内存峰值（从启动或上一次重置开始统计），并重置
     */
    static public JvmMemory getAndResetPermGenMemoryPeakUsage() {
        return getAndResetMemoryPoolPeakUsage(permGenMxBean);
    }

    /**
     * 获取CodeCache区内存情况
     */
    static public JvmMemory getCodeCacheMemoryUsage() {
        return getMemoryPoolUsage(codeCacheMxBean);
    }

    /**
     * 获取CodeCache区内存峰值（从启动或上一次重置开始统计），并重置
     */
    static public JvmMemory getAndResetCodeCacheMemoryPeakUsage() {
        return getAndResetMemoryPoolPeakUsage(codeCacheMxBean);
    }

    static private JvmMemory getMemoryPoolUsage(MemoryPoolMXBean memoryPoolMXBean) {
        return Optional.of(memoryPoolMXBean)
                .map(MemoryPoolMXBean::getUsage)
                .map(usage -> new JvmMemory(usage))
                .orElse(new JvmMemory());
    }

    static private JvmMemory getAndResetMemoryPoolPeakUsage(MemoryPoolMXBean memoryPoolMXBean) {
        return Optional.ofNullable(memoryPoolMXBean)
                .map(MemoryPoolMXBean::getPeakUsage)
                .map(usage -> {
                    memoryPoolMXBean.resetPeakUsage();
                    return new JvmMemory(usage);
                })
                .orElse(new JvmMemory());
    }
}
