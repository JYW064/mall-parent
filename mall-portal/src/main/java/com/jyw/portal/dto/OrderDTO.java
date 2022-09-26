package com.jyw.portal.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-14
 */
@Data
public class OrderDTO {
    private String orderId;

    private long userId;

    private long addressId;

    private BigDecimal totalPay;

    private Integer status;

    private String createTime;

    private List<CartDTO> goodList;

    private long total;
}
