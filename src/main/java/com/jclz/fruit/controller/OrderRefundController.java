package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.service.OrderRefundService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import com.jclz.fruit.wxpay.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户退款API")
@RequestMapping("/orderRefund")
public class OrderRefundController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    OrderRefundService orderRefundService;
    /**
     * 申请退款
     */
    @PostMapping("/apply")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "申请退款")
    public Map<String, Object> applyOrderRefund(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                @ApiParam(value = "用户退款") OrderRefund orderRefund) {
        boolean isSuccess = redisTokenManager.validToken(token);
        if (!isSuccess) {
            return GenResult.TOKEN_INVALID.genResult();
        }
        Map<String, Object> validToken = Jwt.validToken(token);
        JSONObject data = (JSONObject) validToken.get("data");
        Integer userId = Integer.parseInt(data.get("userId").toString());
        if (ObjectUtil.isPassInteger(userId)) {
            orderRefund.setUserId(userId);
            return orderRefundService.applyOrderRefund(orderRefund);
        } else {
            return GenResult.PARAMS_ERROR.genResult();
        }
    }

}
