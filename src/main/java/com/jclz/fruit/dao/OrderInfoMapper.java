package com.jclz.fruit.dao;

import com.jclz.fruit.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    OrderInfo selectByOrderNo(String orderNo);

    List<OrderInfo> selectByOuterTradeNo(String outerTradeNo);

    int updateOrderStatus(OrderInfo o);

    List<OrderInfo> selectExpiredOrders();

    /**
     *  查询待发货-待收货-待评价等的订单
     * @param userId
     * @return
     */
    List<OrderInfo> selectOrdersByUserId(Integer userId);

    List<OrderInfo> selectOtherOrders(Integer userId, Integer orderStatus);

    /**
     * 查询指定用户各个状态的订单数量
     * @param orderStatus
     * @return
     */
    int selectOrderStatus(Integer userId, Integer orderStatus);

    List<OrderInfo> refundAfterAale(Integer userId);
}