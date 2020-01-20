package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.UserAuths;
import com.jclz.fruit.service.UserAuthsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 第三方登录
 */
@RestController
@RequestMapping("/userAuths")
@Api(tags = "第三方登录")
public class UserAuthsController {
    @Autowired
    UserAuthsService userAuthsService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    @ApiOperation(value = "登录", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> login(@ApiParam(value = "userAuths") UserAuths userAuths) {

        return userAuthsService.login(userAuths);
    }

    /**
     * 绑定手机号
     */
    @RequestMapping("/bindPhone")
    @ApiOperation(value = "绑定手机号", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> bindPhone(@ApiParam(required = true, value = "手机号") String telephone,
                                            @ApiParam(required = true, value = "验证码") String vcode,
                                            @ApiParam(required = true, value = "第三方唯一标识") String unionid) {

        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(vcode) || StringUtils.isEmpty(unionid)) {
            return GenResult.PARAMS_ERROR.genResult();

        }
        return userAuthsService.bindPhone(telephone, vcode, unionid);
    }
}
