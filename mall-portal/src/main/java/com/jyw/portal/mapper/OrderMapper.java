package com.jyw.portal.mapper;

import com.jyw.portal.domain.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    List<Order> selectByUserId(long userId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}