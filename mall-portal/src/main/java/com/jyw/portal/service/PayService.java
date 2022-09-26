package com.jyw.portal.service;

import com.alipay.api.AlipayApiException;
import com.jyw.portal.dto.AlipayBean;

public interface PayService {
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}
