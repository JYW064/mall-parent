package com.jyw.portal.service;


import com.jyw.portal.dto.OrderDTO;
import com.jyw.portal.dto.OrderDetailDTO;
import com.jyw.portal.dto.OrderListDTO;

public interface OrderService {
    String addOrder(OrderDTO orderDTO) throws Exception;
    OrderListDTO getOrder(long userId, int currentPage, int pageSize);
    OrderDetailDTO getOrderDetail(String orderId);
    void updateOrderStatus(String orderId,int status) throws Exception;
    void deleteOrder(String orderId);
}
