package com.jyw.portal.service;
import com.jyw.portal.dto.GoodDTO;
import com.jyw.portal.dto.GoodDetailDTO;


import java.util.List;

public interface GoodService {
    GoodDTO getGoodDTO(int pageNum, int pageSize,String sort,int priceMax,int priceMin,long cid);

    GoodDetailDTO  getGoodDetailDTO(long productId);
}

