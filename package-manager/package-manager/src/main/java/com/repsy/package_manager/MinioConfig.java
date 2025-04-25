package com.repsy.package_manager;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // veya senin MinIO adresin
                .credentials("minioadmin", "minioadmin") // kendi kullanıcı ve şifreni yaz
                .build();
    }
}
