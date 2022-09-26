package com.jyw.portal.mapper;

import com.jyw.portal.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    int deleteByUserId(long userId);

    int deleteByUserIdAndProductId(@Param("userId") long userId,@Param("productId") long productId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long id);

    List<Cart> selectByUserId(Long userId);

    Cart selectByUserIDAndProductId(@Param("userId") long userId, @Param("productId") long productId);

    int updateByUserIdSelective(Cart record);

    int updateByUserIdAndProductIdSelective(Cart record);

    int updateByPrimaryKey(Cart record);
}