package com.example.blog.repository;

import com.example.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * 查询所有分类，按创建时间排序
     */
    List<Category> findAllByOrderByCreateTimeDesc();

    /**
     * 根据名称模糊查询分类
     */
    List<Category> findByNameContainingOrderByCreateTimeDesc(String name);

    /**
     * 查询有文章的分类及文章数量
     */
    @Query("SELECT c, COUNT(a) FROM Category c LEFT JOIN Article a ON c.id = a.category.id WHERE a.status = 'PUBLISHED' GROUP BY c ORDER BY COUNT(a) DESC")
    List<Object[]> findCategoriesWithArticleCount();
}