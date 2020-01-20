package com.jclz.fruit.service;

import com.jclz.fruit.entity.Assistance;
import com.jclz.fruit.entity.UserAuths;

import java.util.Map;

public interface AssistanceService {
    Map<String, Object> assistanceList(Integer userId,Integer size,Integer page);

    Map<String, Object> insertAssistance(Assistance assistance);

//    Map<String, Object> addAssistance(Integer assistanceId, UserAuths userAuths) throws Exception;
    Map<String, Object> addAssistance(Integer assistanceId, Integer userId) throws Exception;

    Map<String, Object> assistanceDetail(Integer assistanceId);
}
