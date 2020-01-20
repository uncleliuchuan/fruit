package com.jclz.fruit.dao;

import com.jclz.fruit.entity.Fruit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FruitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fruit record);

    int insertSelective(Fruit record);

    Fruit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Fruit record);

    int updateByPrimaryKey(Fruit record);

    List<Fruit> queryFruitList(Integer start, Integer size, Integer fruitType);

    List<Fruit> selectFruitRecommend();

    /**
     * 更新水果已售数量
     * @param fruit
     * @return
     */
    int updateQuantitySold(Fruit fruit);
}