package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long leaderId;
    private Long supplierId;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;
}
