package com.example.blog.dto.response;

import com.example.blog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse {
    Long id;
    String title;
    String summary;
    String status;
    Long categoryId;
    String categoryName;
    Integer viewCount;
    Integer wordCount;
    Integer readingTime;
    List<String> tags;
    String coverImage;
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
