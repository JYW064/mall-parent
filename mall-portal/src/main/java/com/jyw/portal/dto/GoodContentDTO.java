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
public class GoodContentDTO {
    private long productId;

    private String productName;

    private String image;

    private BigDecimal price;

    private String subTitle;
}
