package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.service.OrderInfoService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import com.jclz.fruit.wxpay.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "订单API")
@RequestMapping("/orderInfo")
public class OrderInfoController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 提交订单
     * @param token
     * @return
     */
    @RequestMapping("/submit")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "提交订单")
    public Map<String, Object> submitOrderInfo(@ApiParam(required = true, value = "水果数组") @RequestParam(value = "fruit") String fruit,
                                              @ApiParam(required = true, value = "地址ID") @RequestParam(value = "addressId") Integer addressId,
                                              @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                               @ApiParam(required = true, value = "支付类型 alipay-支付宝支付,weixin-微信支付") @RequestParam(value = "payType",defaultValue = "alipay") String payType,
                                               @ApiParam(required = true, value = "结算类型0 -立即购买，1-购物车结算") @RequestParam(value = "submitType",defaultValue = "0") Integer submitType){
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(fruit);
        if (StringUtils.isEmpty(token)||null==addressId){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return orderInfoService.submitOrderInfo(jsonArray,userId,addressId,payType,submitType);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("提交订单：------",e);
            return GenResult.FAILED.genResult("提交订单错误!!!!");
        }
    }

    /**
     * 查询我的全部订单
     */
    @PostMapping("/selectAllOrders")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "查询我的全部订单")
    public Map<String, Object> selectAllOrders(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        int userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.selectAllOrders(userId);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 查询我的待支付订单
     */
    @PostMapping("/selectPendingOrders")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "查询我的待支付订单")
    public Map<String, Object> selectPendingOrders(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        int userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.selectPendingOrders(userId);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 查询待发货-待收货-待评价等的订单
     */
    @PostMapping("/selectOtherOrders")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "查询待发货-待收货-待评价等的订单")
    public Map<String, Object> selectOtherOrders(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                   @ApiParam(required = true, value = "订单状态 待付款0,待发货1,待收货2，待评价3，已评价4，申请退款5，已退款6，付款失败-1") @RequestParam(value = "orderStatus") Integer orderStatus) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        int userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.selectOtherOrders(userId,orderStatus);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

    /**
     * 待支付订单-修改地址
     */
    @PostMapping("/updateUserAddress")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "待支付订单-修改地址")
    public Map<String, Object> updateUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                 @ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo,
                                                 @ApiParam(required = true, value = "地址ID") @RequestParam(value = "addressId") Integer addressId) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.updateUserAddress(userId,orderNo,addressId);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 待支付订单-取消订单
     */
    @PostMapping("/deleteOrder")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "待支付订单-取消订单")
    public Map<String, Object> deleteOrder(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                 @ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.deleteOrder(userId,orderNo);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 待支付订单-订单详情
     */
    @PostMapping("/pendingOrdersDetails")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "待支付订单-订单详情")
    public Map<String, Object> pendingOrdersDetails(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                           @ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.pendingOrdersDetails(userId,orderNo);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 订单详情
     */
    @PostMapping("/ordersDetails")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "订单详情")
    public Map<String, Object> ordersDetails(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                    @ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.ordersDetails(userId,orderNo);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 确认收货
     */
    @PostMapping("/confirmReceipt")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "确认收货")
    public Map<String, Object> confirmReceipt(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                             @ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.confirmReceipt (userId,orderNo);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 订单数量
     */
    @PostMapping("/orderNum")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "订单数量")
    public Map<String, Object> orderNum(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.orderNum (userId);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }
    /**
     * 退款售后
     */
    @PostMapping("/refundAfterAale")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "退款售后")
    public Map<String, Object> refundAfterAale(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            return orderInfoService.refundAfterAale(userId);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

}
