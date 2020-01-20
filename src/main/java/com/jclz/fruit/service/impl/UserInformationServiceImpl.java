package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.UserInformationMapper;
import com.jclz.fruit.entity.UserInformation;
import com.jclz.fruit.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@Service("userInformationService")
public class UserInformationServiceImpl implements UserInformationService {
    @Autowired
    UserInformationMapper userInformationMapper;


    @Override
    public Map<String, Object> update(UserInformation userInformation) {
        Integer userId = userInformation.getUserId();
        if (null == userId) {
            return GenResult.PARAMS_ERROR.genResult("userId为空");
        } else {
            UserInformation selectByUserId = userInformationMapper.selectByUserId(userId);
            UserInformation equalsUserIcon = equalsUserIcon(userInformation, selectByUserId);
            Integer updateByUserId = userInformationMapper.updateByUserId(equalsUserIcon);
            if (updateByUserId != 0) {
                System.out.println("更新用户头像信息成功");
                return GenResult.SUCCESS.genResult("更新用户信息成功");
            } else {
                System.out.println("更新用户头像信息失败");
                return GenResult.FAILED.genResult("更新用户信息失败");
            }
        }
    }
    public static UserInformation equalsUserIcon(UserInformation u1, UserInformation u2) {
        Field[] fields = u1.getClass().getDeclaredFields();
        Field[] fields2 = u2.getClass().getDeclaredFields();
        UserInformation userInformation = new UserInformation();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields2[i].setAccessible(true);

            try {
                if (fields[i].get(u1) == null) {// null时填充u2
                    String name = fields[i].getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
                    // System.out.println("name:" + name);
                    Method m = userInformation.getClass().getMethod("set" + name, fields[i].getType());
                    m.invoke(userInformation, fields2[i].get(u2));
                    // System.out.println("null" + userIcon);
                } else {
                    String name = fields[i].getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
                    // System.out.println("name:" + name);
                    Method m = userInformation.getClass().getMethod("set" + name, fields[i].getType());
                    m.invoke(userInformation, fields[i].get(u1));
                    // System.out.println("not null" + userIcon);
                }
            } catch (Exception e) {
            }
            fields[i].setAccessible(false);
            fields2[i].setAccessible(false);

        }
        System.out.println(userInformation);
        return userInformation;

    }
}
