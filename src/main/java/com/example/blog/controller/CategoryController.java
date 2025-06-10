package com.example.blog.controller;

import com.example.blog.dto.request.CreateCategoryRequest;
import com.example.blog.dto.request.UpdateCategoryRequest;
import com.example.blog.dto.response.CategoryWithCountResponse;
import com.example.blog.dto.response.PageResponse;
import com.example.blog.entity.Category;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    @Operation(summary = "获取所有分类", description = "分页查找所有分类，支持关键词搜索")
    public ApiResponse<PageResponse<CategoryWithCountResponse>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<CategoryWithCountResponse> categoryPage = categoryService.getAllCategories(keyword, pageable);
        return ApiResponse.success(PageResponse.of(categoryPage));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有分类", description = "获取所有未删除的分类，按创建时间降序排序")
    public ApiResponse<List<Category>> getAllCategories() {
        return ApiResponse.success(categoryService.getAll());
    }

    @PutMapping("/update")
    @Operation(summary = "更新分类", description = "根据ID更新分类信息")
    public ApiResponse<Boolean> updateCategory(@Valid @RequestBody UpdateCategoryRequest updateCategoryRequest) {

        boolean result = categoryService.updateCategory(updateCategoryRequest.getId(), updateCategoryRequest.getName(), updateCategoryRequest.getDescription());
        if (result) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error("更新分类失败，可能是分类不存在或数据未更改");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除分类", description = "根据ID删除分类")
    public ApiResponse<Boolean> deleteCategory(@PathVariable Long id) {
        boolean result = categoryService.deleteCategory(id); // 传入null表示不更新名称和描述
        if (result) {
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error("删除分类失败，可能是分类不存在");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "创建分类", description = "创建新的分类")
    public ApiResponse<Boolean> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        try {
            categoryService.createCategory(createCategoryRequest.getName(), createCategoryRequest.getDescription());
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
        return ApiResponse.success(true);
    }
}
