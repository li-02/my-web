package com.example.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * 静态资源配置
 * 配置上传文件的访问路径
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置上传文件的访问路径
        // URL路径: /uploads/images/**
        // 实际路径: file:uploads/images/ (相对于项目根目录)
        String uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize().toString();

        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + uploadPath + "/images/")
                .setCachePeriod(3600); // 缓存1小时

        // 如果后续还有其他类型文件，可以继续添加
        // registry.addResourceHandler("/uploads/documents/**")
        //         .addResourceLocations("file:" + uploadPath + "/documents")
        //         .setCachePeriod(3600);
    }
}
