package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Leader;
import com.example.community.mapper.LeaderMapper;
import com.example.community.service.ILeaderService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaderServiceImpl extends ServiceImpl<LeaderMapper, Leader> implements ILeaderService {
    @Override public Leader apply(Leader leader) { save(leader); return leader; }
    @Override public List<Leader> listApplications() { return baseMapper.selectList(null); }
    @Override public void updateStatus(Long id, String status) { update(
            new com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper<Leader>().set("status", status).eq("id", id)
    ); }
    @Override public Leader getByUserId(Long userId) { return lambdaQuery().eq(Leader::getUserId, userId).one(); }
}