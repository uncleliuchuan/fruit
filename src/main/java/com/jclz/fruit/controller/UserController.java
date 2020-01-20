package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户API")
@RequestMapping("/user")
public class UserController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    UserService userService;

    @RequestMapping("/detail")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "用户详情")
    public  Map<String,Object> userDetail(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            return userService.userDetail(userId);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }

    }

}
