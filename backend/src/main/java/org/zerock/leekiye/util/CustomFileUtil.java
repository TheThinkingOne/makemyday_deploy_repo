package org.zerock.leekiye.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("upload")
    private String uploadPath;

    private static final List<String> SUPPORTED_IMAGE_FORMATS = List.of("jpg", "jpeg", "png", "bmp", "gif");
    private static final List<String> SUPPORTED_VIDEO_FORMATS = List.of("mp4");

    @PostConstruct
    public void init() {
        File tempFolder = new File(uploadPath);
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("--------------------");
        log.info("uploadPath: " + uploadPath);
    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException {
        if (files == null || files.isEmpty()) return List.of();

        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile file : files) {
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf('.') + 1).toLowerCase();

            boolean isImage = SUPPORTED_IMAGE_FORMATS.contains(extension);
            boolean isVideo = SUPPORTED_VIDEO_FORMATS.contains(extension);

            if (!isImage && !isVideo) {
                throw new RuntimeException("지원하지 않는 파일 형식입니다: " + extension);
            }

            String savedName = UUID.randomUUID() + "_" + originalName;
            Path savePath = Paths.get(uploadPath, savedName);

            try {
                Files.copy(file.getInputStream(), savePath);

                // 이미지일 경우에만 썸네일 생성
                if (isImage) {
                    Path thumbNailPath = Paths.get(uploadPath, "s_" + savedName);
                    Thumbnails.of(savePath.toFile()).size(200, 200).toFile(thumbNailPath.toFile());
                }

                uploadNames.add(savedName);
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류 발생", e);
            }
        }

        return uploadNames;
    }

    public ResponseEntity<Resource> getFile(String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        if (!resource.exists() || !resource.isReadable()) {
            resource = new FileSystemResource(uploadPath + File.separator + "default.jpeg");
        }

        HttpHeaders headers = new HttpHeaders();
        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine file type", e);
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFiles(List<String> fileNames) {
        if (fileNames == null || fileNames.isEmpty()) return;

        fileNames.forEach(fileName -> {
            Path filePath = Paths.get(uploadPath, fileName);
            Path thumbnailPath = Paths.get(uploadPath, "s_" + fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }
}
