package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "comment", indexes = {
        @Index(name = "idx_article", columnList = "article_id"),
        @Index(name = "idx_parent", columnList = "parent_id")
})
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false, length = 50)
    private String nickname;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "website", length = 100)
    private String website;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "ip", length = 50)
    private String ip;

    @Column(name = "parent_id", columnDefinition = "BIGINT DEFAULT 0")
    private Long parentId = 0L;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private CommentStatus status = CommentStatus.PENDING;

    // 多对一：评论属于一篇文章
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    // Constructors
    public Comment() {}

    public Comment(String nickname, String content, Article article) {
        this.nickname = nickname;
        this.content = content;
        this.article = article;
    }

}