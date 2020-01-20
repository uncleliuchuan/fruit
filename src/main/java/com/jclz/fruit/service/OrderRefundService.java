package com.jclz.fruit.service;

import com.jclz.fruit.entity.OrderRefund;

import java.util.Map;

public interface OrderRefundService {
    Map<String, Object> applyOrderRefund(OrderRefund orderRefund);
}
