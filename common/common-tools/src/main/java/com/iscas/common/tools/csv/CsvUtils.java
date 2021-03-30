package com.iscas.common.tools.csv;
import java.util.LinkedHashMap;
import com.google.common.collect.Lists;

import cn.hutool.core.text.csv.*;
import lombok.Cleanup;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Csv读写工具类
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/3/24 10:04
 * @since jdk1.8
 */
public class CsvUtils {
    private CsvUtils() {}

    /**
     * Csv结果类bean
     * */
    public static class CsvResult<T>{
        /**表头键值对 key : en ; value :ch*/
        private LinkedHashMap<String,String> header;
        /**Excel数据*/
        private List<T> content;

        public LinkedHashMap<String, String> getHeader() {
            return header;
        }

        public void setHeader(LinkedHashMap<String, String> header) {
            this.header = header;
        }

        public List<T> getContent() {
            return content;
        }

        public void setContent(List<T> content) {
            this.content = content;
        }
    }


    /**
     * 写入csv
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param filePath 文件路径
     * @param fieldSeparator 列分隔符
     * @param csvResult 待写入的数据
     * @param withHeader 是否写入表头(第一行)
     * @param charset 编码格式
     * @throws
     * @return void
     */
    public static void writeCsv(String filePath, char fieldSeparator, CsvResult csvResult, boolean withHeader, String charset) throws IOException {
        File file = new File(filePath);
        writeCsv(file, fieldSeparator, csvResult, withHeader, charset);
    }



    /**
     * 写入csv
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param file 文件
     * @param fieldSeparator 列分隔符
     * @param csvResult 待写入的数据
     * @param withHeader 是否写入表头(第一行)
     * @param charset 编码格式
     * @throws
     * @return void
     */
    public static void writeCsv(File file, char fieldSeparator, CsvResult csvResult, boolean withHeader, String charset) throws IOException {
        @Cleanup OutputStream os = new FileOutputStream(file);
        @Cleanup OutputStreamWriter osw = new OutputStreamWriter(os, charset);
        writeCsv(osw, fieldSeparator, csvResult, withHeader);
    }

    /**
     * 写入csv
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param writer writer对象
     * @param fieldSeparator 列分隔符
     * @param csvResult 待写入的数据
     * @param withHeader 是否写入表头(第一行)
     * @throws
     * @return void
     */
    public static void writeCsv(Writer writer, char fieldSeparator, CsvResult csvResult, boolean withHeader) {
        CsvWriteConfig csvWriteConfig = new CsvWriteConfig();
        csvWriteConfig.setFieldSeparator(fieldSeparator);
        @Cleanup CsvWriter csvWriter = new CsvWriter(writer, csvWriteConfig);
        LinkedHashMap<String, String> header = csvResult.getHeader();
        if (withHeader) {
            String[] headerLine = header.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .toArray(String[]::new);
            csvWriter.write(headerLine);
        }
        List content = csvResult.getContent();
        String[][] contentArray = (String[][]) content.stream().map(t ->
            header.entrySet().stream()
                    .map(entry -> {
                        Object o = null;
                        if (t instanceof Map) {
                            o = ((Map) t).get(entry.getKey());
                        } else {
                            //如果是Java对象，利用反射
                            PropertyDescriptor pd = null;
                            try {
                                pd = new PropertyDescriptor(entry.getKey(), t.getClass());
                                Method getMethod = pd.getReadMethod();//获得get方法
                                o = getMethod.invoke(t);//执行get方法返回一个Object
                            } catch (Exception e) {
                                throw new RuntimeException("获取java对象数据的值出错", e);
                            }
                        }
                        return o == null ? "" : o.toString();
                    }).toArray(String[]::new)
        ).toArray(String[][]::new);
        csvWriter.write(contentArray);
    }

    /**
     * 读取CSV文件
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param reader
     * @param fieldSeparator 分隔符
     * @throws
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public static List<Map<String, Object>> readCsvWithHeader(Reader reader, char fieldSeparator) {
        CsvReadConfig csvReadConfig = new CsvReadConfig();
        csvReadConfig.setFieldSeparator(fieldSeparator);
        CsvReader csvReader = new CsvReader(csvReadConfig);
        CsvData csvData = csvReader.read(reader);
        List<String> header = csvData.getRow(0);
        Optional<List<Map<String, Object>>> optionalMaps = Optional.ofNullable(csvData.getRows())
                .map(row -> row.stream()
                        .skip(1)
                        .map(csvRow -> {
                            Map<String, Object> dataMap = new HashMap<>();
                            int[] i = new int[1];
                            header.stream().forEach(h -> {
                                dataMap.put(h, csvRow.get(i[0]++));
                            });
                            return dataMap;
                        }).collect(Collectors.toList())
                );
        return optionalMaps.isPresent() ? optionalMaps.get() : null;
    }

    /**
     * 读取CSV文件，无表头
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param reader
     * @param fieldSeparator 分隔符
     * @throws
     * @return java.util.List<java.util.List<java.lang.String>>
     */
    public static List<List<String>> readCsv(Reader reader, char fieldSeparator) {
        CsvReadConfig csvReadConfig = new CsvReadConfig();
        csvReadConfig.setFieldSeparator(fieldSeparator);
        CsvReader csvReader = new CsvReader(csvReadConfig);
        CsvData csvData = csvReader.read(reader);
        Optional<List<List<String>>> optional = Optional.ofNullable(csvData.getRows())
                .map(vd -> vd.stream()
                        .map(csvRow -> csvRow.getRawList())
                        .collect(Collectors.toList())
                );
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 读取CSV文件，无表头
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param file 文件
     * @param fieldSeparator 分隔符
     * @param charset 编码格式
     * @throws
     * @return java.util.List<java.util.List<java.lang.String>>
     */
    public static List<List<String>> readCsv(File file, char fieldSeparator, String charset) throws IOException {
        @Cleanup InputStream is = new FileInputStream(file);
        @Cleanup InputStreamReader isr = new InputStreamReader(is, charset);
        return readCsv(isr, fieldSeparator);
    }

    /**
     * 读取CSV文件，无表头
     * @version 1.0
     * @since jdk1.8
     * @date 2021/3/24
     * @param path 文件路径
     * @param fieldSeparator 分隔符
     * @param charset 编码格式
     * @throws
     * @return java.util.List<java.util.List<java.lang.String>>
     */
    public static List<List<String>> readCsv(String path, char fieldSeparator, String charset) throws IOException {
        File file = new File(path);
        return readCsv(file, fieldSeparator, charset);
    }

}
