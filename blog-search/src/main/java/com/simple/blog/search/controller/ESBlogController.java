package com.simple.blog.search.controller;

import com.simple.blog.common.api.CommonPage;
import com.simple.blog.common.api.CommonResult;
import com.simple.blog.search.entity.ESBlog;
import com.simple.blog.search.service.ESBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ES 博客 Controller
 */
@RestController
@RequestMapping("/es/blog")
public class ESBlogController {

    @Autowired
    private ESBlogService esBlogService;

    @PostMapping("/create")
    public CommonResult<Integer> create() {
        int count = esBlogService.create();
        return CommonResult.success(count);
    }

    @RequestMapping("/delete/{id}")
    public CommonResult<Void> delete(@PathVariable Long id) {
        esBlogService.delete(id);
        return CommonResult.success();
    }

    @PostMapping("/delete/batch")
    public CommonResult<Void> delete(@RequestBody List<Long> ids) {
        esBlogService.delete(ids);
        return CommonResult.success();
    }

    @RequestMapping("/search")
    public CommonResult<CommonPage<ESBlog>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        Page<ESBlog> esBlogPage = esBlogService.search(title, pageNum, pageSize);
        return CommonResult.success(new CommonPage<>(esBlogPage));
    }

    @RequestMapping("/detail/{id}")
    public CommonResult<ESBlog> detail(@PathVariable Long id) {
        Optional<ESBlog> optional = esBlogService.findById(id);
        ESBlog esBlog = optional.get();
        return CommonResult.success(esBlog);
    }

}
