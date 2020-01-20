package com.jclz.fruit.service;

import com.jclz.fruit.entity.AlipaymentOrder;

import java.util.Map;

public interface AlipayOrderService {
    Map<String, Object> alipayFruit(int userId, String orderNo);

    int createAlipayMentOrder(AlipaymentOrder alipaymentOrder);

    Map<String, Object> alipayVipOrder(int userId, Integer fee, Integer vipType);

    String notify(Map<String, String> conversionParams);

    AlipaymentOrder selectByOutTradeNo(String outTradeNo);

    int updateByPrimaryKey(AlipaymentOrder alipaymentOrder);


    Map<String, Object> checkAlipay(String outTradeNo);

    String refound(String outTradeNo,String outRequestNo,String refundAmount);
}
