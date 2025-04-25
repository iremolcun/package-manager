package com.repsy.package_manager;

import com.repsy.package_manager.storage.mypackage.*;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/minio")
public class MinioController {

    private final StorageStrategy storageStrategy;

    public MinioController(StorageStrategy storageStrategy) {
        this.storageStrategy = storageStrategy;
    }

    // 🔼 Dosya yükleme endpoint
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String path = file.getOriginalFilename(); // veya istersen versiyonlama yapabilirsin
            InputStream inputStream = file.getInputStream();
            storageStrategy.save(path, inputStream);
            return ResponseEntity.ok("Dosya başarıyla yüklendi: " + path);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Yükleme hatası: " + e.getMessage());
        }
    }

    // 🔽 Dosya indirme endpoint
    @GetMapping("/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) {
        try {
            InputStream fileStream = storageStrategy.read(filename);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(fileStream));

        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }
}