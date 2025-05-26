package com.example.blog.repository;

import com.example.blog.entity.Article;
import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据文章查询评论
     */
    List<Comment> findByArticleAndStatusOrderByCreateTimeAsc(Article article, CommentStatus status);

    /**
     * 根据文章ID查询评论
     */
    List<Comment> findByArticleIdAndStatusOrderByCreateTimeAsc(Long articleId, CommentStatus status);

    /**
     * 分页查询文章的评论
     */
    Page<Comment> findByArticleAndStatusOrderByCreateTimeAsc(Article article, CommentStatus status, Pageable pageable);

    /**
     * 根据状态查询评论
     */
    Page<Comment> findByStatusOrderByCreateTimeDesc(CommentStatus status, Pageable pageable);

    /**
     * 查询顶级评论（非回复）
     */
    List<Comment> findByArticleAndParentIdAndStatusOrderByCreateTimeAsc(Article article, Long parentId, CommentStatus status);

    /**
     * 查询某评论的回复
     */
    List<Comment> findByParentIdAndStatusOrderByCreateTimeAsc(Long parentId, CommentStatus status);

    /**
     * 统计文章的评论数量
     */
    long countByArticleAndStatus(Article article, CommentStatus status);

    /**
     * 统计文章的评论数量（根据文章ID）
     */
    long countByArticleIdAndStatus(Long articleId, CommentStatus status);

    /**
     * 根据IP查询评论
     */
    List<Comment> findByIpOrderByCreateTimeDesc(String ip);

    /**
     * 查询最新的评论
     */
    List<Comment> findTop10ByStatusOrderByCreateTimeDesc(CommentStatus status);

    /**
     * 根据昵称和邮箱查询评论
     */
    List<Comment> findByNicknameAndEmailOrderByCreateTimeDesc(String nickname, String email);

    /**
     * 查询待审核的评论数量
     */
    long countByStatus(CommentStatus status);

    /**
     * 查询评论及其回复的完整树形结构
     */
    @Query("SELECT c FROM Comment c WHERE c.article = :article AND c.status = :status ORDER BY c.parentId ASC, c.createTime ASC")
    List<Comment> findCommentTreeByArticle(@Param("article") Article article, @Param("status") CommentStatus status);
}