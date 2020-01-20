package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitCommentsMapper;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.FruitComments;
import com.jclz.fruit.entity.FruitCommentsVo;
import com.jclz.fruit.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("fruitService")
public class FruitServiceImpl implements FruitService {
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    FruitCommentsMapper fruitCommentsMapper;

    @Override
    public Map<String, Object> fruitList(Integer fruitType, Integer size, Integer page) {
        if (page == null) page = 1;
        if (size == null) size = 4;
        Integer start = size * (page - 1);
        List<Fruit> fruits = fruitMapper.queryFruitList(start, size,fruitType);
        return GenResult.SUCCESS.genResult(fruits);
    }

    @Override
    public Map<String, Object> fruitDetail(Integer fruitId) {
        //评价数量
        Integer commentsNum= fruitCommentsMapper.selectCommentsNum(fruitId);

        Fruit fruit=fruitMapper.selectByPrimaryKey(fruitId);
        FruitCommentsVo fruitComments=fruitCommentsMapper.selectNewCommentsByFruitId(fruitId);
        //水果推荐
        List<Fruit> fruitRecommend=fruitMapper.selectFruitRecommend();
        fruit.setCommentsNum(commentsNum);
        fruit.setFruitComment(fruitComments);
        fruit.setFruitRecommend(fruitRecommend);

        return GenResult.SUCCESS.genResult(fruit);
    }
}
