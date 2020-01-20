package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Data
public class OrderRefund {
    private Integer id;

    private String orderNo;//订单编号

    private String outRefundNo;//商户退款单号

    private BigDecimal refundFee;//退款金额

    private String createTime;//申请时间

    private Integer isAgree;//是否同意退款 0-拒绝 /1-同意

    private String fruitStatus;//货物状态

    private String refundReason;//退款原因

    private String refundInstruction;//退款说明

    private String refundUrl;//退款凭证

    private Integer userId;//用户ID


   }