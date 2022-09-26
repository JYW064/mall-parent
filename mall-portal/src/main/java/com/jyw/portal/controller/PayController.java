package com.jyw.portal.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jyw.common.annotation.Token;
import com.jyw.common.config.PropertiesConfig;
import com.jyw.portal.dto.AlipayBean;
import com.jyw.portal.service.impl.OrderServiceImpl;
import com.jyw.portal.service.impl.PayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@CrossOrigin
@RestController()
@RequestMapping("member")
public class PayController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    PayServiceImpl payService;

    @PostMapping("/callBack")
    public String alipayNotify(HttpServletRequest request) throws Exception {

        // 获取支付宝的请求信息
        Map<String, String> map = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        if (requestParams.isEmpty()) {
            return "failure";
        }
        // 将 Map<String,String[]> 转为 Map<String,String>
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            map.put(name, valueStr);
        }
        // 验签
        boolean signVerified = AlipaySignature.rsaCheckV1(map, PropertiesConfig.getKey("alipay_public_key"), PropertiesConfig.getKey("charset"),
                PropertiesConfig.getKey("sign_type"));
        // 验签通过
        if (signVerified) {
            System.out.println(map);
            //若支付成功
            if (map.get("trade_status").equals("TRADE_SUCCESS")) {
                //修改订单状态
                orderService.updateOrderStatus(map.get("out_trade_no"),1);
            }
            return "success"; //支付成功后进行操作
        }
        return "failure";
    }


    @Token
    @GetMapping("/alipay")
    public String Alipay(@RequestParam("orderId") String orderId,
                         @RequestParam("orderName") String orderName,
                         @RequestParam("total") BigDecimal total) throws AlipayApiException {

        return payService.aliPay(new AlipayBean()
                .setOut_trade_no(orderId)
                .setTotal_amount(new StringBuffer().append(total))
                .setSubject(orderName));
    }
}