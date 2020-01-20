package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.enums.VipType;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import com.jclz.fruit.wxpay.service.WxPayService;
import com.jclz.fruit.wxpay.util.ObjectUtil;
import com.jclz.fruit.wxpay.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "微信支付API")
@RequestMapping("/wx")
public class WxpayController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    WxPayService wxPayService;

    @ResponseBody
    @PostMapping("/pay_before")
    @ApiOperation(value = "支付前获取签名", httpMethod = "POST", response = Map.class, responseContainer = "Map")
    public synchronized Map<String, Object> payBefore(@ApiParam(value = "token") @RequestParam String token,
                                         @ApiParam(value = "订单id") @RequestParam String orderNo) {
        //验证参数
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            if (StringUtil.isNull(orderNo)) {
                return wxPayService.payBefore(userId, orderNo);
            } else {
                return GenResult.PARAMS_ERROR.genResult();
            }
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

    @ResponseBody
    @PostMapping("/vip/pay_before")
    @ApiOperation(value = "vip充值前获取签名", httpMethod = "POST", response = Map.class, responseContainer = "Map")
    public synchronized Map<String, Object> payBefore(@ApiParam(value = "token") @RequestParam String token,
                                                      @ApiParam(value = "VIP充值类型") @RequestParam Integer vipType) {
        //验证参数
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            if (vipType == null || VipType.getFee(vipType) == null) {
                return GenResult.PARAMS_ERROR.genResult();
            }
            return wxPayService.vipPayBefore(userId, VipType.getFee(vipType), vipType);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

    /**
     * 方法名：用于接收微信支付的异步回调
     */
    @ApiIgnore
    @RequestMapping("/pay_after")
    public void payAfter(@ApiParam(value = "http请求") HttpServletRequest request, @ApiParam("输出流") PrintWriter out) {
        System.out.println("微信回调");
        wxPayService.payAfter(request, out);
    }


    /**
     * @param token          用户token
     * @param outTradeNo 微信订单号，优先使用
     * @return
     */
    @PostMapping("/select_pay")
    @ApiOperation(value = "微信查询订单api", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String, Object> selectWxPay(@ApiParam(value = "token") @RequestParam String token,
                                              @ApiParam(value = "订单号，生成订单时返回的值") @RequestParam String outTradeNo) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        System.out.println("查询订单");
        if (StringUtils.isNotBlank(outTradeNo)) {
            return wxPayService.selectPay(outTradeNo);
        }
        return GenResult.PARAMS_ERROR.genResult();
    }
}
