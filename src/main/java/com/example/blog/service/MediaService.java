package com.example.blog.service;

import com.example.blog.entity.Media;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MediaService {
    /**
     * 保存上传的文件
     */
    Media saveFile(MultipartFile file, String category) throws IOException;
}
