package com.jyw.portal.mapper;

import com.jyw.portal.domain.PanelContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PanelContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PanelContent record);

    int insertSelective(PanelContent record);

    List<PanelContent> selectByPrimaryKey(Long id);

    List<PanelContent> selectByPanelId(Long panelId);

    int updateByPrimaryKeySelective(PanelContent record);

    int updateByPrimaryKey(PanelContent record);
}