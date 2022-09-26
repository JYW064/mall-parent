package com.jyw.portal.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-09
 */
@Data
public class PanelContentDTO {
    private Long id;

    private Long panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String image;

    private String picUrl2;

    private String picUrl3;

    private BigDecimal price;

    private String productName;

    private String subTitle;


}
