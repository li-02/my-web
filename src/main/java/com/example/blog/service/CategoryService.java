package com.example.blog.service;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryWithCountResponse> getAllCategories(String keyword, Pageable pageable);

    List<Category> getAll();

    boolean updateCategory(Long id, String name, String description);

    boolean deleteCategory(Long id);

    void createCategory(String name, String description);
}
