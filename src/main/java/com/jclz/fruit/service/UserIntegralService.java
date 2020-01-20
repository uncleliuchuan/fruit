package com.jclz.fruit.service;

import com.jclz.fruit.entity.UserIntegral;

import java.util.Map;

public interface UserIntegralService {
    Map<String, Object> userIntegralDetail(Integer userId);

    boolean insertOrUpdate(UserIntegral userIntegral);
}
