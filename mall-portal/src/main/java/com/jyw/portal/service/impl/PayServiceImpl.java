package com.jyw.portal.service.impl;

import com.alipay.api.AlipayApiException;
import com.jyw.portal.dto.AlipayBean;
import com.jyw.portal.service.PayService;
import com.jyw.portal.util.AlipayUtil;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {

        return AlipayUtil.connect(alipayBean);
    }
}

