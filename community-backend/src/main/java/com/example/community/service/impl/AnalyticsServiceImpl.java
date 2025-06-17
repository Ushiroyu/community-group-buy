package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Order;
import com.example.community.mapper.OrderMapper;
import com.example.community.service.IAnalyticsService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsServiceImpl extends ServiceImpl<OrderMapper, Order> implements IAnalyticsService {
    @Override
    public List<Map<String,Object>> salesOverTime() {
        return this.baseMapper.selectMaps(
                new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Order>()
                        .select("DATE(created_at) as date", "SUM(total_amount) as total")
                        .groupBy("DATE(created_at)")
        );
    }
}