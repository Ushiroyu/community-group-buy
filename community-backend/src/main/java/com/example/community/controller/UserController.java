package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.service.IUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;
    public UserController(IUserService s) { this.userService = s; }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) { return userService.getById(id); }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User u) {
        u.setId(id);
        userService.updateById(u);
        return u;
    }
}
