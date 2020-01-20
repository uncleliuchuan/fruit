package com.jclz.fruit.dao;

import com.jclz.fruit.entity.OrderRefund;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderRefundMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderRefund record);

    int insertSelective(OrderRefund record);

    OrderRefund selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderRefund record);

    int updateByPrimaryKey(OrderRefund record);

    OrderRefund selectByOrderNo(String orderNo);

    OrderRefund selectByOutRefundNo(String outRefundNo);
}