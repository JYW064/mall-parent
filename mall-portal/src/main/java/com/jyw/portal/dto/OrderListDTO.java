package com.jyw.portal.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-15
 */
@Data
public class OrderListDTO {
    List<OrderDTO> orderDTOList;
    long total;
}
