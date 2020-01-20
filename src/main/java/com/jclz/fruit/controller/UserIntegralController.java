package com.jclz.fruit.controller;

import com.alipay.api.internal.util.StringUtils;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.UserInformation;
import com.jclz.fruit.service.UserIntegralService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户积分API")
@RequestMapping("/userIntegral")
public class UserIntegralController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    UserIntegralService userIntegralService;

    @RequestMapping("/detail")
    @ApiOperation(httpMethod = "POST",response = Map.class,responseContainer = "Map",value = "用户积分")
    public Map<String,Object> userIntegralDetail(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        boolean itoken = StringUtils.isEmpty(token);
        if (itoken) {
            System.out.println("token为空");
            return GenResult.PARAMS_ERROR.genResult("token为空");
        } else {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return userIntegralService.userIntegralDetail(userId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        }

    }

}
