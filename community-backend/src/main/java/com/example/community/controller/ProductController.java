package com.example.community.controller;

import com.example.community.entity.Product;
import com.example.community.service.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService service;
    public ProductController(IProductService service) { this.service = service; }

    @GetMapping
    public List<Product> list() { return service.listAll(); }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) { return service.getById(id); }
}
