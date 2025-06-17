package com.example.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.community.entity.Address;
import com.example.community.mapper.AddressMapper;
import com.example.community.service.IAddressService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Override
    public List<Address> getByUserId(Long userId) {
        return lambdaQuery().eq(Address::getUserId, userId).list();
    }

    @Override
    public Address add(Address address) {
        save(address);
        return address;
    }

    @Override
    public void delete(Long id) {
        removeById(id);
    }
}