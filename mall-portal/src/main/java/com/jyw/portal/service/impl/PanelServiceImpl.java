package com.jyw.portal.service.impl;

import com.jyw.portal.domain.Good;
import com.jyw.portal.domain.Panel;
import com.jyw.portal.domain.PanelContent;
import com.jyw.portal.dto.PanelContentDTO;
import com.jyw.portal.dto.PanelDTO;
import com.jyw.portal.mapper.GoodMapper;
import com.jyw.portal.mapper.PanelContentMapper;
import com.jyw.portal.mapper.PanelMapper;
import com.jyw.portal.service.PanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-09
 */
@Service
public class PanelServiceImpl implements PanelService {
    @Autowired
    PanelMapper panelMapper;

    @Autowired
    PanelContentMapper panelContentMapper;

    @Autowired
    GoodMapper goodMapper;

    @Override
    public List<PanelDTO> listPanelDTO() {
        List<Panel> panelList = panelMapper.selectAll(0,1);
        List<PanelDTO> panelDTOList = new ArrayList<>();
        for(Panel panel:panelList){
            PanelDTO panelDTO = new PanelDTO();
            List<PanelContent> panelContentList = panelContentMapper.selectByPanelId(panel.getId());
            List<PanelContentDTO> panelContentDTOList = new ArrayList<>();
            for (PanelContent panelContent:panelContentList){
                PanelContentDTO  panelContentDTO = new PanelContentDTO();
                Good good = goodMapper.selectByPrimaryKey(panelContent.getProductId());
                //设置panelContentDTO属性
                panelContentDTO.setId(panelContent.getId());
                panelContentDTO.setPanelId(panelContent.getPanelId());
                panelContentDTO.setProductId(panelContent.getProductId());
                panelContentDTO.setFullUrl(panelContent.getFullUrl());
                panelContentDTO.setImage(panelContent.getPicUrl());
                panelContentDTO.setPicUrl2(panelContent.getPicUrl2());
                panelContentDTO.setPicUrl3(panelContent.getPicUrl3());
                panelContentDTO.setType(panelContent.getType());
                panelContentDTO.setSortOrder(panelContent.getSortOrder());
                if(good!=null){
                    panelContentDTO.setProductName(good.getTitle());
                    panelContentDTO.setSubTitle(good.getInfo());
                    panelContentDTO.setPrice(good.getPrice());
                }


                panelContentDTOList.add(panelContentDTO);
            }
            //设置panelDTO属性
            panelDTO.setId(panel.getId());
            panelDTO.setName(panel.getName());
            panelDTO.setType(panel.getType());
            panelDTO.setPosition(panel.getPosition());
            panelDTO.setSortOrder(panel.getSortOrder());
            panelDTO.setLimitNum(panel.getLimitNum());
            panelDTO.setPanelContentDTOList(panelContentDTOList);
            panelDTOList.add(panelDTO);
        }
        return panelDTOList;
    }
}
