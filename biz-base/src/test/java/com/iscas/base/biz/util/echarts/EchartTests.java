package com.iscas.base.biz.util.echarts;

import cn.hutool.core.util.ImageUtil;
import com.alibaba.fastjson.JSON;
import com.iscas.common.tools.picture.ImageUtils;
import freemarker.template.TemplateException;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/11/24 21:05
 * @since jdk1.8
 */
public class EchartTests {
    private static String url = "http://localhost:7777";
    @Test
    public void test() throws TemplateException, IOException {
        // 变量
        String title = "水果";
        String[] categories = new String[] { "苹果", "香蕉", "西瓜" };
        int[] values = new int[] { 3, 2, 1 };

        // 模板参数
        HashMap<String, Object> datas = new HashMap<>();
        datas.put("categories", JSON.toJSONString(categories));
        datas.put("values", JSON.toJSONString(values));
        datas.put("title", title);

        // 生成option字符串
        String option = FreemarkerUtils.generateString("option.ftl", "C:\\Users\\zqw02\\IdeaProjects\\newframe-20210905\\biz-base\\src\\test\\java\\com\\iscas\\base\\biz\\util\\echarts", datas);

        // 根据option参数
        String base64 = EchartsUtils.generateEchartsBase64(url, option);

        System.out.println("BASE64:" + base64);

        File test = File.createTempFile("test", ".png");
        test.deleteOnExit();
        try (OutputStream os = new FileOutputStream(test)) {
            ImageUtils.convertBase64ToImage(base64, os);
        }
        System.out.println("测试完成");
    }


}
