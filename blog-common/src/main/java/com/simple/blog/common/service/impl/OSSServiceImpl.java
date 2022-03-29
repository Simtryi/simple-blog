package com.simple.blog.common.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.common.exception.ApiException;
import com.simple.blog.common.exception.Asserts;
import com.simple.blog.common.service.OSSService;
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
public class OSSServiceImpl implements OSSService {

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty() || null == multipartFile.getOriginalFilename()) {
            Asserts.fail(ResultCode.BAD_REQUEST, "上传文件错误");
        }

        //  创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(Constants.OSS_ENDPOINT, Constants.OSS_ACCESS_KEY_ID, Constants.OSS_ACCESS_KEY_SECRET);

        //  存储目录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = Constants.OSS_DIR_PREFIX + sdf.format(new Date());

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
            ossClient.putObject(Constants.OSS_BUCKET_NAME, fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            throw new ApiException(ResultCode.UNKNOWN, e.getMessage());
        } finally {
            ossClient.shutdown();
        }

        return fileName;
    }

    @Override
    public void downloadFile(OutputStream outputStream, String objectName) {
        //  创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(Constants.OSS_ENDPOINT, Constants.OSS_ACCESS_KEY_ID, Constants.OSS_ACCESS_KEY_SECRET);

        //  ossObject 包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流
        OSSObject ossObject = ossClient.getObject(Constants.OSS_BUCKET_NAME, objectName);

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
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void deleteFile(String objectName) {
        //  创建 OSSClient 实例
        OSS ossClient = new OSSClientBuilder().build(Constants.OSS_ENDPOINT, Constants.OSS_ACCESS_KEY_ID, Constants.OSS_ACCESS_KEY_SECRET);
        //  删除文件或目录，如果要删除目录，目录必须为空
        ossClient.deleteObject(Constants.OSS_BUCKET_NAME, objectName);
        ossClient.shutdown();
    }

}
