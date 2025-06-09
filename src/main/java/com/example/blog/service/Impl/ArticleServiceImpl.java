package com.example.blog.service.Impl;

import com.example.blog.dto.request.CreateArticleRequest;
import com.example.blog.dto.response.ArticleDetailResponse;
import com.example.blog.dto.response.ArticleResponse;
import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleEnums;
import com.example.blog.entity.Category;
import com.example.blog.entity.Tag;
import com.example.blog.repository.ArticleRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Boolean createArticle(CreateArticleRequest request) {
        Article article = new Article();
        // 基本信息
        article.setTitle(request.getTitle());
        article.setSummary(request.getSummary());
        article.setContent(request.getContent());
        // 在设置 slug 之前添加检查
        if (articleRepository.findBySlug(request.getSlug()).isPresent()) {
            throw new Error("slug 已存在，请使用其他值");
        }
        article.setSlug(request.getSlug());
        article.setCoverImage(request.getCoverImage());

        // 状态相关
        article.setStatus(ArticleEnums.ArticleStatus.valueOf(request.getStatus()));
        article.setIsPinned(request.getIsPinned());
        article.setIsOriginal(request.getIsOriginal());
        article.setIsAllowComment(request.getAllowComment());

        // SEO信息
        article.setMetaDescription(request.getMetaDescription());
        article.setMetaKeywords(request.getMetaKeywords());

        // 统计信息
        article.setReadingTime(request.getReadingTime());
        // wordCount
        article.setWordCount(request.getWordCount());

        // 处理分类
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("分类不存在"));
            article.setCategory(category);
        }

        // 处理标签
        if (request.getTags() != null && !request.getTags().isEmpty()) {
            List<Tag> tags = new ArrayList<>();
            for (String tagName : request.getTags()) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                tags.add(tag);
            }
            article.setTags(tags);
        }

        // 如果是发布状态，设置发布时间
        if ("PUBLISHED".equals(request.getStatus())) {
            article.setPublishTime(LocalDateTime.now());
        }
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 如果保存失败，返回false
        }
        return true;
    }

    @Override
    public Page<ArticleResponse> getArticles(String keyword, String status, Long categoryId, Pageable pageable) {
        Page<Article> articles;
        if (keyword != null && !keyword.isEmpty()) {
            articles = articleRepository.findByTitleContainingOrderByCreateTimeDesc(keyword, pageable);
        } else if (categoryId != null) {
            articles = articleRepository.findByCategoryIdOrderByCreateTimeDesc(categoryId, pageable);
        } else if (status != null && !status.isEmpty()) {
            try {
                ArticleEnums.ArticleStatus articleStatus = ArticleEnums.ArticleStatus.valueOf(status);
                articles = articleRepository.findByStatusOrderByCreateTimeDesc(articleStatus, pageable);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("无效的文章状态: " + status);
            }
        } else {
            articles = articleRepository.findAll(pageable);
        }

        return articles.map(article -> new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getSummary(),
                article.getStatus().toString(),
                article.getCategory() != null ? article.getCategory().getId() : null,
                article.getCategory() != null ? article.getCategory().getName() : "未分类",
                article.getViewCount(),
                article.getWordCount(),
                article.getReadingTime(),
                article.getTags() != null ? article.getTags().stream().map(Tag::getName).toList() : new ArrayList<>(),
                article.getCoverImage(),
                article.getCreateTime(),
                article.getUpdateTime()
        ));
    }

    @Override
    public ArticleDetailResponse getArticleDetail(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        ArticleDetailResponse response = new ArticleDetailResponse();
        if (article.isPresent()) {
            Article art = article.get();
            response.setId(art.getId());
            response.setTitle(art.getTitle());
            response.setSummary(art.getSummary());
            response.setContent(art.getContent());
            response.setCategoryName(art.getCategory().getName());
            response.setReadingTime(art.getReadingTime());
            response.setWordCount(art.getWordCount());
            response.setCoverImage(art.getCoverImage());
            response.setIsPinned(art.getIsPinned());
            response.setIsOriginal(art.getIsOriginal());
            response.setViewCount(art.getViewCount());
            response.setTags(art.getTags().stream().map(Tag::getName).toList());
            response.setCreateTime(art.getCreateTime().toString());
            response.setUpdateTime(art.getUpdateTime().toString());
        } else {
            throw new RuntimeException("文章不存在");
        }
        return response;
    }

    @Override
    @Transactional
    public Boolean incrementViewCount(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        // 如果文章存在，增加浏览量
        if (article.isPresent()) {
            articleRepository.incrementViewCount(id);
            return true;
        } else {
            return false;
        }
    }
}
