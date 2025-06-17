package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.User;
import com.example.community.mapper.UserMapper;
import com.example.community.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User findByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).one();
    }

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("user");
        save(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return super.getById(id);
    }
}