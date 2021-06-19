package com.iscas.common.tools.office.word.poitl;

import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.excel.EasyExcel;
import com.deepoove.poi.XWPFTemplate;
import com.iscas.common.tools.office.excel.easyexcel.read.EasyExcelDemoListener;
import com.iscas.common.tools.office.excel.easyexcel.read.EasyExcelDemoVO;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/16 14:55
 * @since jdk1.8
 */
public class PoiTlSimpleTests {

    /**
     * 最简单的DEMO
     * */
    @Test
    public void testSimple() throws IOException {
        ClassPathResource pathResource = new ClassPathResource("poitl/word-simple.xlsx");
        XWPFTemplate template = XWPFTemplate.compile(pathResource.getStream()).render(
                new HashMap<String, Object>(){{
                    put("title", "Hi, Simple Word Test");
                }});
        File tmpFile = File.createTempFile("word-simple", ".docx");
        tmpFile.deleteOnExit();
        template.writeAndClose(new FileOutputStream(tmpFile));
        tmpFile.delete();
    }

}
