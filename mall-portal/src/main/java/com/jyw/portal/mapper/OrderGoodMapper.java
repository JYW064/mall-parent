package com.jyw.portal.mapper;

import com.jyw.portal.domain.OrderGood;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderGoodMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByOrderId(String orderId);

    int insert(OrderGood record);

    int insertSelective(OrderGood record);

    OrderGood selectByPrimaryKey(Long id);

    List<OrderGood> selectByOrderId(String orderId);

    int updateByPrimaryKeySelective(OrderGood record);

    int updateByPrimaryKey(OrderGood record);
}