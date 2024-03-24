package com.lyl.yph.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.manager.properties.MinioProperties;
import com.lyl.yph.manager.service.FileUploadService;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;


    /**
     * 文件上传
     * @param multipartFile
     * @return
     */
    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 1.创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                    .build();

            // 2.判断桶是否存在
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {       // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {  // 如果存在打印信息
                System.out.println("Bucket 'yph-bucket' already exists.");
            }

            // 3.获取上传文件名称
            // 根据当前日期堆上传文件进行分组
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            // UUID生成唯一id
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //20230801/443e1e772bef482c95be28704bec58a901.jpg
            String fileName = dateDir+"/"+uuid+multipartFile.getOriginalFilename();
//            System.out.println(fileName);



            //设置参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1) // 获取文件输入流
                    .object(fileName)
                    .build();
            // 4.文件上传
            minioClient.putObject(putObjectArgs) ; //传入参数

            // 5.获取上传文件在minio路径
            //如：http://127.0.0.1:9000/yph-bucket/01.jpg
            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName ;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}