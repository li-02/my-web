package com.example.blog.controller;

import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.entity.Category;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "获取所有分类", description = "分页查找所有分类，支持关键词搜索")
    public ApiResponse<Map<String, Object>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<CategoryWithCountResponse> categoryPage = categoryService.getAllCategories(keyword, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("data", categoryPage.getContent());
        response.put("total", categoryPage.getTotalElements());
        response.put("page", page);
        response.put("pageSize", pageSize);
        response.put("totalPages", categoryPage.getTotalPages());
        return ApiResponse.success(response);
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有分类", description = "获取所有未删除的分类，按创建时间降序排序")
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.success(categoryService.getAll());
    }
}
