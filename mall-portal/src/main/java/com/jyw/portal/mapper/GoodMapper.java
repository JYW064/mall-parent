package com.jyw.portal.mapper;

import com.jyw.portal.domain.Good;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Good record);

    int insertSelective(Good record);

    Good selectByPrimaryKey(Long id);

    List<Good> selectAll(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax);

    List<Good> selectAllOrderByPrice(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax);

    List<Good> selectAllOrderByPriceDesc(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax);

    List<Good> selectAllByCid(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax ,long cid);

    List<Good> selectAllByCidOrderByPrice(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax,long cid);

    List<Good> selectAllByCidOrderByPriceDesc(@Param("priceMin") int priceMin, @Param("priceMax") int priceMax,long cid);

    int updateByPrimaryKeySelective(Good record);

    int updateSalesByVersion(@Param("num") int num,@Param("id") long id,@Param("version") long version);

    int updateByPrimaryKeyWithBLOBs(Good record);

    int updateByPrimaryKey(Good record);
}