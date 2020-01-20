package com.jclz.fruit.dao;

import com.jclz.fruit.entity.Assistance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssistanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Assistance record);

    int insertSelective(Assistance record);

    Assistance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assistance record);

    int updateByPrimaryKey(Assistance record);

    /**
     * 查询待助力状态下的指定用户和指定水果的助力
     * @param userId
     * @param fruitId
     * @return
     */
    Assistance selectStayByUserId(Integer userId,Integer fruitId);

    List<Assistance> assistanceList(Integer userId, Integer start, Integer size);

    /**
     * 查询待助力状态下的指定用户的助力
     * @param userId
     * @return
     */
    Assistance selectByUserId(Integer userId);

    /**
     * 查询本月是否存在已成功助力
     * @param userId
     * @param firstDayOfMonth
     * @return
     */
    Assistance selectMonthSuccess(@Param("userId") Integer userId, @Param("firstDayOfMonth") String firstDayOfMonth);
}