package com.simple.blog.common.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.*;

/**
 * Markdown 工具
 */
public class MarkdownUtil {

    /**
     * 将 Markdown 格式解析成 HTML 格式
     */
    public static String parse(String markdown) {
        //  标题锚点扩展
        Set<Extension> headingAnchorExtensions = Collections.singleton(HeadingAnchorExtension.create());
        //  表格扩展
        List<Extension> tableExtensions = Collections.singletonList(TablesExtension.create());

        //  解析 Markdown
        Parser parser = Parser.builder()
                .extensions(tableExtensions)
                .build();
        Node document = parser.parse(markdown);

        HtmlRenderer htmlRenderer = HtmlRenderer.builder()
                .extensions(headingAnchorExtensions)
                .extensions(tableExtensions)
                .attributeProviderFactory(attributeProviderContext -> (node, tagName, attributes) -> {
                    //  改变 <a> 标签的 target 属性为 _blank
                    if (node instanceof Link) {
                        attributes.put("target", "_blank");
                    }
                })
                .build();
        return htmlRenderer.render(document);
    }

}
