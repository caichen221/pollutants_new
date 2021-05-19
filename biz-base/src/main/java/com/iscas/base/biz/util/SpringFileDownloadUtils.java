package com.iscas.base.biz.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 使用spring的方式下载文件
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/5/19 21:25
 * @since jdk1.8
 */
public class SpringFileDownloadUtils {
    private SpringFileDownloadUtils() {}

    /**
     * 根据输入流和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param is 输入流
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, InputStream is) {
        InputStreamResource isr = new InputStreamResource(is);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=".concat(fileName))
                .body(isr);
    }

    /**
     * 根据文件和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param file 文件
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, File file) throws FileNotFoundException {
        return download(fileName, new FileInputStream(file));
    }


    /**
     * 根据文件路径和文件名下载文件， 接口的返回值需要为ResponseEntity<InputStreamResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param filePath 文件路径
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.InputStreamResource>
     */
    public static ResponseEntity<InputStreamResource> download(String fileName, String filePath) throws FileNotFoundException {
        return download(fileName, new FileInputStream(filePath));
    }

    /**
     * 根据字节数组和文件名下载文件， 接口的返回值需要为ResponseEntity<ByteArrayResource>
     * @version 1.0
     * @since jdk1.8
     * @date 2021/5/19
     * @param fileName 文件名
     * @param bytes 字节数组
     * @throws
     * @return org.springframework.http.ResponseEntity<cn.hutool.core.io.resource.ByteArrayResource>
     */
    public static ResponseEntity<ByteArrayResource> download(String fileName, byte[] bytes) throws FileNotFoundException {
        ByteArrayResource bar = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=".concat(fileName))
                .body(bar);
    }

}
