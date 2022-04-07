package com.simple.blog.search.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * ES 中的博客
 */
@Document(indexName = "blog")
public class ESBlog {

    /**
     * Id
     */
    @Id
    private Long id;

    /**
     * 标题
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String title;

    /**
     * 封面
     */
    private String cover;

    /**
     * MD文件源码
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String mdContent;

    /**
     * HTML文件源码
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String htmlContent;

    /**
     * 阅读量
     */
    private Long view;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMdContent() {
        return mdContent;
    }

    public void setMdContent(String mdContent) {
        this.mdContent = mdContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

}
