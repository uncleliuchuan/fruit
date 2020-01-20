package com.jclz.fruit.service;

import com.jclz.fruit.entity.Fruit;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public interface OrderInfoService {
    Map<String, Object> submitOrderInfo(JSONArray jsonArray, Integer userId, Integer addressId,String payType,Integer submitType);

    /**
     * 查询我的全部订单
     * @param userId
     * @return
     */
    Map<String, Object> selectAllOrders(Integer userId);
    /**
     * 查询我的待支付订单
     * @param userId
     * @return
     */
    Map<String, Object> selectPendingOrders(Integer userId);

    /**
     * 查询待发货-待收货-待评价等的订单
     * @param userId
     * @param orderStatus
     * @return
     */
    Map<String, Object> selectOtherOrders(Integer userId, Integer orderStatus);

    Map<String, Object> updateUserAddress(Integer userId, String orderNo, Integer addressId);

    Map<String, Object> deleteOrder(Integer userId, String orderNo);

    /**
     * 待支付订单-订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    Map<String, Object> pendingOrdersDetails(Integer userId, String orderNo);

    /**
     * 订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    Map<String, Object> ordersDetails(Integer userId, String orderNo);

    /**
     * 确认收货
     * @param userId
     * @param orderNo
     * @return
     */
    Map<String, Object> confirmReceipt(Integer userId, String orderNo);

    /**
     * 订单数量
     * @param userId
     * @return
     */
    Map<String, Object> orderNum(Integer userId);

    Map<String, Object> refundAfterAale(Integer userId);
}
