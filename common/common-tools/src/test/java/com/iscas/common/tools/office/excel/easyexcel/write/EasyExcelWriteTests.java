package com.iscas.common.tools.office.excel.easyexcel.write;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

/**
 *
 * 参考：https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/1/24 12:08
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class EasyExcelWriteTests {

    /**
     * 最简单的写
     */
    @Test
    public void simpleWrite() {
        // 写法1
        String fileName = "d:/demo.xlsx";
        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("toIgnore");
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, EasyExcelDemoData.class).excludeColumnFiledNames(excludeColumnFiledNames).sheet("模板").doWrite(data());

        // 写法2
        // 这里 需要指定写用哪个class去写
        ExcelWriter excelWriter = null;
        try {
            Set<String> includeColumnFiledNames = new HashSet<String>();
            includeColumnFiledNames.add("d");
            includeColumnFiledNames.add("date");
            includeColumnFiledNames.add("str");
            excelWriter = EasyExcel.write(fileName, EasyExcelDemoData.class).includeColumnFiledNames(includeColumnFiledNames).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(data(), writeSheet);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * 复杂表头写入
     * */
    @Test
    public void complexWrite() {
        // 写法1
        String fileName = "d:/demo2.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, EasyExcelComplexHeadData.class).sheet("模板").doWrite(data2());
    }

    //更多高级使用参考：https://github.com/alibaba/easyexcel/blob/master/src/test/java/com/alibaba/easyexcel/test/demo/write/WriteTest.java

    public List<EasyExcelDemoData> data() {
        List<EasyExcelDemoData> data = new ArrayList<>();
        data.add(new EasyExcelDemoData("111", new Date(), 1.2, "xxxweg"));
        return data;
    }

    public List<EasyExcelComplexHeadData> data2() {
        List<EasyExcelComplexHeadData> data = new ArrayList<>();
        data.add(new EasyExcelComplexHeadData("111", new Date(), 1.2));
        return data;
    }

}
