package com.jyw.portal.controller;

import com.jyw.common.util.ResultUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.dto.GoodDTO;
import com.jyw.portal.dto.GoodDetailDTO;
import com.jyw.portal.service.GoodService;
import com.jyw.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("goods")
public class GoodController {
    @Autowired
    GoodService goodService;

    @Autowired
    SearchService searchService;

    @GetMapping("/getAllGoods")
    public ResultVO listAllGoods(@RequestParam("currentPage") int currentPage,
                                 @RequestParam("pageSize") int pageSize,
                                 @RequestParam(value = "sort",defaultValue = "0") String sort,
                                 @RequestParam(value = "priceMax",defaultValue = "-1") int priceMax,
                                 @RequestParam(value = "priceMin",defaultValue = "-1") int priceMin,
                                 @RequestParam(value = "cid",defaultValue = "0") int cid){
        return new ResultUtil<GoodDTO>().setData(goodService.getGoodDTO(currentPage,pageSize,sort,priceMax,priceMin,cid));
    }

    @GetMapping("/getGoodDetail")
    public ResultVO getGoodDetail(@RequestParam("productId") long productId){
        return new ResultUtil<GoodDetailDTO>().setData(goodService.getGoodDetailDTO(productId));
    }

    @PostMapping("/addGood")
    public ResultVO getGoodDetail() throws IOException {
        searchService.addDocument();
        return new ResultUtil<GoodDTO>().setData(null);
    }

    @GetMapping("/search")
    public ResultVO search(@RequestParam("key") String key,
                           @RequestParam(value = "currentPage",defaultValue = "1")int currentPage,
                           @RequestParam(value = "pageSize",defaultValue = "20")int  pageSize,
                           @RequestParam(value = "priceMax",defaultValue = "-1")int  priceMax,
                           @RequestParam(value = "priceMin"ck) throws IOException {
        if(key==null||key.equals("")){
            return new ResultUtil<GoodDTO>().setData(goodService.getGoodDTO(currentPage,pageSize,sort,priceMax,priceMin,0));
        }
        return new ResultUtil<GoodDTO>().setData(searchService.SearchDocument(key,currentPage,pageSize,priceMax,priceMin,sort,quick));
    }
}
