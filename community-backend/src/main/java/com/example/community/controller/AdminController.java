// File: src/main/java/com/example/community/controller/AdminController.java
package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.entity.Product;
import com.example.community.entity.Order;
import com.example.community.service.IUserService;
import com.example.community.service.IProductService;
import com.example.community.service.IOrderService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final IUserService userService;
    private final IProductService productService;
    private final IOrderService orderService;

    public AdminController(IUserService userService,
                           IProductService productService,
                           IOrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.list();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.removeById(id);
    }

    @GetMapping("/products/pending")
    public List<Product> pendingProducts() {
        return productService.listAll().stream()
                .filter(p -> p.getApproved() == null || !p.getApproved())
                .toList();
    }

    @PostMapping("/products/{id}/approve")
    public void approveProduct(@PathVariable Long id, @RequestBody ApproveRequest req) {
        Product p = productService.getById(id);
        p.setApproved(req.approve);
        productService.updateById(p);
    }

    @GetMapping("/orders")
    public List<Order> orders() {
        return orderService.list();
    }

    @Data
    public static class ApproveRequest {
        private boolean approve;
    }
}
