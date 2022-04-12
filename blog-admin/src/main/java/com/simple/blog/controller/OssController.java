package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.properties.OssProperties;
import com.simple.blog.common.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OSS Controller
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    OssService ossService;

    @Autowired
    OssProperties ossProperties;

    @PostMapping("/upload")
    public CommonResult<String> uploadFile(@RequestBody MultipartFile multipartFile) {
        String fileName = ossService.uploadFile(multipartFile);
        String url = "http://".concat(ossProperties.getBucketName()).concat(".").concat(ossProperties.getEndpoint().substring(8)).concat("/").concat(fileName);
        return CommonResult.success(url);
    }

    @RequestMapping("/download")
    public CommonResult<Void> downloadFile(@RequestParam String url, HttpServletResponse response) throws IOException {
        String prefix = "http://".concat(ossProperties.getBucketName()).concat(".").concat(ossProperties.getEndpoint().substring(8)).concat("/");
        String objectName = url.replaceAll(prefix, "");
        String fileName = objectName.substring(objectName.lastIndexOf("."));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ossService.downloadFile(response.getOutputStream(), objectName);
        return CommonResult.success();
    }

    @RequestMapping("/delete")
    public CommonResult<Void> deleteFile(@RequestParam String url) {
        String prefix = "http://".concat(ossProperties.getBucketName()).concat(".").concat(ossProperties.getEndpoint().substring(8)).concat("/");
        String objectName = url.replaceAll(prefix, "");
        ossService.deleteFile(objectName);
        return CommonResult.success();
    }

}
