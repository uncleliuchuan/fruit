package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 购物车实体类
 */
@Component
@Data
public class ShoppingCart {
    private Integer sellerId;//水果类型ID
    private String sellerName;//水果类型名称
    private Integer userId;//userID
    private List<Fruit> carInfoList;//购物车中商品
}
