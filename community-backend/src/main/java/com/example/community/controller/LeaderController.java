package com.example.community.controller;

import com.example.community.entity.Leader;
import com.example.community.service.ILeaderService;
import com.example.community.service.IOrderService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leader")
public class LeaderController {
    private final ILeaderService leaderService;
    private final IOrderService orderService;

    public LeaderController(ILeaderService leaderService, IOrderService orderService) {
        this.leaderService = leaderService;
        this.orderService = orderService;
    }

    @PostMapping("/apply")
    public Leader apply(@RequestBody Leader leader) {
        return leaderService.apply(leader);
    }

    @GetMapping("/applications")
    public List<Leader> applications() {
        return leaderService.listApplications();
    }

    @PostMapping("/applications/{id}/approve")
    public void approve(@PathVariable Long id, @RequestBody ApproveRequest req) {
        String status = req.approve ? "APPROVED" : "REJECTED";
        leaderService.updateStatus(id, status);
    }

    @GetMapping("/community")
    public Leader community(@RequestParam Long userId) {
        return leaderService.getByUserId(userId);
    }

    @PutMapping("/community")
    public Leader updateCommunity(@RequestBody Leader leader) {
        return leaderService.apply(leader);
    }

    @GetMapping("/stats")
    public Stats stats(@RequestParam Long leaderId) {
        long total = orderService.listByLeader(leaderId).size();
        long completed = orderService.listByLeader(leaderId).stream()
                .filter(o -> "DELIVERED".equals(o.getStatus()))
                .count();
        Stats stats = new Stats();
        stats.setTotalOrders(total);
        stats.setCompleted(completed);
        return stats;
    }

    @Data
    public static class ApproveRequest {
        private boolean approve;
    }

    @Data
    public static class Stats {
        private long totalOrders;
        private long completed;
    }
}
