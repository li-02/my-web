package com.example.blog.controller;

import com.example.blog.entity.Category;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "获取所有分类", description = "返回所有未删除的文章分类的列表")
    public ApiResponse<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ApiResponse.success(categories);
    }
}
