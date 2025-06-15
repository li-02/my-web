package com.example.blog.controller;

import com.example.blog.dto.response.PageResponse;
import com.example.blog.dto.response.TagWithCountResponse;
import com.example.blog.entity.Tag;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/popular")
    public ApiResponse<List<Tag>> getPopularTags(@RequestParam(value = "limit", required = true) Integer limit) {
        List<Tag> popularTags = tagService.getPopularTags(limit);
        return ApiResponse.success(popularTags);
    }

    @GetMapping("/tags")
    public ApiResponse<PageResponse<TagWithCountResponse>> getAllTags(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<TagWithCountResponse> allTags = tagService.getAllTags(pageable, keyword);
        return ApiResponse.success(PageResponse.of(allTags));
    }

    @GetMapping("/all")
    public ApiResponse<List<Tag>> getAll() {
        List<Tag> tags = tagService.getAll();
        return ApiResponse.success(tags);
    }
    @GetMapping("/search")
    public ApiResponse<List<Tag>> searchTagByName(@RequestParam("keyword") String keyword) {
        List<Tag> tags = tagService.getAll();
        List<Tag> result = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag.getName().contains(keyword)) {
                result.add(tag);
            }
        }
        return ApiResponse.success(result);
    }
    

    @GetMapping("/createTag")
    public ApiResponse<Tag> createTag(@RequestParam("name") String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setUsageCount(0);
        Tag createdTag = tagService.createTag(tag);
        return ApiResponse.success(createdTag);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ApiResponse.success(null);
    }
}
