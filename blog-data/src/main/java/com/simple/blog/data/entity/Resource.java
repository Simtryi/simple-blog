package com.simple.blog.data.entity;

import com.simple.blog.data.base.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 资源
 * <p>
 * 资源即后台接口，可以是单个接口，也可以是接口的集合。
 * 如果接口被添加到资源，用户访问该接口时需要进行鉴权。
 * </p>
 */
public class Resource extends BaseEntity implements Serializable {

    /**
     * 资源名称
     */
    @NotBlank(message = "资源名称不能为空")
    private String name;

    /**
     * 资源 URL，支持 Ant 路径匹配规则
     */
    @NotBlank(message = "资源URL不能为空")
    private String url;

    /**
     * 描述
     */
    private String description;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
