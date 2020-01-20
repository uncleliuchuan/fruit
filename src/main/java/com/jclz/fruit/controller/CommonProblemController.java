package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.enums.CommonProblemEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(tags = "常见问题API")
public class CommonProblemController {
    @RequestMapping("/problems")
    @ApiOperation(httpMethod = "POST",response = Map.class,responseContainer = "Map",value = "常见问题")
    public  Map<String,Object> problems(){
        return GenResult.SUCCESS.genResult(CommonProblemEnum.getCommonProblem());
    }

}
