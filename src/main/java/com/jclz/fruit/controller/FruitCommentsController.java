package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.FruitComments;
import com.jclz.fruit.service.FruitCommentsService;
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
@Api(tags = "水果评论API")
@RequestMapping("/fruitComments")
public class FruitCommentsController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    FruitCommentsService fruitCommentsService;

    /**
     * 评论
     * @param token
     * @return
     */
    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "评论列表")
    public  Map<String,Object> fruitCommentsList(@ApiParam(required = false, value = "水果ID") @RequestParam(required = false,value = "fruitId") Integer fruitId,
                                                 @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                 @ApiParam(required = true, value = "每页个数") @RequestParam(value = "size") Integer size,
                                                 @ApiParam(required = true, value = "页数") @RequestParam(value = "page") Integer page){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            int userId = Integer.parseInt(data.get("userId").toString());
            return fruitCommentsService.fruitCommentsList(fruitId,userId,size,page);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }

    }
    /**
     * 评论
     * @param fruitComments
     * @param token
     * @return
     */
    @RequestMapping("/create")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "评论")
    public  Map<String,Object> createFruitComments(@ApiParam(required = true, value = "评论") FruitComments fruitComments,
                                                   @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            int userId = Integer.parseInt(data.get("userId").toString());
            fruitComments.setUserId(userId);
            return fruitCommentsService.createFruitComments(fruitComments);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }

    }


}
