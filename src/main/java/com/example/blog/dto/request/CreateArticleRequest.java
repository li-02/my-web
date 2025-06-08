package com.example.blog.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateArticleRequest {
    private Long id;
    private String title;
    private String summary;
    private String content;
    private Long categoryId;
    private List<String> tags;
    private String status;
    private Boolean isPinned;
    private Boolean isOriginal;
    private Boolean allowComment;
    private String coverImage;
    private String slug;
    private String metaDescription;
    private String metaKeywords;
    private Integer wordCount;
    private Integer readingTime;
}
