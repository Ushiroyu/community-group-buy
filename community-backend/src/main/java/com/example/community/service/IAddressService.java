package com.example.community.service;

import com.example.community.entity.Address;
import java.util.List;

public interface IAddressService {
    List<Address> getByUserId(Long userId);
    Address add(Address address);
    void delete(Long id);
}