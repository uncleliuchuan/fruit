package com.jclz.fruit.dao;

import com.jclz.fruit.entity.VipRechargeLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VipRechargeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VipRechargeLog record);

    int insertSelective(VipRechargeLog record);

    VipRechargeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VipRechargeLog record);

    int updateByPrimaryKey(VipRechargeLog record);

    VipRechargeLog selectByOrderNo(String orderNo);

    int updateOrderStatus(VipRechargeLog vipRechargeLog);

    List<VipRechargeLog> selectExpiredOrders();

    int delete(String orderNo);
}