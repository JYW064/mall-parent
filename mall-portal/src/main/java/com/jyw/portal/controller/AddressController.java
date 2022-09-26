package com.jyw.portal.controller;

import com.jyw.common.annotation.Token;
import com.jyw.common.util.ResultUtil;
import com.jyw.common.vo.ResultVO;
import com.jyw.portal.domain.Address;
import com.jyw.portal.service.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("member")
public class AddressController {
    @Autowired
    AddressServiceImpl addressService;

    @Token
    @PostMapping("/addAddress")
    public ResultVO addAddress(@RequestBody Address address){
        addressService.addAddress(address);
        return new ResultUtil<>().setData(null);
    }

    @Token
    @PostMapping("/getAddress")
    public ResultVO getAddress(HttpServletRequest request){
        long userId = (long) request.getAttribute("userId");
        return new ResultUtil<>().setData(addressService.getAddress(userId));
    }

    @Token
    @PostMapping("/updateAddress")
    public ResultVO updateAddress(@RequestBody Address address){
        addressService.updateAddress(address);
        return new ResultUtil<>().setData(null);
    }

    @Token
    @PostMapping("/deleteAddress")
    public ResultVO deleteAddress(@RequestBody Address address){
        addressService.deleteAddress(address);
        return new ResultUtil<>().setData(null);
    }
}
