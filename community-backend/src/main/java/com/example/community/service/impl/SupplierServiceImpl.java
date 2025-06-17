package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Supplier;
import com.example.community.mapper.SupplierMapper;
import com.example.community.service.ISupplierService;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements ISupplierService {
    @Override public Supplier register(Supplier s) { save(s); return s; }
    @Override public Supplier getByUserId(Long userId) { return lambdaQuery().eq(Supplier::getId, userId).one(); }
}