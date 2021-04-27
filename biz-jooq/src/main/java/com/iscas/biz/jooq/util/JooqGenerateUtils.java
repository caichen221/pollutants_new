package com.iscas.biz.jooq.util;

import lombok.Cleanup;
import org.jooq.codegen.GenerationTool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * jooq代码生成器
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/4/23 10:51
 * @since jdk1.8
 */
public class JooqGenerateUtils {
    private JooqGenerateUtils() {}

    public static void generate() throws Exception {
        @Cleanup InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("JooqConfig.xml");
        @Cleanup InputStreamReader isr = new InputStreamReader(is);
        @Cleanup BufferedReader br = new BufferedReader(isr);
        String line = null;
        StringBuilder jooqXmlStr = new StringBuilder();
        while ((line = br.readLine()) != null) {
            jooqXmlStr.append(line);
        }
        GenerationTool.generate(jooqXmlStr.toString());
    }

    public static void main(String[] args) throws Exception {
        generate();
    }
}
