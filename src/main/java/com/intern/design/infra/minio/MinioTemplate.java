package com.intern.design.infra.minio;

import com.intern.design.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MinioTemplate {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public ObjectWriteResponse uploadFile(String objectName,InputStream inputStream){
        return uploadFile(minioConfig.getBucketName(),objectName,inputStream);
    }


    @SneakyThrows
    public ObjectWriteResponse uploadFile(
            String bucketName,
            String objectName,
            InputStream inputStream
    ){
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream,inputStream.available(),-1)
                .build();

        return minioClient.putObject(args);
    }

}
