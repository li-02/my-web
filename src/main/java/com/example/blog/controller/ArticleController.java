package com.example.blog.controller;

import com.example.blog.dto.request.CreateArticleRequest;
import com.example.blog.dto.response.ArticleDetailResponse;
import com.example.blog.dto.response.ArticleResponse;
import com.example.blog.dto.response.PageResponse;
import com.example.blog.entity.Article;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/create-article")
    @Operation(summary = "创建文章", description = "创建一篇新的文章，返回创建结果")
    public ApiResponse<Boolean> createArticle(@Valid @RequestBody CreateArticleRequest request) {
        Boolean result = articleService.createArticle(request);
        if (result) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error("创建文章失败");
        }
    }


    @GetMapping("/articles")
    @Operation(summary = "获取文章列表", description = "分页获取所有文章的列表")
    public ApiResponse<PageResponse<ArticleResponse>> getAllArticles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String status
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
            Page<ArticleResponse> articlePage = articleService.getArticles(keyword, status, categoryId, pageable);
            return ApiResponse.success(PageResponse.of(articlePage));
        } catch (IllegalArgumentException e) {
            return ApiResponse.error("参数错误: " + e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("获取文章列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/article-detail/{id}")
    public ApiResponse<ArticleDetailResponse> getArticleDetail(@PathVariable Long id) {
        return ApiResponse.success(articleService.getArticleDetail(id));
    }

    @GetMapping("/increment-view/{id}")
    public ApiResponse<Boolean> incrementView(@PathVariable Long id) {
        try {
            boolean result = articleService.incrementViewCount(id);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("增加浏览量失败: " + e.getMessage());
        }
    }
}
