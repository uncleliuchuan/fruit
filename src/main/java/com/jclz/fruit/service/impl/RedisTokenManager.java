package com.jclz.fruit.service.impl;

import com.jclz.fruit.dao.UserTokenMapper;
import com.jclz.fruit.entity.User;
import com.jclz.fruit.entity.UserToken;
import com.jclz.fruit.service.TokenManager;
import com.jclz.fruit.util.Jwt;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("redisTokenManager")
public class RedisTokenManager implements TokenManager {
    /*public String getToken(String loginName,String userPwd) {
        String token = UUID.randomUUID().toString().replace("-", "");
        System.out.println(token);
        return token;
    }*/
    @Autowired
    UserTokenMapper userTokenMapper;

    @Override
    public boolean validToken(String token) {
        if (null != token) {
            Map<String, Object> validToken = Jwt.validToken(token);
            boolean isSuccess = (boolean) validToken.get("isSuccess");
            if (isSuccess) {
                JSONObject data = (JSONObject) validToken.get("data");
                String userId = data.get("userId").toString();
                System.out.println(userId);
                UserToken selectByUserId = userTokenMapper.selectByUserId(Integer.parseInt(userId));
                String selectToken = selectByUserId.getToken();//null
                if (selectToken.equals(token)) {
                    System.out.println(token);
                    System.out.println(selectToken);
                    return true;
                } else {
                    System.out.println(token);
                    System.out.println(selectToken);
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public String createToken(User record) {
        // 保存信息到token
        Map<String, Object> payload = new HashMap<String, Object>();
        Date date = new Date();
        payload.put("userId", record.getId());// 用户id
        payload.put("account", record.getAccount());//
//        payload.put("userPwd", record.getUserPwd());
        payload.put("expirationTime", date.getTime() + 1000 * 60 * 60 * 480);
        payload.put("iat", date.getTime());// 生成时间
        //payload.put("ext", date.getTime() + 1000 * 60 * 60 * 48);// 过期时间48小时
        String createToken = Jwt.createToken(payload);
        UserToken userToken = new UserToken();
        userToken.setToken(createToken);
        userToken.setUserId(record.getId());
        //token过期时间
        userToken.setExpirationTime(new Date(date.getTime() + 1000 * 60 * 60 * 480));
        //添加事务
        int deleteByUserId = userTokenMapper.deleteByUserId(record.getId());
        //int i = 1/0;
        int insert = userTokenMapper.insertSelective(userToken);

        System.out.println(createToken);
        // 返回token
        return createToken;
    }
}
