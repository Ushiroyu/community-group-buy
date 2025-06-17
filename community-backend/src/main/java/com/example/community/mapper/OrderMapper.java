package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.entity.Order;
import org.apache.ibatis.annotations.Mapper;

// OrderMapper.java
@Mapper
public interface OrderMapper extends BaseMapper<Order> {}
