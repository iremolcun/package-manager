package com.repsy.package_manager;

import io.minio.*;
import com.repsy.package_manager.storage.mypackage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;

@Service
@ConditionalOnProperty(name = "storageStrategy", havingValue = "object-storage")
public class MinioService implements StorageStrategy {

    private MinioClient minioClient;  // <== final kaldırıldı

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @PostConstruct
    public void init() {
        // Bu sınıf zaten @ConditionalOnProperty ile aktif olacağı için tekrar kontrol gerekmez
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Override
    public void save(String path, InputStream content) {
        try {
            boolean exists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!exists) {
                minioClient.makeBucket(
                    MakeBucketArgs.builder().bucket(bucketName).build()
                );
            }

            minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(path)
                        .stream(content, -1, 10485760)
                        .contentType("application/octet-stream")
                        .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("MinIO save failed", e);
        }
    }

    @Override
    public InputStream read(String path) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("MinIO read failed", e);
        }
    }
}
