package com.example.blog.controller;

import com.example.blog.dto.response.UploadFileResponse;
import com.example.blog.entity.Media;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties;
import com.example.blog.response.ResponseCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PostExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * 媒体控制器
 */
@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Value("${app.upload.base-url:http://localhost:8080}")
    private String baseUrl;

    @PostMapping("/upload/image")
    @Operation(summary = "上传图片", description = "上传单张图片接口")
    public ApiResponse<UploadFileResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "image") String type
    ) {
        try {
            // 验证文件
            if (file.isEmpty()) {
                return ApiResponse.badRequest("文件不能为空");
            }
            // 验证文件类型
            if (!isValidImageType(file)) {
                return ApiResponse.badRequest("只支持 JPG, PNG, GIF 格式的图片");
            }
            // 保存文件
            Media media = mediaService.saveFile(file, "image");
            // 构建返回的url
            String imageUrl = baseUrl + "/uploads/images/" + media.getFilename();
            // 返回成功响应
            UploadFileResponse uploadFileResponse = new UploadFileResponse(imageUrl,
                    media.getFilename(),
                    media.getOriginalFilename(),
                    media.getFileSize(),
                    media.getMediaType()
            );

            return ApiResponse.success("图片上传成功", uploadFileResponse);
        } catch (Exception e) {
            return ApiResponse.error(ResponseCode.FILE_UPLOAD_FAILED, "图片上传失败: " + e.getMessage());
        }
    }


    /**
     * 验证图片文件类型
     */
    private boolean isValidImageType(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (
                contentType.equals("image/jpeg") ||
                        contentType.equals("image/jpg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("image/gif") ||
                        contentType.equals("image/webp")
        );
    }

    /**
     * 创建错误响应
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
}
