package com.example.blog.controller;

import com.example.blog.entity.Tag;
import com.example.blog.response.ApiResponse;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse<List<Tag>> getAllTags() {
        List<Tag> allTags = tagService.getAllTags();
        return ApiResponse.success(allTags);
    }

    /**
     * 根据名称模糊查询标签
     *
     * @param keyword 标签名称
     * @return 标签列表
     */
    @GetMapping("/search")
    public ApiResponse<List<Tag>> searchTagByName(@RequestParam("keyword") String keyword) {
        List<Tag> tags = tagService.getAllTags();
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
        Tag createdTag = tagService.createTag(tag);
        return ApiResponse.success(createdTag);
    }
}
