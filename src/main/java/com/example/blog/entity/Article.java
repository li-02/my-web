package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章实体类
 */
@Setter
@Getter
@Entity
@Table(name = "article", indexes = {
        @Index(name = "idx_category", columnList = "category_id"),
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_create_time", columnList = "create_time")
})
public class Article extends BaseEntity {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "slug", unique = true, length = 200)
    private String slug;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "view_count", columnDefinition = "INT DEFAULT 0")
    private Integer viewCount = 0;

    @Column(name = "like_count", columnDefinition = "INT DEFAULT 0")
    private Integer likeCount = 0;

    @Column(name = "comment_count", columnDefinition = "INT DEFAULT 0")
    private Integer commentCount = 0;

    @Column(name = "reading_time", columnDefinition = "INT DEFAULT 0")
    private Integer readingTime = 0;
    
    @Column(name = "word_count", columnDefinition = "INT DEFAULT 0")
    private Integer wordCount = 0;

    @Column(name = "publish_time")
    private LocalDateTime publishTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ArticleEnums.ArticleStatus status = ArticleEnums.ArticleStatus.DRAFT;

    @Column(name = "is_pinned", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPinned = false;

    @Column(name = "is_allow_comment", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAllowComment = true;

    @Column(name = "is_original", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isOriginal = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_type", length = 20)
    private ArticleEnums.AccessType accessType = ArticleEnums.AccessType.PUBLIC;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "meta_description", length = 300)
    private String metaDescription;

    @Column(name = "meta_keywords", length = 200)
    private String metaKeywords;

    // 多对一：文章属于一个分类
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // 多对多：文章可以有多个标签
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    // 一对多：文章可以有多个评论
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // Constructors
    public Article() {}

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
