package com.example.blog.repository;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据名称查询分类
     */
    Optional<Category> findByName(String name);

    /**
     * 检查分类名称是否存在
     */
    boolean existsByName(String name);

    /**
     * 查找所有未删除的分类，按创建时间降序排序
     */
    List<Category> findByDeletedFalseOrderByCreateTimeDesc();

    /**
     * 查分页询所有未删除的分类，按创建时间排序
     */
    @Query("SELECT new com.example.blog.dto.response.CategoryWithCountResponse(c.id, c.name, c.description, c.createTime, c.updateTime, COUNT(a)) " +
            "FROM Category c LEFT JOIN Article a ON c.id = a.category.id AND a.status = 'PUBLISHED' " +
            "WHERE c.deleted = false AND (:keyword IS NULL OR c.name LIKE %:keyword%) " +
            "GROUP BY c.id, c.name, c.description, c.createTime, c.updateTime " +
            "ORDER BY c.createTime DESC")
    Page<CategoryWithCountResponse> findCategoriesWithArticleCount(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 根据名称模糊查询分类
     */
    List<Category> findByNameContainingOrderByCreateTimeDesc(String name);

    /**
     * 查询有文章的分类及文章数量
     */
    @Query("SELECT c, COUNT(a) FROM Category c LEFT JOIN Article a ON c.id = a.category.id WHERE a.status = 'PUBLISHED' GROUP BY c ORDER BY COUNT(a) DESC")
    List<Object[]> findCategoriesWithArticleCount();

    @Modifying
    @Query("UPDATE Category c SET c.name = :name, c.description = :description WHERE c.id = :id")
    int updateCategory(@Param("id") Long id, @Param("name") String name, @Param("description") String description);

    @Modifying
    @Query("UPDATE Category c SET c.deleted = true,c.deleteTime=now() WHERE c.id = :id")
    int deleteCategories(@Param("id") Long id);
}