package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.FileUploadService;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件的上传与下载  minio
 */
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService ;

    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/fileUpload")
    public Result<String> fileUploadService(@RequestParam("file") MultipartFile multipartFile) {
        String fileUrl = fileUploadService.fileUpload(multipartFile) ;
        return Result.build(fileUrl , ResultCodeEnum.SUCCESS) ;
    }

}