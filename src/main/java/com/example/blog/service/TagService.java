package com.example.blog.service;

import com.example.blog.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getPopularTags(Integer limit);

    List<Tag> getAllTags();

    Tag createTag(Tag tag);
}
