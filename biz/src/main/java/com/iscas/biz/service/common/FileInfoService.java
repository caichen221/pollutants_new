package com.iscas.biz.service.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iscas.biz.domain.common.FileInfo;
import com.iscas.biz.mapper.common.FileInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2022/1/10 15:05
 * @since jdk1.8
 */
@Service
@Slf4j
public class FileInfoService extends ServiceImpl<FileInfoMapper, FileInfo> {
    @Value("${file.server.path}")
    private String fileServerPath;

    /**
     * 保存文件
     * */
    public void saveFile(FileInfo fileInfo) {
        LambdaQueryWrapper<FileInfo> lambdaQuery = new QueryWrapper<FileInfo>().lambda();
        lambdaQuery.eq(FileInfo::getFileKey, fileInfo.getFileKey());
        saveOrUpdate(fileInfo, lambdaQuery);
    }

    /**
     * 检查文件
     * */
    public List<FileInfo> check(String key) {
        LambdaQueryWrapper<FileInfo> lambda = new QueryWrapper<FileInfo>().lambda();
        lambda.eq(FileInfo::getFileKey,key);
        return list(lambda);
    }

    /**
     * 文件上传
     * */
    public void upload(MultipartFile file, String suffix, Integer shardIndex, Integer shardSize,
                         Integer shardTotal, Integer size, String key, String name) throws IOException, InterruptedException {
        log.info("上传文件开始");
        File pFile = new File(fileServerPath, "files");
        if (!pFile.exists()) {
            pFile.mkdirs();
        }

        //设置文件新的名字
        // course\6sfSqfOwzmik4A4icMYuUe.mp4
        String fileName = new StringBuffer().append(key).append(".").append(suffix).toString();
        //这个是分片的名字
        String localfileName = new StringBuffer(fileName)
                .append(".")
                .append(shardIndex)
                .toString(); // course\6sfSqfOwzmik4A4icMYuUe.mp4.1

        // 以绝对路径保存重名命后的文件
        File targeFile = new File(pFile, localfileName);
        //上传这个文件
        file.transferTo(targeFile);
        //数据库持久化这个数据
        LocalDateTime now = LocalDateTime.now();
        FileInfo file1 = new FileInfo();
        file1.setPath("files" + File.separator + localfileName)
                .setName(name)
                .setSuffix(suffix)
                .setSize(size)
                .setCreatedAt(now)
                .setUpdatedAt(now)
                .setShardIndex(shardIndex)
                .setShardSize(shardSize)
                .setShardTotal(shardTotal)
                .setFileKey(key);

        //判断当前是不是最后一个分页 如果不是就继续等待其他分页 合并分页
        if (shardIndex.equals(shardTotal)) {
            file1.setPath("files" + File.separator + fileName);
            this.merge(file1, name);
            file1.setPath("files" + File.separator + name);
        }
        //插入到数据库中
        //保存的时候 去处理一下 这个逻辑
        saveFile(file1);
        log.info("上传成功");
    }

    /**
     * 合并文件
     * */
    private void merge(FileInfo fileInfo, String oriFileName) throws IOException, InterruptedException {
        //合并分片开始
        log.info("分片合并开始");
        //获取到的路径 没有.1 .2 这样的东西
        String path = fileInfo.getPath();
        Integer shardTotal= fileInfo.getShardTotal();
        File newFile = new File(fileServerPath + File.separator + "files" + File.separator + oriFileName);
        // 文件追加写入
        FileOutputStream outputStream = new FileOutputStream(newFile,true);
        //分片文件
        FileInputStream fileInputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片
                // course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                fileInputStream = new FileInputStream(fileServerPath + File.separator + path + "." + (i + 1));
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
                fileInputStream.close();
            }
        } catch (IOException e) {
            log.error("分片合并异常", e);
            throw e;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                log.info("IO流关闭");
            } catch (Exception e) {
                log.error("IO流关闭异常", e);
            }
        }
        log.debug("分片结束了");

        log.debug("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = fileServerPath + File.separator + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            log.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        log.info("删除分片结束");
    }

}
