package com.example.blog.service;

import com.example.blog.dto.response.TagWithCountResponse;
import com.example.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    List<Tag> getPopularTags(Integer limit);

    Page<TagWithCountResponse> getAllTags(Pageable pageable, String keyword);

    Tag createTag(Tag tag);

    List<Tag> getAll();

    void deleteTag(Long id);
}
