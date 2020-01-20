package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.Assistance;
import com.jclz.fruit.entity.UserAuths;
import com.jclz.fruit.service.AssistanceService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "助力API")
@RequestMapping("/assistance")
public class AssistanceController {
    @Autowired
    AssistanceService assistanceService;
    @Autowired
    RedisTokenManager redisTokenManager;

    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "我的助力列表")
    public  Map<String,Object> assistanceList(@ApiParam(required = true,value = "token") @RequestParam(value = "token") String token,
                                              @ApiParam(required = true, value = "每页个数") @RequestParam(value = "size") Integer size,
                                              @ApiParam(required = true, value = "页数") @RequestParam(value = "page") Integer page){

        if (StringUtils.isEmpty(token)||null==size||null==page){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (!isSuccess) {
                return GenResult.TOKEN_INVALID.genResult();
            }
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());
            return assistanceService.assistanceList(userId, size, page);
        }
        catch (Exception e){
            log.error("会员福利列表错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }
    @RequestMapping("/insert")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "新建助力")
    public  Map<String,Object> insertAssistance(@ApiParam(required = true,value = "token") @RequestParam(value = "token") String token,
                                              @ApiParam(required = true, value = "助力") Assistance assistance){

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (!isSuccess) {
                return GenResult.TOKEN_INVALID.genResult();
            }
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());
            assistance.setUserId(userId);
            return assistanceService.insertAssistance(assistance);
        }
        catch (Exception e){
            log.error("新建助力错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }
    @CrossOrigin
    @RequestMapping("/detail")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "助力详情")
    public  Map<String,Object> assistanceDetail(@ApiParam(required = true, value = "助力ID") @RequestParam(value = "assistanceId") Integer assistanceId){
        try {
            return assistanceService.assistanceDetail(assistanceId);
        }
        catch (Exception e){
            log.error("助力详情错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }
    /*@CrossOrigin
    @RequestMapping("/add")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "新用户助力")
    public  Map<String,Object> addAssistance(@ApiParam(required = true,value = "助力ID") @RequestParam(value = "assistanceId") Integer assistanceId,
                                             @ApiParam(value = "userAuths") UserAuths userAuths){

        if (null==assistanceId||null==userAuths){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            return assistanceService.addAssistance(assistanceId,userAuths);
        }
        catch (Exception e){
            log.error("新用户助力错误：----",e);
            return GenResult.FAILED.genResult();
        }
    }*/
    @RequestMapping("/add")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "新用户助力")
    public  Map<String,Object> addAssistance(@ApiParam(required = true,value = "token") @RequestParam(value = "token") String token,
                                             @ApiParam(required = true,value = "助力ID") @RequestParam(value = "assistanceId") Integer assistanceId){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (!isSuccess) {
                return GenResult.TOKEN_INVALID.genResult();
            }
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());
            return assistanceService.addAssistance(assistanceId,userId);
        }
        catch (Exception e){
            log.error("新用户助力错误：----",e);
            return GenResult.FAILED.genResult();
        }


    }

}
