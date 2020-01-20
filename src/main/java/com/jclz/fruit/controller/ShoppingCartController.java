package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.service.ShoppingCartService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "购物车API")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    ShoppingCartService shoppingCartService;

    /*
       添加商品到购物车
   */
    @RequestMapping("/add")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "添加购物车")
    public Map<String, Object> addGoodsToCartList(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                  @ApiParam(required = true, value = "水果ID") @RequestParam(value = "fruitId")Integer fruitId){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return shoppingCartService.addLeaseCarList(userId, fruitId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("添加购物车错误：------",e);
            return GenResult.FAILED.genResult("添加购物车失败!!!!");
        }

    }

    /*
        获取购物车数据
    */
    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "获取购物车数据")
    public Map<String, Object> findCartList(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return shoppingCartService.findCartList(userId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("获取购物车数据：------",e);
            return GenResult.FAILED.genResult("获取购物车数据错误!!!!");
        }
    }

    /**
     * 删除购物车数据
     * @param token
     * @return
     */
    @RequestMapping("/delete")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "删除购物车数据")
    public Map<String, Object> deleteCart(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "fruitId")Integer fruitId,
                                          @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return shoppingCartService.deleteCart(userId,fruitId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("删除购物车数据：------",e);
            return GenResult.FAILED.genResult("删除购物车数据错误!!!!");
        }
    }
    /**
     * 购物车水果数量减一
     * @param token
     * @return
     */
    @RequestMapping("/subtract")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "购物车水果数量减一")
    public Map<String, Object> subtractCart(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "fruitId")Integer fruitId,
                                          @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){
        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return shoppingCartService.subtractCart(userId,fruitId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("删除购物车数据：------",e);
            return GenResult.FAILED.genResult("删除购物车数据错误!!!!");
        }
    }

    /**
     * 购物车结算
     * @param token
     * @return
     */
    @RequestMapping("/settlement")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "购物车结算")
    public Map<String, Object> settlementCart(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "fruitIds")String fruitIds,
                                              @ApiParam(required = true, value = "token") @RequestParam(value = "token") String token){

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            String[] split = fruitIds.split(",");
            List<String> fruitId = Arrays.asList(split);
            boolean isSuccess = redisTokenManager.validToken(token);
            if (isSuccess) {
                System.out.println("token校验成功");
                Map<String, Object> validToken = Jwt.validToken(token);
                JSONObject data = (JSONObject) validToken.get("data");
                Integer userId = Integer.parseInt(data.get("userId").toString());
                return shoppingCartService.settlementCart(userId,fruitId);
            } else {
                System.out.println("token校验失败");
                return GenResult.TOKEN_INVALID.genResult();
            }
        } catch (Exception e) {
            log.error("购物车结算：------",e);
            return GenResult.FAILED.genResult("购物车结算错误!!!!");
        }
    }

}
