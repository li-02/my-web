package com.example.blog.entity;

import lombok.Getter;

/**
 * 评论状态枚举
 */
@Getter
public enum CommentStatus {
    PENDING("待审核"),
    APPROVED("已审核"),
    REJECTED("已拒绝");

    private final String description;

    CommentStatus(String description) {
        this.description = description;
    }

}