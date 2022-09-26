package com.jyw.portal.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-11
 */
@Data
public class GoodDetailDTO {
    private Long productId;

    private BigDecimal price;

    private String productName;

    private String subTitle;

    private String imageBig;

    private  String[] imageSmall;

    private int maxNum;
}
