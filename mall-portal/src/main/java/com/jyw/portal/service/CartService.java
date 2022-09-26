package com.jyw.portal.service;


import com.jyw.portal.domain.Cart;
import com.jyw.portal.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> listCartByUserId(long userId);

    String addCart(long userId, long productId, int num);

    Cart isExist(long userId, long productId);

    void updateCart(Cart cart);

    void checkAll(Cart cart);

    void deleteCart(long userId, long productId);

    void deleteCartChecked(long userId);

}
