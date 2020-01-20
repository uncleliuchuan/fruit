package com.jclz.fruit.dao;

import com.jclz.fruit.entity.AlipaymentOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AlipaymentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlipaymentOrder record);

    int insertSelective(AlipaymentOrder record);

    AlipaymentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlipaymentOrder record);

    int updateByPrimaryKey(AlipaymentOrder record);

    AlipaymentOrder selectByOutTradeNo(String outTradeNo);
}