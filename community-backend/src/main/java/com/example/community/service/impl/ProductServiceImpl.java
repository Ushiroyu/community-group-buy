package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Product;
import com.example.community.mapper.ProductMapper;
import com.example.community.service.IProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Override public List<Product> listAll() { return list(); }
    @Override @Cacheable(value="product", key="#id") public Product getById(Long id) {
        return super.getById(id);
    }
}