package com.jclz.fruit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.UserInformationMapper;
import com.jclz.fruit.dao.UserMapper;
import com.jclz.fruit.dao.UserTokenMapper;
import com.jclz.fruit.entity.User;
import com.jclz.fruit.entity.UserInformation;
import com.jclz.fruit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserTokenMapper userTokenMapper;
    @Autowired
    UserInformationMapper userInformationMapper;
    @Autowired
    RedisTokenManager redisTokenManager;

    @Transactional
    @Override
    public Map<String, Object> login(String phone, String code) {
        String rsCode = redisTemplate.opsForValue().get(phone).toString();
        if (null==rsCode){
            return GenResult.SMS_CODE_INVALID.genResult();
        }
        if (!rsCode.equals(code)){
            return GenResult.SMS_CODE_ERROR.genResult();
        }
        User user = userMapper.selectByPhone(phone);
        if (null != user) {
            String token = redisTokenManager.createToken(user);

            JSONObject json=new JSONObject();
            json.put("token",token);
            json.put("userId",user.getId());

            return GenResult.SUCCESS.genResult(json);
        }else {
            //没有用户，即新建用户
            Date date = new Date();
            User newUser=new User();
            newUser.setAccount(phone);
            newUser.setRoleId(0);
            newUser.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
            int usrID = userMapper.insert(newUser);
            UserInformation information=new UserInformation();
            information.setUserId(newUser.getId());
            userInformationMapper.insertSelective(information);
            newUser.setId(usrID);
            String token = redisTokenManager.createToken(newUser);
            JSONObject json=new JSONObject();
            json.put("token",token);
            json.put("userId",usrID);
            return GenResult.SUCCESS.genResult(json);
        }
    }
    @Transactional
    @Override
    public Map<String, Object> userDetail(Integer userId) {
        try {
        User user = userMapper.selectByPrimaryKey(userId);
        if (null==user){
            return GenResult.FAILED.genResult("用户不存在！！！");
        }
        String vipOutTime = user.getVipOutTime();
        if (vipOutTime != null) {
//						System.out.println("会员剩余时间"+(new Date().compareTo(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime))));

                if (new Date().before(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime)))
                    System.out.println("会员有效");

            if (new Date().after(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime)))
                userMapper.removeVip(userId);
        }
        UserInformation information = userInformationMapper.selectByUserId(userId);
        user.setUserName(information.getUserName());
        user.setUserIconUrl(information.getUserIconUrl());
        return GenResult.SUCCESS.genResult(user);
        } catch (ParseException e) {
            log.error("用户信息错误-----",e);
            return GenResult.FAILED.genResult();
        }
    }
    @Transactional
    @Override
    public boolean isVIP(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user.getRoleId()==0){
            return false;
        }
        String vipOutTime = user.getVipOutTime();
        if (vipOutTime != null) {
//						System.out.println("会员剩余时间"+(new Date().compareTo(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime))));

            try {
//                if (new Date().before(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime)))
//                    System.out.println("会员有效");
                if (user.getRoleId()>0){
                    if (new Date().after(new SimpleDateFormat("yyyyMMddHHmmss").parse(vipOutTime))){
                        userMapper.removeVip(userId);
                        return false;
                    }
                    return true;
                }


            } catch (ParseException e) {
                log.error("用户会员判断错误-----",e);
            }


        }
        return false;
    }
}
