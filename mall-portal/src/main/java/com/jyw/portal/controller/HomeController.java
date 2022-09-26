package com.jyw.portal.controller;


import com.jyw.common.annotation.Token;
import com.jyw.common.util.ResultUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.dto.PanelDTO;
import com.jyw.portal.service.PanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("goods")
public class HomeController {
    @Autowired
    PanelService panelService;

    @GetMapping("/home")
    public ResultVO getHome(){
        return new ResultUtil<List<PanelDTO>>().setData(panelService.listPanelDTO());
    }
}

