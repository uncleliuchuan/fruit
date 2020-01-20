package com.jclz.fruit.service;

import java.util.Map;

public interface FruitService {
    Map<String, Object> fruitList(Integer fruitType, Integer size, Integer page);

    Map<String, Object> fruitDetail(Integer id);
}
