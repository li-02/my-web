package com.example.blog.service.Impl;

import com.example.blog.dto.response.TagWithCountResponse;
import com.example.blog.entity.Tag;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    @Override
    public List<Tag> getPopularTags(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return tagRepository.findPopularTags(pageable);
    }

    @Override
    public Page<TagWithCountResponse> getAllTags(Pageable pageable, String keyword) {
        return tagRepository.findTagsWithArticleCount(pageable, keyword);
    }

    @Override
    public Tag createTag(Tag tag) {
        if (tag.getName() == null || tag.getName().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be null or empty.");
        }
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Tag with name '" + tag.getName() + "' already exists.");
        }
        return tagRepository.save(tag);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findByDeletedFalseOrderByCreateTime();
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + id));
        // 检查tag是否被article使用
        if (tag.getUsageCount() > 0) {
            throw new IllegalArgumentException("Tag cannot be deleted because it is in use by articles.");
        }
        tag.setDeleted(true);
        tagRepository.save(tag);
    }

}
