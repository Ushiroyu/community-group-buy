// File: src/main/java/com/example/community/service/impl/OrderServiceImpl.java
package com.example.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Order;
import com.example.community.entity.OrderItem;
import com.example.community.mapper.OrderItemMapper;
import com.example.community.mapper.OrderMapper;
import com.example.community.service.IOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    private final OrderItemMapper itemMapper;
    private final RabbitTemplate rabbit;

    public OrderServiceImpl(OrderItemMapper itemMapper, RabbitTemplate rabbit) {
        this.itemMapper = itemMapper;
        this.rabbit = rabbit;
    }

    @Override
    @Transactional
    public Order create(Order order, List<OrderItem> items) {
        save(order);
        for (OrderItem it : items) {
            it.setOrderId(order.getId());
            itemMapper.insert(it);
        }
        rabbit.convertAndSend("order.created", order.getId());
        return order;
    }

    @Override
    public List<Order> listByUser(Long userId) {
        return list(new QueryWrapper<Order>().eq("user_id", userId));
    }

    @Override
    public List<Order> listBySupplier(Long supplierId) {
        return list(new QueryWrapper<Order>().eq("supplier_id", supplierId));
    }

    @Override
    public Order getById(Long id) {
        return super.getById(id);
    }

    @Override
    public void confirm(Long id) {
        update(new UpdateWrapper<Order>().set("status", "DELIVERED").eq("id", id));
    }

    @Override
    public void ship(Long id) {
        update(new UpdateWrapper<Order>().set("status", "SHIPPED").eq("id", id));
    }

    @Override
    public List<Order> listByLeader(Long leaderId) {
        return list(new QueryWrapper<Order>().eq("leader_id", leaderId));
    }
}
