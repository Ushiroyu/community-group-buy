package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.entity.Product;
import org.apache.ibatis.annotations.Mapper;

// ProductMapper.java
@Mapper
public interface ProductMapper extends BaseMapper<Product> {}
