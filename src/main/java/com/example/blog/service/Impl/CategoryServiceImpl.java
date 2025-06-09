package com.example.blog.service.Impl;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<CategoryWithCountResponse> getAllCategories(String keyword, Pageable pageable) {
        return categoryRepository.findCategoriesWithArticleCount(keyword, pageable);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findByDeletedFalseOrderByCreateTimeDesc();
    }
}
