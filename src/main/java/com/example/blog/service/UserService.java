package com.example.blog.service;

import com.example.blog.entity.User;

import java.util.Optional;

public interface UserService {
    /**
     * 根据用户名查找用户
     */
    Optional<User> findByUsername(String username);

    /**
     * 更新用户最后登录时间
     */
    void updateLastLoginTime(String username);


}
