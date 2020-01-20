package com.jclz.fruit.service;

import java.util.Map;

public interface UserService {

    Map<String, Object> login(String phone, String code);

    Map<String, Object> userDetail(Integer userId);

    boolean isVIP(Integer userId);
}
