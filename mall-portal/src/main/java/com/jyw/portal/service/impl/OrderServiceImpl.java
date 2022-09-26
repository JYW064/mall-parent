package com.jyw.portal.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyw.common.annotation.Retry;
import com.jyw.common.exception.RetryException;
import com.jyw.portal.domain.Address;
import com.jyw.portal.domain.Good;
import com.jyw.portal.domain.Order;
import com.jyw.portal.domain.OrderGood;
import com.jyw.portal.dto.CartDTO;
import com.jyw.portal.dto.OrderDTO;
import com.jyw.portal.dto.OrderDetailDTO;
import com.jyw.portal.dto.OrderListDTO;
import com.jyw.portal.mapper.*;
import com.jyw.portal.service.OrderService;
import com.jyw.portal.util.DTOUtil;
import com.jyw.portal.util.OrderIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderGoodMapper orderGoodMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    GoodMapper goodMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Retry
    @Override
    public String addOrder(OrderDTO orderDTO) throws Exception {
        String orderId = OrderIdUtil.getRandomId();
        BigDecimal totalPay = BigDecimal.ZERO;
        List<CartDTO> cartList = orderDTO.getGoodList();
        for (CartDTO cartDTO : cartList) {
            Good good = goodMapper.selectByPrimaryKey(cartDTO.getProductId());
            if (cartDTO.getNum() > good.getNum()) {
                log.info("订单{}创建失败，{}的库存不够了！", orderId, good.getTitle());
                throw new RuntimeException("库存不够");
            } else {
                good.setNum(good.getNum() - cartDTO.getNum());
                int flag = goodMapper.updateSalesByVersion(good.getNum(), good.getId(), good.getVersion());
                if (flag == 0) {
                    log.info("商品{}库存更新失败，库存已被修改！", good.getTitle());
                    throw new RetryException("库存更新失败！");
                }
                cartMapper.deleteByUserIdAndProductId(orderDTO.getUserId(),cartDTO.getProductId());
            }
            OrderGood orderGood = new OrderGood();
            orderGood.setOrderId(orderId);
            orderGood.setGoodId(cartDTO.getProductId());
            orderGood.setNum(cartDTO.getNum());
            orderGood.setTotalPrice(cartDTO.getPrice().multiply(BigDecimal.valueOf(cartDTO.getNum())));
            totalPay = totalPay.add(orderGood.getTotalPrice());
            orderGoodMapper.insertSelective(orderGood);
        }
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(orderDTO.getUserId());
        order.setAddressId(orderDTO.getAddressId());
        order.setTotalPay(totalPay);
        order.setCreateTime(new Date());
        order.setCloseTime(new Date());
        order.setStatus(0);
        orderMapper.insert(order);
        rabbitTemplate.convertAndSend("xmall.order.direct.exchange", "xmall.order.direct", orderId, message -> {
            //将订单号发送到延迟队列，延迟时间为15分钟
            message.getMessageProperties().setExpiration("900000");
            return message;
        });
        return orderId;
    }


    @Override
    public OrderListDTO getOrder(long userId, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList) {
            List<OrderGood> orderGoodList = orderGoodMapper.selectByOrderId(order.getOrderId());
            List<CartDTO> goodList = new ArrayList<>();
            for (OrderGood orderGood : orderGoodList) {
                long goodId = orderGood.getGoodId();
                Good good = goodMapper.selectByPrimaryKey(goodId);
                goodList.add(DTOUtil.goodToCartDTO(good, orderGood.getNum(), goodId));
            }
            orderDTOList.add(DTOUtil.orderToOrderDTO(order, goodList));
        }
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        return DTOUtil.orderDTOToOrderListDTO(orderDTOList, pageInfo.getTotal());
    }

    @Override
    public OrderDetailDTO getOrderDetail(String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        List<OrderGood> orderGoodList = orderGoodMapper.selectByOrderId(order.getOrderId());
        Address address = addressMapper.selectByPrimaryKey(order.getAddressId());
        List<CartDTO> goodList = new ArrayList<>();
        for (OrderGood orderGood : orderGoodList) {
            long goodId = orderGood.getGoodId();
            Good good = goodMapper.selectByPrimaryKey(goodId);
            goodList.add(DTOUtil.goodToCartDTO(good, orderGood.getNum(), goodId));
        }
        return DTOUtil.getOrderDetailDTO(order,address,goodList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Retry
    @Override
    public void updateOrderStatus(String orderId, int status) throws Exception {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        order.setOrderId(orderId);
        if (status == 1 || status == 2 || status == 3) {
            order.setStatus(status);
        } else if (status == 0 && order.getStatus()==0) {
            order.setStatus(4);
            order.setFinishTime(new Date());
            List<OrderGood> orderGoodList = orderGoodMapper.selectByOrderId(orderId);
            for (OrderGood orderGood : orderGoodList) {
                Good good = goodMapper.selectByPrimaryKey(orderGood.getGoodId());
                good.setNum(good.getNum() + orderGood.getNum());
                int flag = goodMapper.updateSalesByVersion(good.getNum(), good.getId(), good.getVersion());
                if (flag == 0) {
                    log.info("商品{}库存回滚失败，库存已被修改！", good.getTitle());
                    throw new RetryException("库存回滚失败！");
                }
                log.info("回滚成功");
            }
        }
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public void deleteOrder(String orderId){
        orderGoodMapper.deleteByOrderId(orderId);
        orderMapper.deleteByPrimaryKey(orderId);
    }
}