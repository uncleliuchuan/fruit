package com.jclz.fruit.service;

import com.jclz.fruit.entity.UserAuths;

import java.util.Map;

public interface UserAuthsService {
    /**
     * 第三方登录
     *
     * @param userAuths
     * @return
     */
    Map<String,Object> login(UserAuths userAuths);

    /**
     * 绑定手机号
     *
     * @param telephone
     * @param vcode
     * @param unionid
     * @return
     */
    Map<String,Object> bindPhone(String telephone, String vcode, String unionid);
}
