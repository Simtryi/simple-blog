package com.simple.blog.controller;

import com.simple.blog.common.api.CommonResult;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.common.service.OSSService;
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
public class OSSController {

    @Autowired
    OSSService ossService;

    @PostMapping("/upload")
    public CommonResult<String> uploadFile(@RequestBody MultipartFile multipartFile) {
        String fileName = ossService.uploadFile(multipartFile);
        String url = "http://".concat(Constants.OSS_BUCKET_NAME).concat(".").concat(Constants.OSS_ENDPOINT.substring(8)).concat("/").concat(fileName);
        return CommonResult.success(url);
    }

    @RequestMapping("/download")
    public CommonResult<Void> downloadFile(@RequestParam String url, HttpServletResponse response) throws IOException {
        String prefix = "http://".concat(Constants.OSS_BUCKET_NAME).concat(".").concat(Constants.OSS_ENDPOINT.substring(8)).concat("/");
        String objectName = url.replaceAll(prefix, "");
        String fileName = objectName.substring(objectName.lastIndexOf("."));
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        ossService.downloadFile(response.getOutputStream(), objectName);
        return CommonResult.success();
    }

    @RequestMapping("/delete")
    public CommonResult<Void> deleteFile(@RequestParam String url) {
        String prefix = "http://".concat(Constants.OSS_BUCKET_NAME).concat(".").concat(Constants.OSS_ENDPOINT.substring(8)).concat("/");
        String objectName = url.replaceAll(prefix, "");
        ossService.deleteFile(objectName);
        return CommonResult.success();
    }

}
