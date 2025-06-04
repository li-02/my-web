package com.example.blog.service;

import com.example.blog.entity.Media;
import com.example.blog.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    // 从配置文件读取上传目录，默认为 uploads
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;


    @Override
    public Media saveFile(MultipartFile file, String category) throws IOException {
        String subPath = "";
        if (Objects.equals(category, "image")) {
            subPath = "images";
        } else if (Objects.equals(category, "video")) {
            subPath = "videos";
        } else if (Objects.equals(category, "document")) {
            subPath = "documents";
        }
        // 创建上传目录
        Path uploadPath = Paths.get(uploadDir, subPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uniqueFilename = category + "_" + timestamp + "_" + UUID.randomUUID().toString().substring(0, 8) + fileExtension;
        // 保存文件到磁盘
        Path targetPath = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        // 保存文件信息到数据库
        Media media = new Media();
        media.setFilename(uniqueFilename);
        media.setOriginalFilename(originalFilename);
        media.setFilePath(targetPath.toString());
        media.setMediaType(file.getContentType());
        media.setFileSize(file.getSize());

        return mediaRepository.save(media);
    }

    /**
     * 获取文件扩展名
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
