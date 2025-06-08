package com.example.blog.service;

import com.example.blog.dto.request.CreateArticleRequest;
import com.example.blog.dto.response.ArticleDetailResponse;
import com.example.blog.dto.response.ArticleResponse;
import com.example.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Boolean createArticle(CreateArticleRequest request);

    Page<ArticleResponse> getArticles(String keyword, String status, Long categoryId, Pageable pageable);

    ArticleDetailResponse getArticleDetail(Long id);

    Boolean incrementViewCount(Long id);
}
