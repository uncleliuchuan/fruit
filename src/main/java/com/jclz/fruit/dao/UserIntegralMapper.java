package com.jclz.fruit.dao;

import com.jclz.fruit.entity.UserIntegral;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserIntegralMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserIntegral record);

    int insertSelective(UserIntegral record);

    UserIntegral selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserIntegral record);

    int updateByPrimaryKey(UserIntegral record);

    UserIntegral selectByUserId(Integer userId);
}