package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.service.MembershipBenefitsService;
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
@Api(tags = "会员福利API")
@RequestMapping("/membershipBenefits")
public class MembershipBenefitsController {
    @Autowired
    MembershipBenefitsService membershipBenefitsService;
    @Autowired
    RedisTokenManager redisTokenManager;

    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "会员福利列表")
    public  Map<String,Object> membershipBenefitsList(@ApiParam(required = false, value = "token") @RequestParam(required = false,value = "token",defaultValue = "") String token){

        try {
            if (StringUtils.isNotBlank(token)){
                boolean isSuccess = redisTokenManager.validToken(token);
                if (!isSuccess) {
                    return GenResult.TOKEN_INVALID.genResult();
                }
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return membershipBenefitsService.membershipBenefitsList(userId);
            }else {
                return membershipBenefitsService.membershipBenefitsList(null);
            }

        }
        catch (Exception e){
            log.error("会员福利列表错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }
    @RequestMapping("/detial")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "会员福利详情")
    public  Map<String,Object> membershipBenefitsDetial(@ApiParam(required = false, value = "token") @RequestParam(required = false, value = "token") String token,
                                                        @ApiParam(required = true, value = "会员福利ID") @RequestParam(value = "id") Integer id){
        if (null==id){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            if (StringUtils.isNotBlank(token)){
                boolean isSuccess = redisTokenManager.validToken(token);
                if (!isSuccess) {
                    return GenResult.TOKEN_INVALID.genResult();
                }
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return membershipBenefitsService.membershipBenefitsDetial(userId,id);
            }else {
                return membershipBenefitsService.membershipBenefitsDetial(null,id);
            }


        }
        catch (Exception e){
            log.error("会员福利详情错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }


}
