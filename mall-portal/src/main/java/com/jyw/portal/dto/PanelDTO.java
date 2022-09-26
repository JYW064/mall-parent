package com.jyw.portal.dto;

import com.jyw.portal.domain.Panel;
import com.jyw.portal.domain.PanelContent;
import javafx.scene.layout.Pane;
import lombok.Data;

import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-09
 */
@Data
public class PanelDTO {
    private Long id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private Integer limitNum;

    private List<PanelContentDTO> panelContentDTOList;
}
