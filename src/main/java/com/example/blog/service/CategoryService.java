package com.example.blog.service;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryWithCountResponse> getAllCategories(String keyword, Pageable pageable);

    List<Category> getAll();
}
