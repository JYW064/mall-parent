package com.jyw.portal.service;


import com.jyw.portal.domain.Address;

import java.util.List;

public interface AddressService {
    void addAddress(Address address);
    List<Address> getAddress(long userId);
    void updateAddress(Address address);
    void deleteAddress(Address address);
}
