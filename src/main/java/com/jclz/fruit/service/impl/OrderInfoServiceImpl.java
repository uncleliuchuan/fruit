package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.dao.UserAddressMapper;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.entity.UserAddress;
import com.jclz.fruit.service.AlipayOrderService;
import com.jclz.fruit.service.OrderInfoService;
import com.jclz.fruit.service.OrderInitializingBean;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.util.DateUtil;
import com.jclz.fruit.wxpay.service.WxPayService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    UserAddressMapper userAddressMapper;
    @Autowired
    WxPayService wxPayService;
    @Autowired
    AlipayOrderService alipayOrderService;
    @Autowired
    OrderRefundMapper orderRefundMapper;
    private static String Body="超级果蔬--";

    @Override
    public Map<String, Object> submitOrderInfo(JSONArray jsonArray, Integer userId, Integer addressId,String payType,Integer submitType) {
        if (1==submitType){
            //购物车结算，，先将购物车中水果删除
            List<Fruit> shopingCart= redisTemplate.opsForList().range(""+userId, 0, -1);
            List<Fruit> delList = new ArrayList();
            for (Fruit fruitTemp: shopingCart) {
                for (Object o: jsonArray) {
                    JSONObject json =JSONObject.fromObject(o);
                    if (fruitTemp.getId()==json.getInt("id")){
                        delList.add(fruitTemp);
                    }
                }
            }
            shopingCart.removeAll(delList);
            redisTemplate.delete(""+userId);
            if (!shopingCart.isEmpty()){
                redisTemplate.opsForList().rightPushAll(""+userId,shopingCart);
            }
        }
        boolean isVIP = userService.isVIP(userId);
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//当前时间
        if (jsonArray.size()==1){//提交订单只有一个水果
            JSONObject json = (JSONObject) jsonArray.get(0);
            Integer shoppingCartNum = json.getInt("shoppingCartNum");
            Integer id = json.getInt("id");
            Fruit fruit=fruitMapper.selectByPrimaryKey(id);
            fruit.setShoppingCartNum(shoppingCartNum);
            OrderInfo orderInfo=new OrderInfo();
            String key ="fruit:" + fruit.getId() + ":" + userId + ":" + UUID.randomUUID().toString();
            orderInfo.setOrderNo(key);//订单编号
            orderInfo.setShopId(fruit.getFruitType());
            orderInfo.setFruitCount(shoppingCartNum);
            if (isVIP){//判断是否会员，结算最终价格
                orderInfo.setFruitAmountTotal(fruit.getFruitVipPrice().multiply(new BigDecimal(shoppingCartNum)));
                orderInfo.setFruitPrice(fruit.getFruitVipPrice());
            }else {
                orderInfo.setFruitAmountTotal(fruit.getFruitPrice().multiply(new BigDecimal(shoppingCartNum)));
                orderInfo.setFruitPrice(fruit.getFruitPrice());
            }
            orderInfo.setOrderStatus(0);//待付款
            orderInfo.setCreateTime(time);
            orderInfo.setCreateUser(userId);
            orderInfo.setFruitId(fruit.getId());
            orderInfo.setBody(Body+fruit.getFruitName());
            orderInfo.setFruitName(fruit.getFruitName());
            orderInfo.setAddressId(addressId);
            orderInfo.setOrderTime(time);
            orderInfo.setExpirationTime(DateUtil.getNextFifteen(time));
            orderInfo.setFruitNum(fruit.getFruitNum());
            orderInfo.setFruitPictureUrl(fruit.getFruitPictureUrl());
            orderInfo.setOrderInfoType(0);
            orderInfo.setName(userAddress.getName());
            orderInfo.setPhone(userAddress.getPhone());
            orderInfo.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
            redisTemplate.opsForValue().set(key,orderInfo,900, TimeUnit.SECONDS);//订单保存时间15分钟
            if (payType.equals("weixin")){
                return wxPayService.payBefore(userId,key);
            }else if (payType.equals("alipay")){
                return alipayOrderService.alipayFruit(userId,key);
            }

        }else {

            List<OrderInfo> list=new ArrayList<>();
            for (Object o: jsonArray) {
                JSONObject json =JSONObject.fromObject(o);
                Integer shoppingCartNum = json.getInt("shoppingCartNum");
                Integer id = json.getInt("id");
                Fruit fruit=fruitMapper.selectByPrimaryKey(id);
                fruit.setShoppingCartNum(shoppingCartNum);
                OrderInfo orderInfo=new OrderInfo();
                String key = "fruitList:" + fruit.getId() + ":" + userId + ":" +UUID.randomUUID().toString();
                orderInfo.setOrderNo(key);//订单编号
                orderInfo.setShopId(fruit.getFruitType());
                orderInfo.setFruitCount(shoppingCartNum);
                if (isVIP){//判断是否会员，结算最终价格
                    orderInfo.setFruitAmountTotal(fruit.getFruitVipPrice().multiply(new BigDecimal(shoppingCartNum)));
                    orderInfo.setFruitPrice(fruit.getFruitVipPrice());
                }else {
                    orderInfo.setFruitAmountTotal(fruit.getFruitPrice().multiply(new BigDecimal(shoppingCartNum)));
                    orderInfo.setFruitPrice(fruit.getFruitPrice());
                }
                orderInfo.setOrderStatus(0);//待付款
                orderInfo.setCreateTime(time);
                orderInfo.setCreateUser(userId);
                orderInfo.setFruitId(fruit.getId());
                orderInfo.setBody(Body+fruit.getFruitName());
                orderInfo.setFruitName(fruit.getFruitName());
                orderInfo.setAddressId(addressId);
                orderInfo.setOrderTime(time);
                orderInfo.setExpirationTime(DateUtil.getNextFifteen(time));
                orderInfo.setFruitNum(fruit.getFruitNum());
                orderInfo.setFruitPictureUrl(fruit.getFruitPictureUrl());
                orderInfo.setOrderInfoType(0);
                orderInfo.setName(userAddress.getName());
                orderInfo.setPhone(userAddress.getPhone());
                orderInfo.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
                list.add(orderInfo);
            }
            redisTemplate.opsForList().leftPushAll("list:"+list.get(0).getOrderNo(),list);//订单保存时间15分钟
            redisTemplate.expire("list:"+list.get(0).getOrderNo(),900, TimeUnit.SECONDS);
            if (payType.equals("weixin")){
                return wxPayService.payBefore(userId,"list:"+list.get(0).getOrderNo());
            }else if (payType.equals("alipay")){
                return alipayOrderService.alipayFruit(userId,"list:"+list.get(0).getOrderNo());
            }
        }
        return GenResult.FAILED.genResult();
    }
    @Override
    public Map<String, Object> selectAllOrders(Integer userId) {
        boolean isVIP = userService.isVIP(userId);
        //查询非购物车结算的待支付订单
        Set<String> keys = redisTemplate.keys("fruit:" + "*" + ":"+ userId + ":" + "*");
        List<OrderInfo> list = new ArrayList<>();
        for (String keytemp : keys) {
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(keytemp);
            if (orderInfo != null) {
                list.add(orderInfo);
            }
        }
        //查询购物车结算的待支付订单
        Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*" + ":"+ userId + ":" + "*");
        for (String keytemp : keysList) {
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range(keytemp, 0, -1);
            if (orderInfos .size()>0) {
                list.addAll(orderInfos);
            }
        }
        //查询待发货-待收货-待评价等的订单
        List<OrderInfo> orderInfos =orderInfoMapper.selectOrdersByUserId(userId);
        for (OrderInfo orderInfo:orderInfos) {
            Fruit fruit=fruitMapper.selectByPrimaryKey(orderInfo.getFruitId());
            orderInfo.setFruitNum(fruit.getFruitNum());
            orderInfo.setFruitPictureUrl(fruit.getFruitPictureUrl());
            if (isVIP){//判断是否会员，结算最终价格
                orderInfo.setFruitPrice(fruit.getFruitVipPrice());
            }else {
                orderInfo.setFruitPrice(fruit.getFruitPrice());
            }
        }
        list.addAll(orderInfos);
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> selectPendingOrders(Integer userId) {
        //查询非购物车结算的待支付订单
        Set<String> keys = redisTemplate.keys("fruit:" + "*" + ":"+ userId + ":" + "*");
        List<OrderInfo> list = new ArrayList<>();
        for (String keytemp : keys) {
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(keytemp);
            if (orderInfo != null) {
                list.add(orderInfo);
            }
        }
        //查询购物车结算的待支付订单
        Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*" + ":"+ userId + ":" + "*");
        for (String keytemp : keysList) {
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range(keytemp, 0, -1);
            if (orderInfos .size()>0) {
                list.addAll(orderInfos);
            }
        }
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> selectOtherOrders(Integer userId, Integer orderStatus) {
        boolean isVIP = userService.isVIP(userId);
        List<OrderInfo> list = new ArrayList<>();
        //查询待发货-待收货-待评价等的订单
        List<OrderInfo> orderInfos =orderInfoMapper.selectOtherOrders(userId,orderStatus);
        for (OrderInfo orderInfo:orderInfos) {
            Fruit fruit=fruitMapper.selectByPrimaryKey(orderInfo.getFruitId());
            orderInfo.setFruitNum(fruit.getFruitNum());
            orderInfo.setFruitPictureUrl(fruit.getFruitPictureUrl());
            if (isVIP){//判断是否会员，结算最终价格
                orderInfo.setFruitPrice(fruit.getFruitVipPrice());
            }else {
                orderInfo.setFruitPrice(fruit.getFruitPrice());
            }
        }
        list.addAll(orderInfos);
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> updateUserAddress(Integer userId, String orderNo, Integer addressId) {
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (orderNo.startsWith("fruit:")){//单个水果购买
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(orderNo);
            if (null==orderInfo){
                return GenResult.ORDER_EXPIRED.genResult();
            }
            orderInfo.setAddressId(addressId);
            orderInfo.setName(userAddress.getName());
            orderInfo.setPhone(userAddress.getPhone());
            orderInfo.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
            Long expire = redisTemplate.getExpire(orderNo, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(orderNo,orderInfo,expire, TimeUnit.SECONDS);//订单保存时间15分钟
            return GenResult.SUCCESS.genResult();
        }else if (orderNo.startsWith("fruitList:")){//购物车购买多个水果
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range("list:"+orderNo, 0, -1);
            if (orderInfos.size()>0){//当前key正是生成的订单列表的key
                for (OrderInfo orderInfo: orderInfos) {
                    if (orderNo.equals(orderInfo.getOrderNo())){
                        orderInfo.setAddressId(addressId);
                        orderInfo.setName(userAddress.getName());
                        orderInfo.setPhone(userAddress.getPhone());
                        orderInfo.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
                    }
                }
                Long expire = redisTemplate.getExpire("list:"+orderNo, TimeUnit.SECONDS);
                redisTemplate.delete("list:"+orderNo);
                redisTemplate.opsForList().leftPushAll("list:"+orderNo,orderInfos);//订单保存时间15分钟
                redisTemplate.expire("list:"+orderNo,expire, TimeUnit.SECONDS);
                return GenResult.SUCCESS.genResult();
            }else {//说明当前key不是生成的订单列表的key
                Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*" + userId + ":" + "*");
                for (String keytemp : keysList) {
                    List<OrderInfo> list = redisTemplate.opsForList().range(keytemp, 0, -1);
                    if (list .size()>0) {
                        for (OrderInfo orderInfo: list) {
                            if (orderNo.equals(orderInfo.getOrderNo())){
                                orderInfo.setAddressId(addressId);
                                orderInfo.setName(userAddress.getName());
                                orderInfo.setPhone(userAddress.getPhone());
                                orderInfo.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
                            }
                        }
                        Long expire = redisTemplate.getExpire(keytemp, TimeUnit.SECONDS);
                        redisTemplate.delete(keytemp);
                        redisTemplate.opsForList().leftPushAll(keytemp,list);//订单保存时间15分钟
                        redisTemplate.expire(keytemp,expire, TimeUnit.SECONDS);
                        return GenResult.SUCCESS.genResult();
                    }
                }
                return GenResult.ORDER_EXPIRED.genResult();
            }
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> deleteOrder(Integer userId, String orderNo) {
        OrderInfo info = orderInfoMapper.selectByOrderNo(orderNo);//如果该订单已入库，删除订单
        if (null!=info&&info.getOrderStatus()<1){
            orderInfoMapper.deleteByPrimaryKey(info.getId());
        }
        if (orderNo.startsWith("fruit:")){//单个水果购买
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(orderNo);
            if (null==orderInfo){
                return GenResult.ORDER_EXPIRED.genResult();
            }
            redisTemplate.delete(orderNo);
            return GenResult.SUCCESS.genResult();
        }else if (orderNo.startsWith("fruitList:")){//购物车购买多个水果
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range("list:"+orderNo, 0, -1);
            List<OrderInfo> delList = new ArrayList();
            if (orderInfos.size()>0){//当前key正是生成的订单列表的key
                for (OrderInfo orderInfo: orderInfos) {
                    if (orderNo.equals(orderInfo.getOrderNo())){
                        delList.add(orderInfo);
                    }
                }
                orderInfos.removeAll(delList);
                Long expire = redisTemplate.getExpire("list:"+orderNo, TimeUnit.SECONDS);
                redisTemplate.delete("list:"+orderNo);
                if (!orderInfos.isEmpty()){
                    redisTemplate.opsForList().leftPushAll("list:"+orderNo,orderInfos);//订单保存时间15分钟
                    redisTemplate.expire("list:"+orderNo,expire, TimeUnit.SECONDS);
                }

                return GenResult.SUCCESS.genResult();
            }else {//说明当前key不是生成的订单列表的key
                Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*" + userId + ":" + "*");
                for (String keytemp : keysList) {
                    List<OrderInfo> list = redisTemplate.opsForList().range(keytemp, 0, -1);
                    delList = new ArrayList();
                    if (list .size()>0) {
                        for (OrderInfo orderInfo: list) {
                            if (orderNo.equals(orderInfo.getOrderNo())){
                                delList.add(orderInfo);
                            }
                        }
                        list.removeAll(delList);
                        Long expire = redisTemplate.getExpire(keytemp, TimeUnit.SECONDS);
                        redisTemplate.delete(keytemp);
                        if (!list.isEmpty()){
                            redisTemplate.opsForList().leftPushAll(keytemp,list);//订单保存时间15分钟
                            redisTemplate.expire(keytemp,expire, TimeUnit.SECONDS);
                        }
                        return GenResult.SUCCESS.genResult();
                    }
                }
                return GenResult.ORDER_EXPIRED.genResult();
            }
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> pendingOrdersDetails(Integer userId, String orderNo) {
        if (orderNo.startsWith("fruit:")){//单个水果购买
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(orderNo);
            if (null==orderInfo){
                return GenResult.ORDER_EXPIRED.genResult();
            }
            return GenResult.SUCCESS.genResult(orderInfo);
        }else if (orderNo.startsWith("fruitList:")){//购物车购买多个水果
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range("list:"+orderNo, 0, -1);
            if (orderInfos.size()>0){//当前key正是生成的订单列表的key
                for (OrderInfo orderInfo: orderInfos) {
                    if (orderNo.equals(orderInfo.getOrderNo())){

                        return GenResult.SUCCESS.genResult(orderInfo);
                    }
                }

            }else {//说明当前key不是生成的订单列表的key
                Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*" + ":"+ userId + ":" + "*");
                for (String keytemp : keysList) {
                    List<OrderInfo> list = redisTemplate.opsForList().range(keytemp, 0, -1);
                    if (list .size()>0) {
                        for (OrderInfo orderInfo: list) {
                            if (orderNo.equals(orderInfo.getOrderNo())){
                                return GenResult.SUCCESS.genResult(orderInfo);
                            }
                        }
                    }
                }
                return GenResult.ORDER_EXPIRED.genResult();
            }
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> ordersDetails(Integer userId, String orderNo) {
        boolean isVIP = userService.isVIP(userId);
        OrderInfo info = orderInfoMapper.selectByOrderNo(orderNo);
        if (null==info){
            return GenResult.ORDER_NOT_EXIST.genResult();
        }
        Fruit fruit=fruitMapper.selectByPrimaryKey(info.getFruitId());
        info.setFruitNum(fruit.getFruitNum());
        info.setFruitPictureUrl(fruit.getFruitPictureUrl());
        if (isVIP){//判断是否会员，结算最终价格
            info.setFruitPrice(fruit.getFruitVipPrice());
        }else {
            info.setFruitPrice(fruit.getFruitPrice());
        }
        if (info.getOrderStatus()>=5&&info.getOrderStatus()<=8){
            OrderRefund orderRefund = orderRefundMapper.selectByOrderNo(orderNo);
            if (null!=orderRefund){
                info.setOrderRefund(orderRefund);
            }
        }
        return GenResult.SUCCESS.genResult(info);
    }

    @Override
    public Map<String, Object> confirmReceipt(Integer userId, String orderNo) {
        OrderInfo info = orderInfoMapper.selectByOrderNo(orderNo);
        if (null==info){
            return GenResult.ORDER_NOT_EXIST.genResult();
        }
        if (info.getOrderStatus()==2){//确认收货
            info.setOrderStatus(3);
            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//当前时间
            info.setConfirmReceivingTime(time);//收货时间
            orderInfoMapper.updateOrderStatus(info);
            OrderInitializingBean.OrderDelayQueueThread.removeDelay(info);//自动收货队列删除  调用线程的removeDelay方法删除数据
            OrderInitializingBean.fruitCommentsDelayQueueThread.add(info.getOrderNo());//默认评价队列添加
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> orderNum(Integer userId) {
        Integer pendingOrdersNum =0;
        //查询非购物车结算的待支付订单
        Set<String> keys = redisTemplate.keys("fruit:" + "*" + ":"+ userId + ":" + "*");
        List<OrderInfo> list = new ArrayList<>();
        for (String keytemp : keys) {
            OrderInfo orderInfo = (OrderInfo) redisTemplate.opsForValue().get(keytemp);
            if (orderInfo != null) {
                list.add(orderInfo);
            }
        }
        //查询购物车结算的待支付订单
        Set<String> keysList = redisTemplate.keys("list:fruitList:" + "*"+ ":" + userId + ":" + "*");
        for (String keytemp : keysList) {
            List<OrderInfo> orderInfos = redisTemplate.opsForList().range(keytemp, 0, -1);
            if (orderInfos .size()>0) {
                list.addAll(orderInfos);
            }
        }
        pendingOrdersNum=list.size();//待付款
       Integer deliverGoodsNum= orderInfoMapper.selectOrderStatus(userId,1);//待发货
       Integer receivingGoodsNum= orderInfoMapper.selectOrderStatus(userId,2);//待收货
       Integer evaluateNum= orderInfoMapper.selectOrderStatus(userId,3);//待评价
        JSONObject json =new JSONObject();
        json.put("pendingOrdersNum",pendingOrdersNum);//待付款
        json.put("deliverGoodsNum",deliverGoodsNum);//待发货
        json.put("receivingGoodsNum",receivingGoodsNum);//待收货
        json.put("evaluateNum",evaluateNum);//待评价
        return GenResult.SUCCESS.genResult(json);
    }

    @Override
    public Map<String, Object> refundAfterAale(Integer userId) {
        boolean isVIP = userService.isVIP(userId);
        List<OrderInfo> orderInfos =orderInfoMapper.refundAfterAale(userId);
        for (OrderInfo orderInfo:orderInfos) {
            Fruit fruit=fruitMapper.selectByPrimaryKey(orderInfo.getFruitId());
            orderInfo.setFruitNum(fruit.getFruitNum());
            orderInfo.setFruitPictureUrl(fruit.getFruitPictureUrl());
            if (isVIP){//判断是否会员，结算最终价格
                orderInfo.setFruitPrice(fruit.getFruitVipPrice());
            }else {
                orderInfo.setFruitPrice(fruit.getFruitPrice());
            }
        }
        return GenResult.SUCCESS.genResult(orderInfos);
    }

}
