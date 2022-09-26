package com.jyw.portal.service.impl;


import com.jyw.portal.domain.Address;
import com.jyw.portal.mapper.AddressMapper;
import com.jyw.portal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

    @Override
    public void addAddress(Address address) {
        addressMapper.insertSelective(address);
    }

    @Override
    public List<Address> getAddress(long userId) {
        return addressMapper.selectByUserId(userId);
    }

    @Override
    public void updateAddress(Address address) {
        addressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressMapper.deleteByPrimaryKey(address.getAddressId());
    }
}
