package com.example.blog.dto.response;

import lombok.Data;

@Data
public class ArticleDetailResponse {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private String categoryName;
    private String coverImage;
    private Boolean isPinned;
    private Boolean isOriginal;
    private Integer viewCount;
    private Integer readingTime;
    private Integer wordCount;
    private String createTime;
    private String updateTime;
}
