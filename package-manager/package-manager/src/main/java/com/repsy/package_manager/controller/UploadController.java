package com.repsy.package_manager.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.repsy.package_manager.dto.MetaJsonDTO;
import com.repsy.package_manager.model.PackageMetadata;
import com.repsy.package_manager.repository.PackageMetadataRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final PackageMetadataRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/{packageName}/{version}")
    public ResponseEntity<?> uploadPackage(
            @PathVariable String packageName,
            @PathVariable String version,
            @RequestParam("meta") MultipartFile metaFile,
            @RequestParam("package") MultipartFile packageFile
    ) {
        try {
            
            MetaJsonDTO meta = objectMapper.readValue(metaFile.getInputStream(), MetaJsonDTO.class);

            
            if (!meta.getName().equals(packageName) || !meta.getVersion().equals(version)) {
                return ResponseEntity.badRequest().body("Meta bilgisiyle path uyuşmuyor!");
            }

            
            PackageMetadata metadata = PackageMetadata.builder()
                    .name(meta.getName())
                    .version(meta.getVersion())
                    .author(meta.getAuthor())
                    .dependencies(meta.getDependencies().stream()
                            .map(d -> d.getPackageName() + ":" + d.getVersion()).toList())
                    .build();
            repository.save(metadata);

            
            Path uploadDir = Paths.get("storage", packageName, version);
            Files.createDirectories(uploadDir);

            Files.copy(metaFile.getInputStream(), uploadDir.resolve("meta.json"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(packageFile.getInputStream(), uploadDir.resolve("package.rep"), StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("Yükleme başarılı");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Yükleme başarısız");
        }
    }
}
