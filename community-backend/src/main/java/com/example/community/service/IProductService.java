package com.example.community.service;

import com.example.community.entity.Product;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface IProductService extends IService<Product> {
    List<Product> listAll();
    Product getById(Long id);
}