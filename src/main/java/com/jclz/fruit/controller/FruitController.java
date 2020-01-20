package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.enums.FruitTypeEnum;
import com.jclz.fruit.enums.VegetablesTypeEnum;
import com.jclz.fruit.service.FruitService;
import com.jclz.fruit.service.impl.RedisTokenManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "水果API")
@RequestMapping("/fruit")
public class FruitController {
    @Autowired
    FruitService fruitService;
    @Autowired
    RedisTokenManager redisTokenManager;


    @RequestMapping("/fruitTypes")
    @ApiOperation(httpMethod = "POST",response = Map.class,responseContainer = "Map",value = "水果类型")
    public  Map<String,Object> fruitType(){
        return GenResult.SUCCESS.genResult(FruitTypeEnum.getFruitTypes());
    }

    @RequestMapping("/vegetableTypes")
    @ApiOperation(httpMethod = "POST",response = Map.class,responseContainer = "Map",value = "蔬菜类型")
    public  Map<String,Object> vegetableTypes(){
        return GenResult.SUCCESS.genResult(VegetablesTypeEnum.getFruitTypes());
    }


    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "水果列表")
    public  Map<String,Object> fruitList(@ApiParam(required = true, value = "水果类型") @RequestParam(value = "fruitType") Integer fruitType,
                                         @ApiParam(required = true, value = "每页个数") @RequestParam(value = "size") Integer size,
                                         @ApiParam(required = true, value = "页数") @RequestParam(value = "page") Integer page){
        if (null==fruitType||null==size||null==page){
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            return fruitService.fruitList(fruitType, size, page);
        }
        catch (Exception e){
            log.error("水果列表错误：----",e);
            return GenResult.FAILED.genResult();
        }

    }
    @RequestMapping("/detail")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "水果详情")
    public  Map<String,Object> fruitDetail(@ApiParam(required = true, value = "水果ID") @RequestParam(value = "id") Integer id){

            return fruitService.fruitDetail(id);


    }



}
