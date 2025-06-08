package com.example.blog.dto.response;

import com.example.blog.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    LocalDateTime createTime;
    LocalDateTime updateTime;
}
