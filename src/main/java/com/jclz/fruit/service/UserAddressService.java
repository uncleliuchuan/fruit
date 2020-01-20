package com.jclz.fruit.service;

import com.jclz.fruit.entity.UserAddress;

import java.util.Map;

public interface UserAddressService {
    Map<String, Object> userAddressList(Integer userId);

    Map<String, Object> addUserAddress(UserAddress userAddress);

    Map<String, Object> updateUserAddress(UserAddress userAddress);

    Map<String, Object> deleteUserAddress(Integer id);

    Map<String, Object> defaultUserAddress(Integer userId);

    Map<String, Object> setdefaultUserAddress(Integer userId, Integer id);
}
