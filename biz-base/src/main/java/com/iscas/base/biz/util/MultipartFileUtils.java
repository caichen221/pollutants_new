package com.iscas.base.biz.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Assert;
import com.iscas.common.tools.constant.CharsetConstant;
import com.iscas.common.tools.core.io.file.IoRaiseUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * MultipartFile相关操作工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/3/23 19:33
 * @since jdk1.8
 */
@Slf4j
public class MultipartFileUtils {
    public static int DEFAULT_BUFFER_SIZE = 8192;
    private MultipartFileUtils () {}


    /**
     * 将MultipartFile拷贝入输出流
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param os 输出流
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, OutputStream os) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return IoUtil.copyByNIO(inputStream, os, DEFAULT_BUFFER_SIZE, null);
        } finally {
            IoRaiseUtils.closeAnyway(os);
        }
    }

    /**
     * 将MultipartFile拷贝入文件
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param file 输出文件
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, File file) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        Assert.notNull(file, "输出文件不能为空");
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        @Cleanup OutputStream outputStream = new FileOutputStream(file);
        return IoUtil.copyByNIO(inputStream, outputStream, DEFAULT_BUFFER_SIZE, null);
    }

    /**
     * 将MultipartFile拷贝入文件
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param path 输出文件位置
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, String path) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        Assert.notNull(path, "输出路径不能为空");
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        @Cleanup OutputStream outputStream = new FileOutputStream(path);
        return IoUtil.copyByNIO(inputStream, outputStream, DEFAULT_BUFFER_SIZE, null);
    }

    /**
     * 将MultipartFile拷贝入输出流，带进度
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param os 输出流
     * @param streamProgress 进度
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, OutputStream os, StreamProgress streamProgress) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        try {
            return IoUtil.copyByNIO(inputStream, os, DEFAULT_BUFFER_SIZE, streamProgress);
        } finally {
            IoRaiseUtils.closeAnyway(os);
        }
    }

    /**
     * 将MultipartFile拷贝入文件，带进度条
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param file 输出文件
     * @param streamProgress 进度
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, File file, StreamProgress streamProgress) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        Assert.notNull(file, "输出文件不能为空");
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        @Cleanup OutputStream outputStream = new FileOutputStream(file);
        return IoUtil.copyByNIO(inputStream, outputStream, DEFAULT_BUFFER_SIZE, streamProgress);
    }

    /**
     * 将MultipartFile拷贝入文件，带进度
     * 使用NIO的方式，*注意输入流输出流已经自动关闭
     *
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/23
     * @param multipartFile
     * @param path 输出文件位置
     * @param streamProgress 进度
     * @throws
     * @return long 拷贝的字节数
     */
    public static long copy(MultipartFile multipartFile, String path, StreamProgress streamProgress) throws IOException {
        Assert.notNull(multipartFile, "multipartFile不能为空");
        Assert.notNull(path, "输出路径不能为空");
        @Cleanup InputStream inputStream = multipartFile.getInputStream();
        @Cleanup OutputStream outputStream = new FileOutputStream(path);
        return IoUtil.copyByNIO(inputStream, outputStream, DEFAULT_BUFFER_SIZE, streamProgress);
    }

    /**
     * 将multipartfile 按字符读取出来
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/26
     * @param multipartFile
     * @param charset 编码格式
     * @throws
     * @return java.lang.String
     */
    public static String getDataAsString(MultipartFile multipartFile, String charset) throws IOException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            return new String(inputStream.readAllBytes(), charset);
        }
    }

    /**
     * 将multipartfile 按字符读取出来,默认编码格式
     * @version 1.0
     * @since jdk1.8
     * @date 2019/3/26
     * @param multipartFile
     * @throws
     * @return java.lang.String
     */
    public static String getDataAsString(MultipartFile multipartFile) throws IOException {
        return getDataAsString(multipartFile, CharsetConstant.UTF8);
    }
}
