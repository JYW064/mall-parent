package com.jyw.portal.util;

import com.jyw.portal.domain.*;
import com.jyw.portal.dto.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-11
 */
public class DTOUtil {
    public static LoginDTO userToLoginDTO(User user, String token) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser(user);
        loginDTO.setToken(token);
        return loginDTO;
    }

    public static GoodContentDTO goodTOGoodContentDTO(Good good) {
        GoodContentDTO goodContentDTO = new GoodContentDTO();
        goodContentDTO.setProductId(good.getId());
        goodContentDTO.setProductName(good.getTitle());
        //将image中包含多个url，用","隔开，这里需要获取第一个url
        String[] strings = new String[0];
        if (good.getImage() != null) {
            strings = good.getImage().split(",");
        }
        goodContentDTO.setImage(strings[0]);
        goodContentDTO.setPrice(good.getPrice());
        goodContentDTO.setSubTitle(good.getInfo());
        return goodContentDTO;
    }

    public static GoodDTO goodTOGoodDTO(List<Good> goodList, long total) {
        List<GoodContentDTO> goodContentDTOList = new ArrayList<>();
        GoodDTO goodDTO = new GoodDTO();
        for (Good good : goodList) {
            goodContentDTOList.add(goodTOGoodContentDTO(good));
        }
        goodDTO.setGoodContentDTOList(goodContentDTOList);
        goodDTO.setTotal(total);
        return goodDTO;
    }

    public static GoodDetailDTO goodTOGoodDetailDTO(Good good) {
        GoodDetailDTO goodDetailDTO = new GoodDetailDTO();
        goodDetailDTO.setProductId(good.getId());
        goodDetailDTO.setProductName(good.getTitle());
        goodDetailDTO.setSubTitle(good.getInfo());
        goodDetailDTO.setMaxNum(good.getNum());
        goodDetailDTO.setImageBig(good.getImageBig());
        String[] strings = new String[0];
        if (good.getImage() != null) {
            strings = good.getImage().split(",");
        }
        goodDetailDTO.setImageSmall(strings);
        goodDetailDTO.setPrice(good.getPrice());
        return goodDetailDTO;
    }

    public static CartDTO getCartDTO(Good good, Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId(cart.getProductId());
        cartDTO.setNum(cart.getNum());
        cartDTO.setChecked(cart.getChecked());
        //将image中包含多个url，用","隔开，这里需要获取第一个url
        String[] strings = new String[0];
        if (good.getImage() != null) {
            strings = good.getImage().split(",");
        }
        cartDTO.setImage(strings[0]);
        cartDTO.setProductName(good.getTitle());
        cartDTO.setPrice(good.getPrice());
        cartDTO.setMaxNum(good.getNum());
        return cartDTO;
    }

    public static OrderDTO orderToOrderDTO(Order order, List<CartDTO> goodList) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setAddressId(order.getAddressId());
        orderDTO.setTotalPay(order.getTotalPay());
        orderDTO.setCreateTime(formatter.format(order.getCreateTime()));
        orderDTO.setStatus(order.getStatus());
        orderDTO.setGoodList(goodList);
        return orderDTO;
    }

    public static OrderListDTO orderDTOToOrderListDTO(List<OrderDTO> orderDTOList, long total) {
        OrderListDTO orderListDTO = new OrderListDTO();
        orderListDTO.setOrderDTOList(orderDTOList);
        orderListDTO.setTotal(total);
        return orderListDTO;
    }

    public static OrderDetailDTO getOrderDetailDTO(Order order, Address address, List<CartDTO> goodList) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderId(order.getOrderId());
        orderDetailDTO.setTotalPay(order.getTotalPay());
        orderDetailDTO.setStatus(order.getStatus());
        orderDetailDTO.setCreateTime(formatter.format(order.getCreateTime()));
        if(order.getPaymentTime()!=null){
            orderDetailDTO.setPaymentTime(formatter.format(order.getPaymentTime()));
        }
        if(order.getDeliveryTime()!=null){
            orderDetailDTO.setDeliveryTime(formatter.format(order.getDeliveryTime()));
        }
        if(order.getFinishTime()!=null){
            orderDetailDTO.setFinishTime(formatter.format(order.getFinishTime()));
        }
        if(order.getCloseTime()!=null){
            orderDetailDTO.setCloseTime(order.getCloseTime().getTime()+15*60*1000);
        }
        orderDetailDTO.setAddress(address);
        orderDetailDTO.setGoodList(goodList);
        return orderDetailDTO;
    }

    public static CartDTO goodToCartDTO(Good good, int num, long goodId) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId(goodId);
        cartDTO.setProductName(good.getTitle());
        cartDTO.setNum(num);
        cartDTO.setPrice(good.getPrice());
        //将image中包含多个url，用","隔开，这里需要获取第一个url
        String[] strings = new String[0];
        if (good.getImage() != null) {
            strings = good.getImage().split(",");
        }
        cartDTO.setImage(strings[0]);
        return cartDTO;
    }
}
