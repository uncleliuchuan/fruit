package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserAddress {
    private Integer id;

    private String name;

    private String phone;

    private String area;

    private String address;

    private Integer isDefault;

    private Integer userId;


}