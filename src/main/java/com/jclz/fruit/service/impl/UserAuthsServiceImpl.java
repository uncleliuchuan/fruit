package com.jclz.fruit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.UserAuthsMapper;
import com.jclz.fruit.dao.UserInformationMapper;
import com.jclz.fruit.dao.UserMapper;
import com.jclz.fruit.dao.UserTokenMapper;
import com.jclz.fruit.entity.User;
import com.jclz.fruit.entity.UserAuths;
import com.jclz.fruit.entity.UserInformation;
import com.jclz.fruit.service.UserAuthsService;
import com.jclz.fruit.service.UserInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("userAuthsService")
public class UserAuthsServiceImpl implements UserAuthsService {
    @Autowired
    UserAuthsMapper userAuthsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserTokenMapper userTokenMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserInformationMapper userInformationMapper;
    @Autowired
    RedisTokenManager redisTokenManager;

    @Override
    public Map<String,Object> login(UserAuths userAuths) {
        UserAuths userAuthTemp = userAuthsMapper.selectByUnionid(userAuths.getUnionid());//验证是否入库
        if (userAuthTemp != null) {
            if (userAuthTemp.getUserId() == null) {
                return GenResult.NOT_BIND_SMS.genResult();
            } else {//已绑定手机号，生成token
                User user = userMapper.selectByPrimaryKey(userAuthTemp.getUserId());
                if (null != user) {
                    // exist，查该用户信息
                    // 保存信息到token
                    String token = redisTokenManager.createToken(user);
                    JSONObject json=new JSONObject();
                    json.put("token",token);
                    json.put("userId",user.getId());
                    return GenResult.SUCCESS.genResult(json);

                } else {
                    return GenResult.NOT_BIND_SMS.genResult();
                }

            }
        } else {
            int row = userAuthsMapper.insertSelective(userAuths);
            if (row > 0) {
                return GenResult.NOT_BIND_SMS.genResult();
            }
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String,Object> bindPhone(String telephone, String vcode, String unionid) {
        try {
            String code = redisTemplate.opsForValue().get(telephone).toString();
            if (StringUtils.isEmpty(code)) {
                return GenResult.SMS_CODE_INVALID.genResult();
            }
            if (!code.equals(vcode)) {
                return GenResult.SMS_CODE_ERROR.genResult();
            }
            // 非空判断
            if (telephone == null) {
                return GenResult.FAILED.genResult("电话号码为空，绑定失败");
            }
            UserAuths userAuths = userAuthsMapper.selectByUnionid(unionid);//第三方登录
            if (userAuths == null) {
                return GenResult.FAILED.genResult("第三方账号未入库，绑定失败");
            }
            User user = userMapper.selectByPhone(telephone);
            if (user != null) {
                UserAuths userAuthsByUserId = userAuthsMapper.selectByUserId(user.getId());//查询该手机号是否已被绑定
                if (null!=userAuthsByUserId){
                    return GenResult.SMS_HAD_BIND.genResult();
                }
                userAuths.setUserId(user.getId());
                userAuthsMapper.updateByPrimaryKeySelective(userAuths);//绑定手机号

                // 保存信息到token
                String token = redisTokenManager.createToken(user);
                JSONObject json=new JSONObject();
                json.put("token",token);
                json.put("userId",user.getId());
                return GenResult.SUCCESS.genResult(json);
            } else {
                //没有用户，即新建用户
                Date date = new Date();
                User newUser=new User();
                newUser.setAccount(telephone);
                newUser.setRoleId(0);
                newUser.setCreateTime(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                 userMapper.insert(newUser);
                /**
                 * 用户信息
                 */
                UserInformation information=new UserInformation();
                information.setUserId(newUser.getId());
                information.setUserIconUrl(userAuths.getIconUrl());
                information.setUserName(userAuths.getName());
                userInformationMapper.insertSelective(information);
                /**
                 * 绑定手机号
                 */
                userAuths.setUserId(newUser.getId());
                userAuthsMapper.updateByPrimaryKeySelective(userAuths);//绑定手机号
                String token = redisTokenManager.createToken(newUser);
                JSONObject json=new JSONObject();
                json.put("token",token);
                json.put("userId",newUser.getId());
                return GenResult.SUCCESS.genResult(json);
            }
        } catch (Exception e) {
            log.error("绑定失败----",e);
            return GenResult.FAILED.genResult();
        }
    }
}
