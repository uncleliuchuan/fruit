package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.UserIntegralMapper;
import com.jclz.fruit.entity.UserIntegral;
import com.jclz.fruit.service.UserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("userIntegralService")
public class UserIntegralServiceImpl implements UserIntegralService {
    @Autowired
    UserIntegralMapper userIntegralMapper;
    @Override
    public Map<String, Object> userIntegralDetail(Integer userId) {
        UserIntegral userIntegral = userIntegralMapper.selectByUserId(userId);
        if (null==userIntegral){
            userIntegral=new UserIntegral();
            userIntegral.setIntegral(0);
            userIntegral.setUserId(userId);
            userIntegralMapper.insertSelective(userIntegral);
            return GenResult.SUCCESS.genResult(0);
        }
        return GenResult.SUCCESS.genResult(userIntegral.getIntegral());
    }

    @Override
    public boolean insertOrUpdate(UserIntegral userIntegral) {
        UserIntegral old = userIntegralMapper.selectByUserId(userIntegral.getUserId());
        if (null==old){
            userIntegralMapper.insertSelective(userIntegral);
            return true;
        }else {
            old.setIntegral(old.getIntegral()+userIntegral.getIntegral());
            userIntegralMapper.updateByPrimaryKeySelective(old);
            return true;
        }
    }
}
