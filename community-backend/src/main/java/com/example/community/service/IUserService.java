package com.example.community.service;

import com.example.community.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IUserService extends IService<User> {
    User findByUsername(String username);
    User register(User user);
    User getById(Long id);
}