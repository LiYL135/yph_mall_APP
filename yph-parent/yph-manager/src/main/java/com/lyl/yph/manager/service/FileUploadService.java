package com.lyl.yph.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: lyl
 * @Description: 文件上传与下载 minio
 * @Date: 2024/1/31 10:04
 */
public interface FileUploadService {

    // 文件上传
    String fileUpload(MultipartFile multipartFile);
}
