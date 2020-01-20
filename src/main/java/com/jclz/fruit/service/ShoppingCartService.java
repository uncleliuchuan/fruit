package com.jclz.fruit.service;

import java.util.List;
import java.util.Map;

public interface ShoppingCartService {
    Map<String, Object> findCartList(Integer userId);

    Map<String, Object> addLeaseCarList(Integer userId, Integer fruitId);

    Map<String, Object> deleteCart(Integer userId, Integer fruitId);

    Map<String, Object> subtractCart(Integer userId, Integer fruitId);

    Map<String, Object> settlementCart(Integer userId, List<String> fruitIds);
}
