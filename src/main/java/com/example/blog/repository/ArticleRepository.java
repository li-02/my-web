package com.example.blog.repository;

import com.example.blog.entity.Article;
import com.example.blog.entity.ArticleEnums;
import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    // JpaRepository<Article,Long> 表示要操作Article这个类,Long表示article中主键id是Long类型
    // Long会被映射到数据库的bigint类型
    /**
     * 根据状态查询文章
     */
    List<Article> findByStatus(ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 分页查询已发布的文章
     */
    Page<Article> findByStatusOrderByCreateTimeDesc(ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 根据分类查询已发布的文章
     */
    Page<Article> findByCategoryAndStatusOrderByCreateTimeDesc(Category category, ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 根据slug查询文章
     */
    Optional<Article> findBySlug(String slug);

    /**
     * 根据标题模糊查询已发布的文章
     */
    Page<Article> findByTitleContainingAndStatusOrderByCreateTimeDesc(String title, ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 查询置顶的已发布文章
     */
    List<Article> findByIsPinnedTrueAndStatusOrderByCreateTimeDesc(ArticleEnums.ArticleStatus status);

    /**
     * 根据分类ID查询文章数量
     */
    long countByCategoryId(Long categoryId);

    /**
     * 查询热门文章（按浏览量排序）
     */
    @Query("SELECT a FROM Article a WHERE a.status = :status ORDER BY a.viewCount DESC")
    List<Article> findTopByViewCount(@Param("status") ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 增加浏览量
     */
    @Modifying
    @Query("UPDATE Article a SET a.viewCount = a.viewCount + 1 WHERE a.id = :id")
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加点赞数
     */
    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount + 1 WHERE a.id = :id")
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 全文搜索（标题和内容）
     */
    @Query("SELECT a FROM Article a WHERE a.status = :status AND (a.title LIKE %:keyword% OR a.content LIKE %:keyword%) ORDER BY a.createTime DESC")
    Page<Article> searchByKeyword(@Param("keyword") String keyword, @Param("status") ArticleEnums.ArticleStatus status, Pageable pageable);

    /**
     * 查询最新文章
     */
    List<Article> findTop5ByStatusOrderByCreateTimeDesc(ArticleEnums.ArticleStatus status);

    /**
     * 根据标签查询文章
     */
    @Query("SELECT DISTINCT a FROM Article a JOIN a.tags t WHERE t.name = :tagName AND a.status = :status ORDER BY a.createTime DESC")
    Page<Article> findByTagName(@Param("tagName") String tagName, @Param("status") ArticleEnums.ArticleStatus status, Pageable pageable);

    Page<Article> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Article> findByTitleContaining(String keyword, Pageable pageable);

    Page<Article> findByTitleContainingOrderByCreateTimeDesc(String title, Pageable pageable);

    /**
     * 根据分类ID查询文章
     */
    @Query("SELECT a FROM Article a WHERE a.category.id = :categoryId ORDER BY a.createTime DESC")
    Page<Article> findByCategoryIdOrderByCreateTimeDesc(@Param("categoryId") Long categoryId, Pageable pageable);
}
