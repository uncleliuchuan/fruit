package com.jclz.fruit.service;

import com.jclz.fruit.entity.UserInformation;

import java.util.Map;

public interface UserInformationService {
    Map<String, Object> update(UserInformation userInformation);
}
