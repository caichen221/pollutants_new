package com.iscas.common.markdown.tools;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Image;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * @author zhuquanwen
 * @version 1.0
 * @date 2023/4/18 19:17
 */
public class MarkdownUtils {
    private MarkdownUtils() {
    }

    /**
     * markdown转为html
     *
     * @param markdown markdown编写的字符串
     * @return java.lang.String
     * @date 2023/4/18
     */
    public static String markdownToHtml(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public static String markdownToHtml(String markdown, AttributeProvider attributeProvider) {
        //h标题生成id
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //转换table的HTML
        List<Extension> tableExtension = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder()
                .extensions(tableExtension)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtension)
                .attributeProviderFactory(context -> attributeProvider)
                .build();
        return renderer.render(document);
    }

    static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            //改变a标签的target属性为_blank
            if (node instanceof Link) {
                attributes.put("target", "_blank");
            } else if (node instanceof TableBlock) {
                attributes.put("class", "ui celled table");
            } else if (node instanceof Image) {

            }
        }
    }



}
