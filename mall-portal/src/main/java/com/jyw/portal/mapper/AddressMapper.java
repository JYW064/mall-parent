package com.jyw.portal.mapper;

import com.jyw.portal.domain.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    int deleteByPrimaryKey(Long addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long addressId);

    List<Address> selectByUserId(Long userId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
}