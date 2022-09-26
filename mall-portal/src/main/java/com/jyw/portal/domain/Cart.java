package com.jyw.portal.domain;

import lombok.Data;

@Data
public class Cart {
    private Long id;

    private Long userId;

    private Long productId;

    private Integer num;

    private Integer checked;
}