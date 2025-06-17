package com.example.community.service;

import com.example.community.entity.Supplier;

public interface ISupplierService {
    Supplier register(Supplier supplier);
    Supplier getByUserId(Long userId);
}