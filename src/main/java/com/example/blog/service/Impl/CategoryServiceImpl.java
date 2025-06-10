package com.example.blog.service.Impl;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.service.CategoryService;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public boolean updateCategory(Long id, String name, String description) {
        return categoryRepository.updateCategory(id, name, description) > 0;
    }

    @Transactional
    @Override
    public boolean deleteCategory(Long id) {
        return categoryRepository.deleteCategories(id) > 0;
    }

    @Override
    public void createCategory(String name, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("分类名称已存在");
        }
        Category category = new Category(name, description);
        categoryRepository.save(category);
    }
}
