package com.example.community.controller;

import com.example.community.entity.Order;
import com.example.community.entity.OrderItem;
import com.example.community.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final IOrderService service;
    public OrderController(IOrderService service) { this.service = service; }

    @PostMapping
    public Order create(@RequestBody OrderRequest req) {
        return service.create(req.toOrder(), req.toItems());
    }

    @GetMapping
    public List<Order> list(@RequestParam(required = false) Long userId,
                            @RequestParam(required = false) Long supplierId) {
        if (userId != null) return service.listByUser(userId);
        if (supplierId != null) return service.listBySupplier(supplierId);
        return List.of();
    }

    @GetMapping("/{id}")
    public Order detail(@PathVariable Long id) { return service.getById(id); }

    @PatchMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id) { service.confirm(id); }

    @PatchMapping("/{id}")
    public void updateStatus(@PathVariable Long id, @RequestBody StatusRequest req) {
        if ("SHIPPED".equals(req.getStatus())) service.ship(id);
    }

    public static class OrderRequest {
        public Long userId;
        public Long leaderId;
        public Long supplierId;
        public java.math.BigDecimal totalAmount;
        public Long addressId;
        public List<OrderItem> items;
        public Order toOrder() {
            Order o = new Order();
            o.setUserId(userId); o.setLeaderId(leaderId);
            o.setSupplierId(supplierId); o.setTotalAmount(totalAmount);
            o.setStatus("CREATED");
            return o;
        }
        public List<OrderItem> toItems() { return items; }
    }
    public static class StatusRequest { public String status; public String getStatus(){return status;} }
}