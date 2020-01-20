package com.jclz.fruit.dao;

import com.jclz.fruit.entity.UserAuths;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAuthsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuths record);

    int insertSelective(UserAuths record);

    UserAuths selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuths record);

    int updateByPrimaryKey(UserAuths record);

    UserAuths selectByUnionid(String unionid);

    UserAuths selectByUserId(Integer userId);
}