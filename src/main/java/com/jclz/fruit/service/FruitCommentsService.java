package com.jclz.fruit.service;

import com.jclz.fruit.entity.FruitComments;

import java.util.Map;

public interface FruitCommentsService {
    Map<String, Object> createFruitComments(FruitComments fruitComments);

    Map<String, Object> fruitCommentsList(Integer fruitId, Integer userId,Integer size,Integer page);
}
