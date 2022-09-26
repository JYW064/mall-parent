package com.jyw.portal.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyw.portal.domain.Good;
import com.jyw.portal.dto.GoodDTO;
import com.jyw.portal.dto.GoodDetailDTO;
import com.jyw.portal.mapper.GoodMapper;
import com.jyw.portal.service.GoodService;

import com.jyw.portal.service.SearchService;
import com.jyw.portal.util.DTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class GoodServiceImpl implements GoodService {
    @Autowired
    GoodMapper goodMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SearchService searchService;

    @Override
    public GoodDTO getGoodDTO(int pageNum, int pageSize,String sort,int priceMax,int priceMin,long cid) {
        String key = "cid"+cid+":"+"cur"+pageNum+"from"+priceMin+"to"+priceMax+"sort"+sort+"size"+pageSize;
        if(redisTemplate.hasKey(key)){
            log.info("缓存命中");
            return (GoodDTO)redisTemplate.opsForValue().get(key);
        }else {
            PageHelper.startPage(pageNum, pageSize);
            List<Good> goodList;
            if (cid == 0) {
                if(sort.equals("1")){
                    goodList = goodMapper.selectAllOrderByPrice(priceMin,priceMax);
                }else if(sort.equals("-1")){
                    goodList = goodMapper.selectAllOrderByPriceDesc(priceMin,priceMax);
                }else{
                    goodList = goodMapper.selectAll(priceMin,priceMax);
                }
            }else {
                if(sort.equals("1")){
                    goodList = goodMapper.selectAllByCidOrderByPrice(priceMin,priceMax,cid);
                }else if(sort.equals("-1")){
                    goodList = goodMapper.selectAllByCidOrderByPriceDesc(priceMin,priceMax,cid);
                }else{
                    goodList = goodMapper.selectAllByCid(priceMin,priceMax,cid);
                }
            }
            PageInfo<Good> pageInfo = new PageInfo<>(goodList);
            GoodDTO goodDTO = DTOUtil.goodTOGoodDTO(goodList,pageInfo.getTotal());

            redisTemplate.opsForValue().set(key,goodDTO);
            redisTemplate.expire(key,1, TimeUnit.HOURS);
        }
        return (GoodDTO)redisTemplate.opsForValue().get(key);
    }

    @Override
    public GoodDetailDTO getGoodDetailDTO(long productId) {
        if(redisTemplate.hasKey("productDetail"+":"+productId)){
            log.info("缓存命中");
            return (GoodDetailDTO)redisTemplate.opsForValue().get("productDetail"+":"+productId);
        }else {
            Good good = goodMapper.selectByPrimaryKey(productId);
            GoodDetailDTO goodDetailDTO = DTOUtil.goodTOGoodDetailDTO(good);
            redisTemplate.opsForValue().set("productDetail"+":"+productId,goodDetailDTO);
            redisTemplate.expire("productDetail",1, TimeUnit.HOURS);
        }
        return (GoodDetailDTO)redisTemplate.opsForValue().get("productDetail"+":"+productId);
    }
}
