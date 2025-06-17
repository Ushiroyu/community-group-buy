package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Community;
import com.example.community.mapper.CommunityMapper;
import com.example.community.service.ICommunityService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements ICommunityService {
    @Override
    public List<Community> listAll() {
        return list();
    }
}