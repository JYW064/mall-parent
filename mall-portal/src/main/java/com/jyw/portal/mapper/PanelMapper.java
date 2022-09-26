package com.jyw.portal.mapper;

import com.jyw.portal.domain.Panel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PanelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Panel record);

    int insertSelective(Panel record);

    Panel selectByPrimaryKey(Long id);

    List<Panel> selectAll(@Param("position") int position, @Param("status") int status);

    int updateByPrimaryKeySelective(Panel record);

    int updateByPrimaryKey(Panel record);
}