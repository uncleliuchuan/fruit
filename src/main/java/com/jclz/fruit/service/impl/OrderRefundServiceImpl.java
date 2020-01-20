package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.service.OrderInitializingBean;
import com.jclz.fruit.service.OrderRefundService;
import com.jclz.fruit.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService {
    @Autowired
    OrderRefundMapper orderRefundMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Override
    public Map<String, Object> applyOrderRefund(OrderRefund orderRefund) {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
        if (null==orderInfo){
            return GenResult.FAILED.genResult("该订单不存在，无法退款！！！");
        }
        if (orderInfo.getOrderStatus()!=1&&orderInfo.getOrderStatus()!=3){
            return GenResult.FAILED.genResult("订单只在未发货时和确认收货未评价时允许申请退款！！！");
        }
        OrderRefund old = orderRefundMapper.selectByOrderNo(orderRefund.getOrderNo());
        if (null!=old){
            return GenResult.FAILED.genResult("您已申请退款，不能重复申请！！！");
        }
        orderRefund.setCreateTime(DateUtil.getTime());
        int row = orderRefundMapper.insertSelective(orderRefund);
        if (row>0){
            if (orderInfo.getOrderStatus()==1){//待发货时退款
                orderInfo.setOrderStatus(5);
                orderInfoMapper.updateOrderStatus(orderInfo);
                OrderInitializingBean.orderRefundDelayQueueThread.add(orderRefund.getOrderNo());//自动退款

            }
            if (orderInfo.getOrderStatus()==3){//确认收货时退款
                orderInfo.setOrderStatus(6);
                orderInfoMapper.updateOrderStatus(orderInfo);
                OrderInitializingBean.fruitCommentsDelayQueueThread.removeDelay(orderInfo);//把3天自动评价删除
            }
        }
        return GenResult.SUCCESS.genResult();
    }
}
