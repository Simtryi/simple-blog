package com.simple.blog.portal.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.data.entity.Blog;
import com.simple.blog.data.entity.Tag;
import com.simple.blog.portal.service.TagShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签展示 Controller
 */
@RestController
@RequestMapping("/portal/tag")
public class TagShowController {

    @Autowired
    private TagShowService tagShowService;

    /**
     * 展示所有标签
     */
    @RequestMapping(value = "/all")
    public CommonResult<List<Tag>> listAll() {
        List<Tag> tagList = tagShowService.listAll();
        return CommonResult.success(tagList);
    }

    /**
     * 展示随机标签
     */
    @RequestMapping(value = "/random")
    public CommonResult<List<Tag>> listRandom() {
        List<Tag> tagList = tagShowService.listRandom();
        return CommonResult.success(tagList);
    }

    /**
     * 获取标签的博客列表
     */
    @RequestMapping(value = "/blog/list/{id}")
    public CommonResult<List<Blog>> getBlogList(@PathVariable long id) {
        List<Blog> blogList = tagShowService.getBlogList(id);
        return CommonResult.success(blogList);
    }

}
