package com.jclz.fruit.wxpay.service;


import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.*;
import com.jclz.fruit.entity.*;
import com.jclz.fruit.service.UserIntegralService;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.wxpay.util.DateUtil;
import com.jclz.fruit.wxpay.util.HttpXmlUtil;
import com.jclz.fruit.wxpay.util.OrderCodeFactory;
import com.jclz.fruit.wxpay.util.WxPayCore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 处理微信支付的业务层
 */
@Slf4j
@Service
public class WxPayService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    VipRechargeLogMapper vipRechargeLogMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    UserIntegralService userIntegralService;
    @Autowired
    OrderRefundMapper orderRefundMapper;
    /**
     * 将支付信息插入到数据库中，与微信进行交互，返回微信值
     *
     * @param userId
     * @param orderNo
     */
    @Transactional
    public Map<String, Object> payBefore(Integer userId, String orderNo) {
        //生成微信订单号
//        String order = OrderCodeFactory.getOrderCode((long)userId);
        String order = "fruit" + UUID.randomUUID().toString().substring(0,27);
        DecimalFormat df = new DecimalFormat("#0.00"); //用于格式化充值金额

        String total_fee = "";//充值所耗费的rmb   单位为元
        total_fee = df.format(0.010001);
        String fruit_total_fee =df.format(0.00);
        //查询redis里缓存是否存在订单（即订单是否在有效期内）
        if (orderNo.contains("list:")){//购物车结算包含多个订单
            List<OrderInfo> list=redisTemplate.opsForList().range(orderNo, 0, -1);
            if (list.isEmpty()) {//订单失效
                return GenResult.ORDER_EXPIRED.genResult();
            }
            total_fee=df.format(0.01*list.size());
            for (OrderInfo orderInfo: list) {//购物车结算计算总金额
                fruit_total_fee=df.format(new BigDecimal(fruit_total_fee).add(orderInfo.getFruitAmountTotal()));
                OrderInfo old=  orderInfoMapper.selectByOrderNo(orderInfo.getOrderNo());
                if (null==old){//此订单尚未入库
                    orderInfo.setOuterTradeNo(order);

                    //测试修改为0.01
                    orderInfo.setFruitAmountTotal(new BigDecimal(0.01));

                    orderInfoMapper.insertSelective(orderInfo);//订单入库
                }else {
                    old.setOuterTradeNo(order);

                    //测试修改为0.01
                    old.setFruitAmountTotal(new BigDecimal(0.01));

                    orderInfoMapper.updateByPrimaryKey(old);//订单已入库则更新
                }
            }
        }else {//不是list即为普通订单直接计算即可
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(orderNo);
            if (null == orderInfo) {//订单失效
                /**
                 * 此情况为购物车结算多个水果，调起支付后多个订单已入库
                 * 然后取消支付回到APP，依次支付每个订单
                 */
                OrderInfo old=  orderInfoMapper.selectByOrderNo(orderNo);
                if (null==old){//此订单尚未入库.即为订单已失效
                    return GenResult.ORDER_EXPIRED.genResult();
                }
                old.setOuterTradeNo(order);

                //测试修改为0.01
                old.setFruitAmountTotal(new BigDecimal(0.01));

                orderInfoMapper.updateByPrimaryKey(old);//订单已入库则更新
                fruit_total_fee=df.format(old.getFruitAmountTotal());
            }else {
                fruit_total_fee=df.format(orderInfo.getFruitAmountTotal());
                OrderInfo old=  orderInfoMapper.selectByOrderNo(orderInfo.getOrderNo());
                if (null==old){//此订单尚未入库
                    orderInfo.setOuterTradeNo(order);

                    //测试修改为0.01
                    orderInfo.setFruitAmountTotal(new BigDecimal(0.01));

                    orderInfoMapper.insertSelective(orderInfo);//订单入库
                }else {
                    old.setOuterTradeNo(order);

                    //测试修改为0.01
                    old.setFruitAmountTotal(new BigDecimal(0.01));

                    orderInfoMapper.updateByPrimaryKey(old);//订单已入库则更新
                }
            }

        }

        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		自身业务结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
        //	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼拼接微信所需参数，一般情况下无需修改 ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
        /**
         * @param order    String	传入微信订单号，必须唯一；
         * @param total_fee    String	所要支付的金额，格式必须为0.00，单位为元。
         */
        String orderType = "fruit";
        //测试： --total_fee
        //正式：--fruit_total_fee
        SortedMap<Object, Object> wxAppparameters = WxPayCore.createWxPayParam(order, total_fee, orderType);

        if (wxAppparameters != null) {
            /**
             * 以resultdata对象的形式给App返回
             */
            wxAppparameters.put("out_trade_no", order);
            return GenResult.SUCCESS.genResult(wxAppparameters);
        } else {
            return GenResult.UNKNOWN_ERROR.genResult("微信支付，拼接参数交易金额有误！请重新进入充值界面后再试！");
        }
        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		拼接微信所需参数，一般情况下无需修改		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
    }

    /**
     处理微信异步回调的业务
     * 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
     * 对后台通知交互时，如果微信收到商户的应答不是成功或超时，
     * 微信认为通知失败，微信会通过一定的策略定期重新发起通知，
     * 尽可能提高通知的成功率，但微信不保证通知最终能成功。
     * （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒）
     * 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
     * 推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，
     * 判断该通知是否已经处理过，如果没有处理过再进行处理，
     * 如果处理过直接返回结果成功。
     * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证，防止数据泄漏导致出现“假通知”，造成资金损失。
     *
     * @param request
     * @param out
     */
    @Transactional
    public void payAfter(HttpServletRequest request, PrintWriter out) {
        //	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		验证签名、基础参数是否是微信发来的，一般情况下无需修改		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
        log.info("微信回调" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SortedMap<Object, Object> parameters = null;

        try {
            parameters = WxPayCore.checkWxPayParam(request);
        } catch (Exception e) {
            log.error("验证微信异步回调出错，错误为：" ,e);
        }

        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		验证结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲


        //	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		请抒写自身验证以及支付成功的业务		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼

        if (parameters != null) {
            out.print(HttpXmlUtil.backWeixin("SUCCESS", "OK"));//没有分销
            String return_code = parameters.get("return_code").toString();//返回状态码
            if ("SUCCESS".equals(return_code)) {
                String out_refund_no = "";
                if (parameters.containsKey("out_refund_no")) {
                    out_refund_no = parameters.get("out_refund_no").toString();//商户退款单号
                }
                String out_trade_no = parameters.get("out_trade_no").toString();
                if (!"".equals(out_refund_no) && null != out_refund_no) {
                    //商户退款单号不为空则改变当前活动报名订单状态为已退款
                    if (out_trade_no.startsWith("fruit")) {
                        List<OrderInfo> list= orderInfoMapper.selectByOuterTradeNo(out_trade_no);
                        if (list.isEmpty()) {//入库订单不存在，除非前端操作越过了入库
                            log.error("订单不存在:" + out_trade_no + "   请查询备份数据库！！！！！！！！！");
                        } else {
                            //修改订单状态为已退款
                            OrderRefund orderRefund = orderRefundMapper.selectByOutRefundNo(out_refund_no);
                            OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
                            if (orderInfo.getOrderStatus()==5||orderInfo.getOrderStatus()==6){
                                orderInfo.setOrderStatus(7);//已退款
                                orderInfoMapper.updateOrderStatus(orderInfo);
                            }

                        }
                    }
                } else {

                    if (out_trade_no.startsWith("vip")) {
  // 	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		vip订单支付成功逻辑		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
                        VipRechargeLog vipRechargeLog= vipRechargeLogMapper.selectByOrderNo(out_trade_no);
                        if (vipRechargeLog != null) {
                            redisTemplate.delete(out_trade_no);
                            if (vipRechargeLog.getOrderStatus() != 1) {
                                vipRechargeLog.setOrderStatus(1);
                                vipRechargeLogMapper.updateOrderStatus(vipRechargeLog);
                                User user = userMapper.selectByPrimaryKey(vipRechargeLog.getCreateUser());
                                String vipOutTime = user.getVipOutTime();
                                String vipTime = "";
                                String nowtime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                                if (!StringUtils.isNotBlank(vipOutTime)) {
                                    vipTime = nowtime;
                                } else {
                                    try {
                                        if (new Date().after(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime))) {
                                            vipTime = nowtime;
                                        } else {
                                            vipTime = vipOutTime;
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                String temp = "";
                                System.out.println("时间：" + vipTime);
                                if (vipRechargeLog.getVipType() == 1) {//判断vip充值类型
                                    temp = DateUtil.getNextMonthTime(vipTime);
                                } else if (vipRechargeLog.getVipType()  == 2) {
                                    temp = DateUtil.getNextSseason(vipTime);
                                } else if (vipRechargeLog.getVipType()  == 3) {
                                    temp = DateUtil.getNextYear(vipTime);
                                }
                                boolean isVip = userService.isVIP(vipRechargeLog.getCreateUser());
                                if (!isVip){//如果不是VIP则覆盖用户的vip充值时间
                                    user.setVipTime(nowtime);
                                }
                                user.setVipOutTime(temp);
                                user.setRoleId(1);
                                userMapper.updateVip(user);
                            }
                        } else {
                            log.error("数据库中无此订单：" + out_trade_no + "   请查询备份数据库！！！！！！！！！");
                        }
    //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		vip支付回调结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

                    } else  if (out_trade_no.startsWith("fruit")){
      // 	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		水果订单支付成功逻辑		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
                        List<OrderInfo> list=orderInfoMapper.selectByOuterTradeNo(out_trade_no);
                        if (list.size()==0) {//入库订单不存在，除非前端操作越过了入库
                            log.error("订单不存在:" + out_trade_no + "   请查询备份数据库！！！！！！！！！");
                        } else {
                            //修改订单状态
                            //查询数据库中是否有多个订单，，即是否购物车结算逻辑
                            if (list.size()>1){
                                for (OrderInfo o: list) {
                                    if (o.getOrderStatus()!= 1) {
                                        redisTemplate.delete("list:"+o.getOrderNo());
                                        o.setOrderStatus(1);//支付成功即为待发货状态
                                        o.setPayType("weixin");
                                        o.setPayTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                                        o.setPayAmount(o.getFruitAmountTotal());
                                        orderInfoMapper.updateOrderStatus(o);
                                        Fruit fruit = fruitMapper.selectByPrimaryKey(o.getFruitId());
                                        fruit.setFruitAcceptedNum(fruit.getFruitAcceptedNum()+o.getFruitCount());
                                        fruitMapper.updateQuantitySold(fruit);
                                        UserIntegral userIntegral =new UserIntegral();
                                        userIntegral.setIntegral(o.getFruitAmountTotal().intValue());
                                        userIntegral.setUserId(o.getCreateUser());
                                        userIntegralService.insertOrUpdate(userIntegral);
                                    }
                                }

                            }else {
                                OrderInfo orderInfo=list.get(0);
                                if (orderInfo.getOrderStatus()!= 1) {
                                    redisTemplate.delete(orderInfo.getOrderNo());
                                    orderInfo.setOrderStatus(1);//支付成功即为待发货状态
                                    orderInfo.setPayType("weixin");
                                    orderInfo.setPayTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                                    orderInfo.setPayAmount(orderInfo.getFruitAmountTotal());
                                    orderInfoMapper.updateOrderStatus(orderInfo);
                                    Fruit fruit = fruitMapper.selectByPrimaryKey(orderInfo.getFruitId());//水果已售数量更新
                                    fruit.setFruitAcceptedNum(fruit.getFruitAcceptedNum()+orderInfo.getFruitCount());
                                    fruitMapper.updateQuantitySold(fruit);
                                    UserIntegral userIntegral =new UserIntegral();
                                    userIntegral.setIntegral(orderInfo.getFruitAmountTotal().intValue());
                                    userIntegral.setUserId(orderInfo.getCreateUser());
                                    userIntegralService.insertOrUpdate(userIntegral);
                                }

                            }
                            log.info("<<订单：" + out_trade_no + "    支付宝支付成功>>");
                            //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		水果订单支付回调结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
                        }
                    }
                }
            } else {
                //微信回调返回值不为"SUCCESS"
                String out_trade_no = parameters.get("out_trade_no").toString();
                if (out_trade_no.startsWith("vip")) {
                    log.error("订单" + out_trade_no + " 支付失败！");
                } else if (out_trade_no.startsWith("fruit")){
                    List<OrderInfo> list=orderInfoMapper.selectByOuterTradeNo(out_trade_no);

                    if (list.size()==0) {//支付失败未入库
                        log.error("订单不存在:" + out_trade_no + "   请查询备份数据库！！！！！！！！！");
                    } else {//支付失败已入库
                        for (OrderInfo o: list) {
                            o.setOrderStatus(-1);//修改订单状态为支付失败
                            orderInfoMapper.updateOrderStatus(o);
                        }
                        log.info("<<订单：" + out_trade_no + "    微信支付失败>>");
                    }
                }
            }
        } else {
            log.info("微信回调验证失败。可能不是微信发来的通知。");
        }
        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		最终业务逻辑处理成功后，向微信反馈success，必须！		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

    }


    public Map<String, Object> selectPay( String outTradeNo) {

        SortedMap<Object, Object> wxAppparameters = WxPayCore.createWxPayParamForSelect(outTradeNo);
        return GenResult.SUCCESS.genResult(wxAppparameters);

    }

    /**
     * vip充值前获取签名
     * @param userId
     * @param fee
     * @param vipType
     * @return
     */
    public Map<String, Object> vipPayBefore(Integer userId, Integer fee, Integer vipType) {
        VipRechargeLog vipRechargeLog=new VipRechargeLog();
        String key = "vip" + UUID.randomUUID().toString().substring(0, 29);
        vipRechargeLog.setOrderNo(key);
        vipRechargeLog.setCreateUser(userId);
        vipRechargeLog.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        vipRechargeLog.setOrderStatus(0);
        vipRechargeLog.setRechargeAmount(new BigDecimal(fee));
        vipRechargeLog.setVipType(vipType);
        redisTemplate.opsForValue().set(key, vipRechargeLog, 60 * 5, TimeUnit.SECONDS);//订单保存时间15分钟
        vipRechargeLogMapper.insertSelective(vipRechargeLog);
        DecimalFormat df = new DecimalFormat("#0.00"); //用于格式化充值金额

        String total_fee = "";//充值所耗费的rmb   单位为元

       total_fee = df.format(0.01);
//        total_fee = df.format(fee);
//        if (fee==30){
//            total_fee = df.format(0.01);
//        }else if (fee==90){
//            total_fee = df.format(0.02);
//        }else if (fee==360){
//            total_fee = df.format(0.03);
//        }
        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		自身业务结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲


        //	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		拼接微信所需参数，一般情况下无需修改 ▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
        /**
         * @param out_trade_no    String	传入商户内部订单号，必须唯一；
         * @param total_fee    String	所要支付的金额，格式必须为0.00，单位为元。
         */
        String orderType = "vip";
        SortedMap<Object, Object> wxAppparameters = WxPayCore.createWxPayParam(key, total_fee, orderType);
        if (wxAppparameters != null) {
            /**
             * 以resultdata对象的形式给App返回
             */
            wxAppparameters.put("out_trade_no", key);
            return GenResult.SUCCESS.genResult(wxAppparameters);
        } else {
            return GenResult.WECHAT_PAYMENT_ERROR.genResult();
        }
        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		拼接微信所需参数，一般情况下无需修改		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

    }
}

