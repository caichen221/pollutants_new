package com.iscas.common.markdown.tools;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/18 19:19
 */
class MarkdownUtilsTest {

    @Test
    void markdownToHtml() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("guid.md");
        byte[] bytes = is.readAllBytes();
        String s = new String(bytes, StandardCharsets.UTF_8);
        String s1 = MarkdownUtils.markdownToHtml(s);
        System.out.println(s1);
    }

    @Test
    void markdownToHtml2() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("guid.md");
        byte[] bytes = is.readAllBytes();
        String s = new String(bytes, StandardCharsets.UTF_8);
        String s1 = MarkdownUtils.markdownToHtml(s, new MarkdownUtils.CustomAttributeProvider());
        System.out.println(s1);
    }
}