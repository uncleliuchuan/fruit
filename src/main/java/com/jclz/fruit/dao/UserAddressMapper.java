package com.jclz.fruit.dao;

import com.jclz.fruit.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserAddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    UserAddress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAddress record);

    int updateByPrimaryKey(UserAddress record);

    List<UserAddress> userAddressList(Integer userId);

    UserAddress getDefaultUserAddress(Integer userId);

    int unDefaultUserAddress(Integer id);

    int defaultUserAddress(Integer id);
}