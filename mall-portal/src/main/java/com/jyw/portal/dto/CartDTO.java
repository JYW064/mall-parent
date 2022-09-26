package com.jyw.portal.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-12
 */
@Data
public class CartDTO {
    private Long productId;

    private String productName;

    private Integer num;

    private Integer checked;

    private BigDecimal price;

    private Integer maxNum;

    private String image;
}
