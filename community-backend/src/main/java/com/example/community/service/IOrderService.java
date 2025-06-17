// File: src/main/java/com/example/community/service/IOrderService.java
package com.example.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.community.entity.Order;
import com.example.community.entity.OrderItem;

import java.util.List;

public interface IOrderService extends IService<Order> {
    Order create(Order order, List<OrderItem> items);
    List<Order> listByUser(Long userId);
    List<Order> listBySupplier(Long supplierId);
    Order getById(Long id);
    void confirm(Long id);
    void ship(Long id);
    List<Order> listByLeader(Long leaderId); // 按团长ID查询订单
}
