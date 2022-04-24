package com.iscas.common.tools.csv;

import cn.hutool.core.map.MapUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhuquanwen
 * @version 1.0
 * @date 2021/3/24 11:01
 * @since jdk1.8
 */
public class CsvUtilsTests {

    @Test
    public void testWrite() throws IOException {
        File file = writeCsv();
        file.delete();
    }

    @Test
    public void testRead1() throws IOException {
        File file = writeCsv();
        @Cleanup InputStream is =  new FileInputStream(file);
        @Cleanup InputStreamReader fr = new InputStreamReader(is, "utf-8");
        List<Map<String, Object>> maps = CsvUtils.readCsvWithHeader(fr, ' ');
        maps.stream().forEach(System.out::println);
        file.delete();
    }

    @Test
    public void testRead2() throws IOException {

        File file = writeCsv();
        @Cleanup InputStream is =  new FileInputStream(file);
        @Cleanup InputStreamReader fr = new InputStreamReader(is, "utf-8");
        List<List<String>> lists = CsvUtils.readCsv(fr, ' ');
        lists.stream().forEach(System.out::println);
        file.delete();
    }

    private File writeCsv() throws IOException {
        CsvUtils.CsvResult<Map<Object, Object>> csvResult = new CsvUtils.CsvResult<>();
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("a", "欸");
        header.put("b", "必");
        header.put("c", "西");

        List<Map<Object, Object>> maps = Arrays.asList(MapUtil.builder().put("a", "hahaha")
                        .put("b", "lalala")
                        .put("c", "哈哈哈").build(),
                MapUtil.builder().put("a", "一年又一年")
                        .put("b", "啦啦啦啦")
                        .put("c", "测试测试").build());
        csvResult.setHeader(header);
        csvResult.setContent(maps);
        File file = File.createTempFile("test", ".csv");
        file.deleteOnExit();
        @Cleanup FileOutputStream fileOutputStream = new FileOutputStream(file);
        @Cleanup OutputStreamWriter osw = new OutputStreamWriter(fileOutputStream);
        CsvUtils.writeCsv(osw, ' ', csvResult, true);
        return file;
    }
}
