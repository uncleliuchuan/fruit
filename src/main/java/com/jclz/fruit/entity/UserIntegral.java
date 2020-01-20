package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserIntegral {
    private Integer id;

    private Integer userId;

    private Integer integral;

}