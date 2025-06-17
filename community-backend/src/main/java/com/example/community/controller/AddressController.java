package com.example.community.controller;

import com.example.community.entity.Address;
import com.example.community.service.IAddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{uid}/addresses")
public class AddressController {
    private final IAddressService service;
    public AddressController(IAddressService s){this.service=s;}

    @GetMapping
    public List<Address> list(@PathVariable Long uid){ return service.getByUserId(uid); }

    @PostMapping
    public Address add(@PathVariable Long uid, @RequestBody Address a){ a.setUserId(uid); return service.add(a); }

    @DeleteMapping("/{id}")
    public void del(@PathVariable Long uid, @PathVariable Long id){ service.delete(id); }
}