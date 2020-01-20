package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.UserAddress;
import com.jclz.fruit.service.UserAddressService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import com.jclz.fruit.util.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 第三方登录
 */
@RestController
@RequestMapping("/userAddress")
@Api(tags = "用户收货地址")
public class UserAddressConntroller {
    @Autowired
    RedisTokenManager redisTokenManager;
    @Autowired
    UserAddressService userAddressService;

    /**
     * 收货地址列表
     */
    @RequestMapping("/list")
    @ApiOperation(value = "收货地址列表", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> userAddressList(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            return userAddressService.userAddressList(userId);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }

    /**
     * 添加收货地址
     */
    @RequestMapping("/insert")
    @ApiOperation(value = "添加收货地址", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> addUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                              @ApiParam(required = true, value = "收货地址") UserAddress userAddress) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            userAddress.setUserId(userId);
            return userAddressService.addUserAddress(userAddress);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }
    /**
     * 修改收货地址
     */
    @RequestMapping("/update")
    @ApiOperation(value = "修改收货地址", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> updateUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                             @ApiParam(required = true, value = "收货地址") UserAddress userAddress) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            userAddress.setUserId(userId);
            return userAddressService.updateUserAddress(userAddress);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }
    /**
     * 删除收货地址
     */
    @RequestMapping("/delete")
    @ApiOperation(value = "删除收货地址", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> deleteUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                @ApiParam(required = true, value = "收货地址ID") @RequestParam(value = "id") Integer id) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            return userAddressService.deleteUserAddress(id);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }
    /**
     * 设为默认地址
     */
    @RequestMapping("/setdefault")
    @ApiOperation(value = "设为默认地址", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> setdefaultUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token,
                                                @ApiParam(required = true, value = "收货地址ID") @RequestParam(value = "id") Integer id) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            return userAddressService.setdefaultUserAddress(userId,id);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }
    /**
     * 获取默认地址
     */
    @RequestMapping("/default")
    @ApiOperation(value = "获取默认地址", httpMethod = "POST", responseContainer = "Map", response = Map.class)
    public Map<String,Object> defaultUserAddress(@ApiParam(required = true, value = "token") @RequestParam(value = "token") String token) {

        if (StringUtils.isEmpty(token)){
            return GenResult.PARAMS_ERROR.genResult();
        }
        boolean isSuccess = redisTokenManager.validToken(token);
        if (isSuccess) {
            System.out.println("token校验成功");
            Map<String, Object> validToken = Jwt.validToken(token);
            JSONObject data = (JSONObject) validToken.get("data");
            Integer userId = Integer.parseInt(data.get("userId").toString());

            return userAddressService.defaultUserAddress(userId);
        } else {
            System.out.println("token校验失败");
            return GenResult.TOKEN_INVALID.genResult();
        }
    }
}
