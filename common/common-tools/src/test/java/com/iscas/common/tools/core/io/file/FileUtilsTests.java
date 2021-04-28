package com.iscas.common.tools.core.io.file;


import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

/**
 * <b>添加了一部分单元测试 <b/>
 * @author zhuquanwen
 * @date: 2018/7/16
 **/
public class FileUtilsTests {

    @Test
    public void test() throws IOException {
        File file = File.createTempFile("test", ".txt");
        @Cleanup PrintWriter pw = new PrintWriter(file);
        pw.println("1111");
        pw.println("222");
        pw.close();
        @Cleanup InputStream is = new FileInputStream(file);
        @Cleanup InputStreamReader isr = new InputStreamReader(is);
        List<String> res = FileUtils.readLines(isr);
        res.forEach(System.out::println);
        file.delete();
    }

    @Test
    public void test2() throws IOException {
        File file = File.createTempFile("test", ".txt");
        @Cleanup PrintWriter pw = new PrintWriter(file);
        pw.println("1111");
        pw.println("222");
        pw.println("222");
        pw.println("222");
        pw.close();
        @Cleanup InputStream is = new FileInputStream(file);
        @Cleanup InputStreamReader isr = new InputStreamReader(is);
        List<String> res = FileUtils.readLines(isr, 2);
        res.forEach(System.out::println);
        file.delete();
    }

    @Test
    public void test3() throws IOException {
        File file = File.createTempFile("test", ".txt");
        @Cleanup PrintWriter pw = new PrintWriter(file);
        pw.println("1111");
        pw.println("222");
        pw.println("222");
        pw.println("222");
        pw.close();
        List<String> res = FileUtils.reverseReadLines(file, "utf-8", 2);
        res.forEach(System.out::println);
        file.delete();
    }

}
