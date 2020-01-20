package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.enums.VipType;
import com.jclz.fruit.service.AlipayOrderService;
import com.jclz.fruit.service.OrderInfoService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import com.jclz.fruit.wxpay.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@Api(tags = "支付宝订单API")
//@ApiIgnore
@RequestMapping("/alipay")
public class AlipayContoller {
    private static final Logger logger = Logger.getLogger(AlipayContoller.class);

    @Autowired
    RedisTokenManager redisToken;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    AlipayOrderService alipayOrderService;


    /**
     * 获取支付宝加签后台的订单信息字符串
     */
    @PostMapping("/pay_before")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "水果调起支付宝支付")
    public synchronized Map<String, Object> alipayFruit(@ApiParam(required = true, value = "水果订单号") @RequestParam(value = "orderNo") String orderNo,
                                                 @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {
        boolean isSuccess = redisToken.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        int userId = Integer.parseInt(data.get("userId").toString());
            if (StringUtils.isNotBlank(orderNo)) {
                return alipayOrderService.alipayFruit(userId, orderNo);
            } else {
                return GenResult.PARAMS_ERROR.genResult();
            }
    }

    /**
     * 获取支付宝加签后台的订单信息字符串
     */
    @PostMapping("/vip/pay_before")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "vip充值支付宝支付前获取签名")
    public synchronized Map<String, Object> alipayVipOrder(@ApiParam(value = "token") @RequestParam String token,
                                              @ApiParam(value = "VIP充值类型") @RequestParam Integer vipType) {
        boolean isSuccess = redisToken.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        int userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            if (vipType == null || VipType.getFee(vipType) == null) {
                return GenResult.PARAMS_ERROR.genResult();
            }
            return alipayOrderService.alipayVipOrder(userId, VipType.getFee(vipType), vipType);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

    /**
     * 支付宝支付成功后.异步请求该接口
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/notify_url")
    @ApiIgnore
    public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("==================支付宝异步返回支付结果开始");
        //1.从支付宝回调的request域中取值
        //获取支付宝返回的参数集合
        Map<String, String[]> aliParams = request.getParameterMap();
        //用以存放转化后的参数集合
        Map<String, String> conversionParams = new HashMap<String, String>();
        for (Iterator<String> iter = aliParams.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            String[] values = aliParams.get(key);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "uft-8");
            conversionParams.put(key, valueStr);
        }
        logger.info("==================返回参数集合：" + conversionParams);
        String status = alipayOrderService.notify(conversionParams);
        return status;
    }

    /**
     * 向支付宝发起订单查询请求
     */
    @RequestMapping("/checkAlipay")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "支付宝订单结果查询")
    public Map<String, Object> checkAlipay(@ApiParam(value = "token") @RequestParam String token,
                                           @ApiParam(required = true, value = "订单id") @RequestParam(value = "outTradeNo") String outTradeNo) {
        boolean isSuccess = redisToken.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());

        if (StringUtils.isNotBlank(outTradeNo)) {
            return alipayOrderService.checkAlipay(outTradeNo);
        }
        return GenResult.PARAMS_ERROR.genResult();
    }
}
