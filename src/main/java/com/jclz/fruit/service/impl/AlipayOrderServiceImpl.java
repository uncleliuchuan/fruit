package com.jclz.fruit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jclz.fruit.config.AlipayConfig;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.*;
import com.jclz.fruit.entity.*;
import com.jclz.fruit.service.AlipayOrderService;
import com.jclz.fruit.service.UserIntegralService;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.wxpay.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Service("alipayOrderService")
public class AlipayOrderServiceImpl implements AlipayOrderService {
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
    AlipaymentOrderMapper alipaymentOrderMapper;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    UserIntegralService userIntegralService;
    @Autowired
    OrderRefundMapper orderRefundMapper;

    private static String Body="超级果蔬--";

    @Override
    @Transactional
    public Map<String, Object> alipayFruit(int userId, String orderNo) {
        //最终返回加签之后的，app需要传给支付宝app的订单信息字符串
        String order = "fruit" + UUID.randomUUID().toString();
        String orderString = "";
        DecimalFormat df = new DecimalFormat("#0.00"); //用于格式化充值金额

        String total_fee = "";//充值所耗费的rmb   单位为元

        total_fee = df.format(0.010001);
        String fruit_total_fee =df.format(0.00);
        log.info("==================支付宝下单,商户订单号为：" + orderNo);
        if (orderNo.contains("list:")) {//购物车结算包含多个订单
            List<OrderInfo> list = redisTemplate.opsForList().range(orderNo, 0, -1);
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
        //创建商户支付宝订单(因为需要记录每次支付宝支付的记录信息，单独存一个表跟商户订单表关联，以便以后查证)
        AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
        //alipaymentOrder.setClubOrderId(oreder.getId().toString());//商家订单主键
        alipaymentOrder.setOutTradeNo(order);//商户订单号
        alipaymentOrder.setTradeStatus(0);//交易状态
        /**
         * 测试：total_fee
         * 正式：fruit_total_fee
         */
        alipaymentOrder.setTotalAmount(new BigDecimal(total_fee));//订单金额
        alipaymentOrder.setReceiptAmount(new BigDecimal(0.00));//实收金额
        alipaymentOrder.setInvoiceAmount(new BigDecimal(0.00));//开票金额
        alipaymentOrder.setBuyerPayAmount(new BigDecimal(0.00));//付款金额
        alipaymentOrder.setRefundFee(new BigDecimal(0.00));    //总退款金额

        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            model.setBody(Body+order);                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setSubject("超级果蔬");                 //商品名称
            model.setOutTradeNo(order);           //商户订单号(自动生成)
            // model.setTimeoutExpress("30m");     			  //交易超时时间
            model.setTotalAmount(total_fee);         //支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");              //销售产品码（固定值）
            ali_request.setBizModel(model);
            log.info("====================异步通知的地址为：" + AlipayConfig.notify_url);
            ali_request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
            ali_request.setReturnUrl(AlipayConfig.return_url);        //同步回调地址（APP）

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            orderString = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
            this.createAlipayMentOrder(alipaymentOrder);//创建新的商户支付宝订单

        } catch (AlipayApiException e) {
            log.info("与支付宝交互出错，未能生成订单，请检查代码！",e);
            return GenResult.FAILED.genResult() ;
        }
        JSONObject json=new JSONObject();
        json.put("orderString",orderString);
        json.put("out_trade_no", order);
        return GenResult.SUCCESS.genResult(json) ;
    }
    @Override
    public int createAlipayMentOrder(AlipaymentOrder alipaymentOrder) {
        return alipaymentOrderMapper.insertSelective(alipaymentOrder);
    }

    @Override
    @Transactional
    public Map<String, Object> alipayVipOrder(int userId, Integer fee, Integer vipType) {
        VipRechargeLog vipRechargeLog=new VipRechargeLog();
        String orderString = "";
        String order = "vip" + UUID.randomUUID().toString();
        vipRechargeLog.setOrderNo(order);
        vipRechargeLog.setCreateUser(userId);
        vipRechargeLog.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        vipRechargeLog.setOrderStatus(0);
        vipRechargeLog.setRechargeAmount(new BigDecimal(fee));
        vipRechargeLog.setVipType(vipType);
        redisTemplate.opsForValue().set(order, vipRechargeLog, 60 * 5, TimeUnit.SECONDS);//订单保存时间15分钟
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
        AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
        //alipaymentOrder.setClubOrderId(oreder.getId().toString());//商家订单主键
        alipaymentOrder.setOutTradeNo(order);//商户订单号
        alipaymentOrder.setTradeStatus(0);//交易状态
        alipaymentOrder.setTotalAmount(new BigDecimal(total_fee));//订单金额
        alipaymentOrder.setReceiptAmount(new BigDecimal(0.00));//实收金额
        alipaymentOrder.setInvoiceAmount(new BigDecimal(0.00));//开票金额
        alipaymentOrder.setBuyerPayAmount(new BigDecimal(0.00));//付款金额
        alipaymentOrder.setRefundFee(new BigDecimal(0.00));    //总退款金额
        //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		自身业务结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            model.setBody(Body+order);                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setSubject("超级果蔬");                 //商品名称
            model.setOutTradeNo(order);           //商户订单号(自动生成)
            // model.setTimeoutExpress("30m");     			  //交易超时时间
            model.setTotalAmount(total_fee);         //支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");              //销售产品码（固定值）
            ali_request.setBizModel(model);
            log.info("====================异步通知的地址为：" + AlipayConfig.notify_url);
            ali_request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
            ali_request.setReturnUrl(AlipayConfig.return_url);        //同步回调地址（APP）

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            orderString = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
            this.createAlipayMentOrder(alipaymentOrder);//创建新的商户支付宝订单
        } catch (AlipayApiException e) {
            log.info("与支付宝交互出错，未能生成订单，请检查代码！",e);
            return GenResult.FAILED.genResult() ;
        }
        JSONObject json=new JSONObject();
        json.put("orderString",orderString);
        json.put("out_trade_no", order);

        return GenResult.SUCCESS.genResult(json) ;
    }

    @Override
    @Transactional
    public String notify(Map<String, String> conversionParams) {
        log.info("==================支付宝异步请求逻辑处理");

        //签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;

        try {
            //调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(conversionParams, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

        } catch (AlipayApiException e) {
            log.info("==================验签失败 ！");
            e.printStackTrace();
        }

        //对验签进行处理
        if (signVerified) {
            //验签通过
            //获取需要保存的数据
            String appId = conversionParams.get("app_id");//支付宝分配给开发者的应用Id
            String notifyTime = conversionParams.get("notify_time");//通知时间:yyyy-MM-dd HH:mm:ss
            String gmtCreate = conversionParams.get("gmt_create");//交易创建时间:yyyy-MM-dd HH:mm:ss
            String gmtPayment = conversionParams.get("gmt_payment");//交易付款时间
            String gmtRefund = conversionParams.get("gmt_refund");//交易退款时间
            String gmtClose = conversionParams.get("gmt_close");//交易结束时间
            String tradeNo = conversionParams.get("trade_no");//支付宝的交易号
            String outTradeNo = conversionParams.get("out_trade_no");//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
            String outBizNo = conversionParams.get("out_biz_no");//商户业务号(商户业务ID，主要是退款通知中返回退款申请的流水号)
            String buyerLogonId = conversionParams.get("buyer_logon_id");//买家支付宝账号
            String sellerId = conversionParams.get("seller_id");//卖家支付宝用户号
            String sellerEmail = conversionParams.get("seller_email");//卖家支付宝账号
            String totalAmount = conversionParams.get("total_amount");//订单金额:本次交易支付的订单金额，单位为人民币（元）
            String receiptAmount = conversionParams.get("receipt_amount");//实收金额:商家在交易中实际收到的款项，单位为元
            String invoiceAmount = conversionParams.get("invoice_amount");//开票金额:用户在交易中支付的可开发票的金额
            String buyerPayAmount = conversionParams.get("buyer_pay_amount");//付款金额:用户在交易中支付的金额
            String tradeStatus = conversionParams.get("trade_status");// 获取交易状态

            //支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）
            AlipaymentOrder alipaymentOrder = this.selectByOutTradeNo(outTradeNo);

            if (alipaymentOrder != null && totalAmount.equals(alipaymentOrder.getTotalAmount().toString()) && AlipayConfig.APPID.equals(appId)) {
                //修改数据库支付宝订单表(因为要保存每次支付宝返回的信息到数据库里，以便以后查证)
                alipaymentOrder.setNotifyTime(notifyTime);
                alipaymentOrder.setGmtCreate(gmtCreate);
                alipaymentOrder.setGmtPayment(gmtPayment);
                alipaymentOrder.setGmtRefund(gmtRefund);
                alipaymentOrder.setGmtClose(gmtClose);
                alipaymentOrder.setTradeNo(tradeNo);
                alipaymentOrder.setOutBizNo(outBizNo);
                alipaymentOrder.setBuyerLogonId(buyerLogonId);
                alipaymentOrder.setSellerId(sellerId);
                alipaymentOrder.setSellerEmail(sellerEmail);
                alipaymentOrder.setTotalAmount(new BigDecimal(totalAmount));
                alipaymentOrder.setReceiptAmount(new BigDecimal(receiptAmount));
                alipaymentOrder.setInvoiceAmount(new BigDecimal(invoiceAmount));
                alipaymentOrder.setBuyerPayAmount(new BigDecimal(buyerPayAmount));
                switch (tradeStatus) // 判断交易结果
                {
                    case "TRADE_FINISHED": // 交易结束并不可退款
                        alipaymentOrder.setTradeStatus(3);
                        break;
                    case "TRADE_SUCCESS": // 交易支付成功
                        alipaymentOrder.setTradeStatus( 2);

                        if (outTradeNo.startsWith("vip")) {
                            // 	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		vip订单支付成功逻辑		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
                            VipRechargeLog vipRechargeLog= vipRechargeLogMapper.selectByOrderNo(outTradeNo);
                            if (vipRechargeLog != null) {
                                redisTemplate.delete(outTradeNo);
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
                                log.error("数据库中无此订单：" + outTradeNo + "   请查询备份数据库！！！！！！！！！");
                            }
                            //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		vip支付回调结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

                        } else if (outTradeNo.startsWith("fruit")){
                            // 	▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼		水果订单支付成功逻辑		▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
                            List<OrderInfo> list=orderInfoMapper.selectByOuterTradeNo(outTradeNo);
                            Stream.of(list).forEach(System.out::println);//JDK8以上版本使用的数组遍历

                            if (list.size()==0) {//入库订单不存在，除非前端操作越过了入库
                                log.error("订单不存在:" + outTradeNo + "   请查询备份数据库！！！！！！！！！");
                            } else {
                                //修改订单状态
                                    //查询数据库中是否有多个订单，，即是否购物车结算逻辑
                                    if (list.size()>1){
                                        for (OrderInfo o: list) {
                                            if (o.getOrderStatus()!= 1) {
                                                redisTemplate.delete("list:"+o.getOrderNo());
                                                o.setOrderStatus(1);//支付成功即为待发货状态
                                                o.setPayType("alipay");
                                                o.setPayTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                                                o.setPayAmount(o.getFruitAmountTotal());
                                                orderInfoMapper.updateOrderStatus(o);
                                                Fruit fruit = fruitMapper.selectByPrimaryKey(o.getFruitId());//水果已售数量更新
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
                                            orderInfo.setPayType("alipay");
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
                                    log.info("<<订单：" + outTradeNo + "    支付宝支付成功>>");
                                    //	▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲		水果订单支付回调结束		▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
                                }
                        }

                        break;
                    case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                        alipaymentOrder.setTradeStatus( 1);
                        if (outTradeNo.startsWith("vip")) {
                            log.error("订单" + outTradeNo + " 支付失败！");
                        } else if (outTradeNo.startsWith("fruit")){
                            List<OrderInfo> list=orderInfoMapper.selectByOuterTradeNo(outTradeNo);

                            if (list.size()==0) {//支付失败未入库
                                log.error("订单不存在:" + outTradeNo + "   请查询备份数据库！！！！！！！！！");
                            } else {//支付失败已入库
                                for (OrderInfo o: list) {
                                    o.setOrderStatus(-1);//修改订单状态为支付失败
                                    orderInfoMapper.updateOrderStatus(o);
                                }
                                log.info("<<订单：" + outTradeNo + "  支付宝支付失败>>");
                            }
                        }
                        break;
                    case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                        alipaymentOrder.setTradeStatus(0);
                        break;
                    default:
                        break;
                }
                int returnResult = this.updateByPrimaryKey(alipaymentOrder);    //更新交易表中状态

                if (tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功

                    if (returnResult > 0) {
                        return "success";
                    } else {
                        return "fail";
                    }
                } else {
                    return "fail";
                }

            } else {
                log.info("==================支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）,不一致！返回fail");
                return "fail";
            }

        } else {  //验签不通过
            log.info("==================验签不通过 ！");
            return "fail";
        }
    }
    @Override
    public AlipaymentOrder selectByOutTradeNo(String outTradeNo) {
        return alipaymentOrderMapper.selectByOutTradeNo(outTradeNo);
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(AlipaymentOrder alipaymentOrder) {
        return alipaymentOrderMapper.updateByPrimaryKey(alipaymentOrder);
    }

    @Override
    public Map<String, Object> checkAlipay(String outTradeNo) {
        log.info("==================向支付宝发起查询，查询商户订单号为：" + outTradeNo);

        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型）
            AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                    AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                    AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
            AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
            alipayTradeQueryRequest.setBizContent("{" +
                    "\"out_trade_no\":\"" + outTradeNo + "\"" +
                    "}");
            AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
            if (alipayTradeQueryResponse.isSuccess()) {

                AlipaymentOrder alipaymentOrder = this.selectByOutTradeNo(outTradeNo);
                //修改数据库支付宝订单表
                alipaymentOrder.setTradeNo(alipayTradeQueryResponse.getTradeNo());
                alipaymentOrder.setBuyerLogonId(alipayTradeQueryResponse.getBuyerLogonId());
                alipaymentOrder.setTotalAmount(new BigDecimal(alipayTradeQueryResponse.getTotalAmount()));
                alipaymentOrder.setReceiptAmount(new BigDecimal(alipayTradeQueryResponse.getReceiptAmount()));
                alipaymentOrder.setInvoiceAmount(new BigDecimal(alipayTradeQueryResponse.getInvoiceAmount()));
                alipaymentOrder.setBuyerPayAmount(new BigDecimal(alipayTradeQueryResponse.getBuyerPayAmount()));
                switch (alipayTradeQueryResponse.getTradeStatus()) // 判断交易结果
                {
                    case "TRADE_FINISHED": // 交易结束并不可退款
                        alipaymentOrder.setTradeStatus(3);
                        break;
                    case "TRADE_SUCCESS": // 交易支付成功
                        alipaymentOrder.setTradeStatus(2);
                        break;
                    case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                        alipaymentOrder.setTradeStatus(1);
                        break;
                    case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                        alipaymentOrder.setTradeStatus(0);
                        break;
                    default:
                        break;
                }
                this.updateByPrimaryKey(alipaymentOrder); //更新表记录
                return GenResult.SUCCESS.genResult(alipaymentOrder.getTradeStatus());
            } else {
                log.info("==================调用支付宝查询接口失败！");
            }
        } catch (AlipayApiException e) {
            log.info("==================调用支付宝查询接口失败！",e);
            return GenResult.FAILED.genResult();
        }
        return GenResult.FAILED.genResult();
    }
    @Override
    public String refound(String outTradeNo,String outRequestNo,String refundAmount) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel bizModel = new AlipayTradeRefundModel();
        bizModel.setOutTradeNo(outTradeNo);
//        bizModel.setTradeNo(tradeNo);
        bizModel.setRefundAmount(refundAmount);
        bizModel.setOutRequestNo(outRequestNo);
        Boolean flag = new Boolean(false);
        request.setBizModel(bizModel);

        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);

            if (response.isSuccess()) {
                flag = true;
                System.out.println("成功");
                log.info("==================调用支付宝退款接口成功！");
                //修改订单状态为已退款
                     OrderRefund orderRefund = orderRefundMapper.selectByOutRefundNo(outRequestNo);
                    OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
                     if (orderInfo.getOrderStatus() == 5 || orderInfo.getOrderStatus() == 6) {
                    orderInfo.setOrderStatus(7);//已退款
                    }
                    orderInfoMapper.updateOrderStatus(orderInfo);
                    return response.getBody();
                } else {
                    log.info("==================调用支付宝退款接口失败！");
                    flag = false;
                    return response.getBody();
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("异常");
            flag = false;
            return response.getBody();
        }
      }

    }
