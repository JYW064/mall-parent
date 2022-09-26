package com.jyw.portal.controller;

import com.jyw.common.annotation.Token;
import com.jyw.common.util.ResultUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.domain.Cart;
import com.jyw.portal.dto.CartDTO;
import com.jyw.portal.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("member")
public class CartController {
    @Autowired
    CartService cartService;

    @Token
    @PostMapping("/cartList")
    public ResultVO getCart(HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        List<CartDTO> cartList = cartService.listCartByUserId(userId);
        return new ResultUtil<>().setData(cartList);
    }

    @Token
    @PostMapping("/addCart")
    public ResultVO addCart(@RequestBody Cart cart,
                            HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        String msg = cartService.addCart(userId, cart.getProductId(), cart.getNum());
        if(msg.equals("")){
            return new ResultUtil<>().setData(null);
        }else{
            return new ResultUtil<>().setErrorMsg(msg);
        }
    }

    @Token
    @PostMapping("/updateCart")
    public ResultVO updateCart(@RequestBody Cart cart,
                               HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        cart.setUserId(userId);
        cartService.updateCart(cart);
        return new ResultUtil<>().setData(null);
    }

    @Token
    @PostMapping("/checkAll")
    public ResultVO checkAll(@RequestBody Cart cart,
                               HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        cart.setUserId(userId);
        cartService.checkAll(cart);
        return new ResultUtil<>().setData(null);
    }

    @Token
    @PostMapping("/delCart")
    public ResultVO delCart(@RequestBody Cart cart,
                            HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        cartService.deleteCart(userId, cart.getProductId());
        return new ResultUtil<>().setData(null);
    }

    @Token
    @PostMapping("/delCartChecked")
    public ResultVO delCartChecked(@RequestBody Cart cart,
                                   HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        cartService.deleteCartChecked(userId);
        return new ResultUtil<>().setData(null);
    }
}
