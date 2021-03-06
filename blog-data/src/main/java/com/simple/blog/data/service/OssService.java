package com.simple.blog.data.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

/**
 * OSS 服务
 */
public interface OssService {

    /**
     * 上传文件
     */
    String uploadFile(MultipartFile multipartFile);

    /**
     * 下载文件
     */
    void downloadFile(OutputStream outputStream, String objectName);

    /**
     * 删除文件
     */
    void deleteFile(String objectName);

}
