package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Data
public class VipRechargeLog {
    private Integer id;
    /**
     * 订单单号
     */
    private String orderNo;
    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;
    /**
     * 订单状态 待付款0,已付款1，付款失败-1
     */
    private Integer orderStatus;
    /**
     * 订单创建时间
     */
    private String createTime;
    /**
     * 创建用户
     */
    private Integer createUser;
    /**
     * vip类型 1-月度，2季度，3年度
     */
    private Integer vipType;


}