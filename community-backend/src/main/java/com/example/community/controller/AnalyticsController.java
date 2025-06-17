package com.example.community.controller;

import com.example.community.service.IAnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final IAnalyticsService service;
    public AnalyticsController(IAnalyticsService s) { this.service = s; }

    @GetMapping("/sales")
    public List<Map<String,Object>> sales() { return service.salesOverTime(); }
}