package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@Data
public class AlipaymentOrder {
    private Integer id;
    /**
     * 通知时间 通知时间yyyy-MM-dd HH:mm:ss
     */
    private String notifyTime;
    /**
     * 交易创建时间 交易创建时间:yyyy-MM-dd HH:mm:ss
     */
    private String gmtCreate;
    /**
     * 交易付款时间
     */
    private String gmtPayment;
    /**
     * 交易退款时间 交易退款时间
     */
    private String gmtRefund;
    /**
     * 交易结束时间 交易结束时间
     */
    private String gmtClose;
    /**
     * 支付宝的交易号 支付宝的交易号
     */
    private String tradeNo;
    /**
     * 商户订单号 商户系统的唯一订单号
     */
    private String outTradeNo;
    /**
     * 商户业务号 商户业务ID，主要是退款通知中返回退款申请的流水号
     */
    private String outBizNo;
    /**
     * 买家支付宝账号 买家支付宝账号
     */
    private String buyerLogonId;
    /**
     * 卖家支付宝用户号 卖家支付宝用户号
     */
    private String sellerId;
    /**
     * 订单金额 本次交易支付的订单金额，单位为人民币（元）
     */
    private BigDecimal totalAmount;
    /**
     * 实收金额 商家在交易中实际收到的款项，单位为元
     */
    private BigDecimal receiptAmount;
    /**
     * 开票金额 用户在交易中支付的可开发票的金额
     */
    private BigDecimal invoiceAmount;
    /**
     * 付款金额 用户在交易中支付的金额
     */
    private BigDecimal buyerPayAmount;
    /**
     * 交易状态 TRADE_FINISHED交易结束并不可退款=3
     * TRADE_SUCCESS交易支付成功=2
     * TRADE_CLOSED未付款交易超时关闭或支付完成后全额退款=1
     * WAIT_BUYER_PAY交易创建并等待买家付款=0
     */
    private Integer tradeStatus;
    /**
     * 总退款金额
     */
    private BigDecimal refundFee;
    /**
     * 卖家支付宝账号
     */
    private String sellerEmail;


}