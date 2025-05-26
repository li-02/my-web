package com.example.blog.entity;

/**
 * 文章相关枚举类
 */
public class ArticleEnums {

    /**
     * 文章状态枚举
     */
    public enum ArticleStatus {
        DRAFT("草稿"),
        PUBLISHED("已发布"),
        ARCHIVED("已归档");

        private final String description;

        ArticleStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 文章访问类型枚举
     */
    public enum AccessType {
        PUBLIC("公开"),
        PRIVATE("私有"),
        PASSWORD("密码保护");

        private final String description;

        AccessType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
