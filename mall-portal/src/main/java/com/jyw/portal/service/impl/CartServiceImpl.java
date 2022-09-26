package com.jyw.portal.service.impl;


import com.jyw.portal.domain.Cart;
import com.jyw.portal.domain.Good;
import com.jyw.portal.dto.CartDTO;
import com.jyw.portal.mapper.CartMapper;
import com.jyw.portal.mapper.GoodMapper;
import com.jyw.portal.service.CartService;
import com.jyw.portal.util.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    GoodMapper goodMapper;

    @Override
    public List<CartDTO> listCartByUserId(long userId) {
       List<Cart> cartList = cartMapper.selectByUserId(userId);
       List<CartDTO> cartDTOList = new ArrayList<>();
       for(Cart cart:cartList){
           long productId = cart.getProductId();
           Good good = goodMapper.selectByPrimaryKey(productId);
           cartDTOList.add(DTOUtil.getCartDTO(good,cart));
       }
       return cartDTOList;
    }

    @Override
    public String addCart(long userId, long productId, int num) {
        Good good = goodMapper.selectByPrimaryKey(productId);
        Cart cart = isExist(userId,productId);
        int oldNum = cart==null?0:cart.getNum();
        String msg = "";
        if(good.getNum()>=num+oldNum){
            if(cart==null){
                cart = new Cart();
                cart.setUserId(userId);
                cart.setProductId(productId);
                cart.setChecked(1);
                cart.setNum(num);
                cartMapper.insert(cart);
            }else{
                cart.setNum(num+cart.getNum());
                cartMapper.updateByUserIdAndProductIdSelective(cart);
            }
        }else{
            msg = "商品已售罄！";
        }
        return  msg;
    }

    @Override
    public Cart isExist(long userId, long productId) {
        Cart cart = cartMapper.selectByUserIDAndProductId(userId,productId);
        return cart;
    }

    @Override
    public void updateCart(Cart cart) {
        cartMapper.updateByUserIdAndProductIdSelective(cart);
    }

    @Override
    public void checkAll(Cart cart) {
        cartMapper.updateByUserIdSelective(cart);
    }

    @Override
    public void deleteCart(long userId, long productId) {
        cartMapper.deleteByUserIdAndProductId(userId,productId);
    }

    @Override
    public void deleteCartChecked(long userId) {
        cartMapper.deleteByUserId(userId);
    }
}

