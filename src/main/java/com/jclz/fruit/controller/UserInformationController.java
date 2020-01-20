package com.jclz.fruit.controller;

import com.alipay.api.internal.util.StringUtils;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.UserInformation;
import com.jclz.fruit.service.UserInformationService;
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
@Api(tags = "用户信息API")
@RequestMapping("/userInformation")
public class UserInformationController {
    @Autowired
    UserInformationService userInformationService;
    @Autowired
    RedisTokenManager redisTokenManager;

    @RequestMapping("/update")
    @ApiOperation(httpMethod = "POST",response = Map.class,responseContainer = "Map",value = "修改用户信息")
    public Map<String,Object> updateUserInformation(@ApiParam(value = "用户信息")UserInformation userInformation,
                                                    @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        boolean itoken = StringUtils.isEmpty(token);
        if (itoken) {
            System.out.println("token为空");
            return GenResult.PARAMS_ERROR.genResult("token为空");
        } else {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                int userId = Integer.parseInt(data.get("userId").toString());

                 userInformation.setUserId(userId);
                return userInformationService.update(userInformation);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        }

    }
}
