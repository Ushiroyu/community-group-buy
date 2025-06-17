package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("community")
public class Community {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String location;
}