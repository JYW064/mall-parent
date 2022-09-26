package com.jyw.portal.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-10
 */
@Data
public class GoodDTO {
    private List<GoodContentDTO> goodContentDTOList;

    private long total;
}
