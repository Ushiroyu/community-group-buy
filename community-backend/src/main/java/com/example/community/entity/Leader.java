package com.example.community.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("leader")
public class Leader {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long communityId;
    private String status;
    private LocalDateTime applyTime;
}