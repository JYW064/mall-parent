package com.jyw.portal.controller;

import com.jyw.common.annotation.Token;
import com.jyw.common.util.ResultUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.domain.Order;
import com.jyw.portal.dto.OrderDTO;
import com.jyw.portal.dto.OrderDetailDTO;
import com.jyw.portal.dto.OrderListDTO;
import com.jyw.portal.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("member")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Token
    @PostMapping("/addOrder")
    public ResultVO addOrder(@RequestBody OrderDTO orderDTO,
                             HttpServletRequest request){
        String msg = "";
        String orderId = "";
        long userId = (long) request.getAttribute("userId");
        orderDTO.setUserId(userId);
        try{
            orderId = orderService.addOrder(orderDTO);
        }catch(Exception e){
            msg = e.getMessage();
        }
        return new ResultUtil<>().setData(orderId);
    }

    @Token
    @GetMapping("/orderList")
    public ResultVO getOrder(@RequestParam("currentPage") int currentPage,
                             @RequestParam("pageSize") int pageSize,
                             HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");
        return new ResultUtil<OrderListDTO>().setData(orderService.getOrder(userId,currentPage,pageSize));
    }

    @Token
    @GetMapping("/getOrderDetail")
    public ResultVO getOrder(@RequestParam("orderId") String orderId){
        return new ResultUtil<OrderDetailDTO>().setData(orderService.getOrderDetail(orderId));
    }

    @Token
    @PostMapping("/updateOrderStatus")
    public ResultVO updateOrderStatus(@RequestBody Order order) throws Exception {
        orderService.updateOrderStatus(order.getOrderId(),order.getStatus());
        return new ResultUtil<>().setData(null);
    }


    @Token
    @PostMapping("/delOrder")
    public ResultVO delOrder(@RequestBody Order order){
        log.info(order.getOrderId());
        orderService.deleteOrder(order.getOrderId());
        return new ResultUtil<>().setData(null);
    }
}
