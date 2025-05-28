package com.example.blog.repository;

import com.example.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据名称查询标签
     */
    Optional<Tag> findByName(String name);

    /**
     * 检查标签名称是否存在
     */
    boolean existsByName(String name);

    /**
     * 根据名称集合查询标签
     */
    List<Tag> findByNameIn(List<String> names);

    /**
     * 查询所有标签，按创建时间排序
     */
    List<Tag> findAllByOrderByCreateTimeDesc();

    /**
     * 根据名称模糊查询标签
     */
    List<Tag> findByNameContainingOrderByCreateTimeDesc(String name);

    /**
     * 查询使用频率最高的标签
     */
    @Query("SELECT t, COUNT(a) FROM Article a JOIN a.tags t WHERE a.status = 'PUBLISHED' GROUP BY t ORDER BY COUNT(a) DESC")
    List<Object[]> findPopularTags();

    /**
     * 查询指定数量的热门标签
     */
    @Query(value = "SELECT t.* FROM tag t " +
            "JOIN article_tag at ON t.id = at.tag_id " +
            "JOIN article a ON at.article_id = a.id " +
            "WHERE a.status = 'PUBLISHED' " +
            "GROUP BY t.id " +
            "ORDER BY COUNT(a.id) DESC " +
            "LIMIT ?1", nativeQuery = true)
    List<Tag> findTopTags(int limit);
}