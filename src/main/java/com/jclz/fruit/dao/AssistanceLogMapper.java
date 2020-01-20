package com.jclz.fruit.dao;

import com.jclz.fruit.entity.AssistanceLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AssistanceLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AssistanceLog record);

    int insertSelective(AssistanceLog record);

    AssistanceLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AssistanceLog record);

    int updateByPrimaryKey(AssistanceLog record);

    AssistanceLog selectByUnionidAssistanceId(Integer assistanceId, Integer authId);

    List<AssistanceLog> selectByAssistanceId(Integer assistanceId);

    int deleteByAssistanceId(Integer assistanceId);

    AssistanceLog selectByAuthId(Integer authId);
}