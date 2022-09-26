package com.jyw.portal.dto;

import com.jyw.portal.domain.Address;
import com.jyw.portal.domain.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-17
 */
@Data
public class OrderDetailDTO {
    private String orderId;

    private BigDecimal totalPay;

    private String paymentTime;

    private String deliveryTime;

    private String finishTime;

    private Long closeTime;

    private String createTime;

    private Integer status;

    private Address address;

    private List<CartDTO> goodList;
}
