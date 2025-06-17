package com.example.community.service;

import com.example.community.entity.Leader;
import java.util.List;

public interface ILeaderService {
    Leader apply(Leader leader);
    List<Leader> listApplications();
    void updateStatus(Long id, String status);
    Leader getByUserId(Long userId);
}