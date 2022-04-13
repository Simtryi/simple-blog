package com.simple.blog.data.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.ApiException;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.data.properties.OssProperties;
import com.simple.blog.data.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * OSSService 实现类
 */
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private OSS ossClient;

    @Autowired
    private OssProperties ossProperties;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || null == multipartFile.getOriginalFilename()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "上传文件错误");
        }

        //  存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = ossProperties.getDirPrefix() + sdf.format(new Date());

        //  文件类型
        String originalFilename = multipartFile.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));

        //  文件名
        String fileName = UUID.randomUUID().toString();

        try {
            //  设置文件元信息
            InputStream inputStream = multipartFile.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");

            //  上传文件
            fileName = dir + "/" + fileName + fileType;
            ossClient.putObject(ossProperties.getBucketName(), fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new ApiException(ResultCode.UNKNOWN, e.getMessage());
        }

        return fileName;
    }

    @Override
    public void downloadFile(OutputStream outputStream, String objectName) {
        //  ossObject 包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流
        OSSObject ossObject = ossClient.getObject(ossProperties.getBucketName(), objectName);

        //  读取文件内容
        BufferedInputStream bufferedInputStream = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        byte[] buffer = new byte[1024];
        int length;
        try {
            while ((length = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, length);
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            throw new ApiException(ResultCode.UNKNOWN, e.getMessage());
        }
    }

    @Override
    public void deleteFile(String objectName) {
        //  删除文件或目录，如果要删除目录，目录必须为空
        ossClient.deleteObject(ossProperties.getBucketName(), objectName);
    }

}
