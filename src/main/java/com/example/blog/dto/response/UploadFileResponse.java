package com.example.blog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse {
    String url;
    String filename;
    String originalFilename;
    Long size;
    String mediaType;
}
