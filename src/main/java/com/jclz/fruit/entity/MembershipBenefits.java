package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 会员福利
 */
@Component
@Data
public class MembershipBenefits {
    private Integer id;

    private Integer fruitId;//水果ID

    private Integer surplusNum;//剩余数量

    private Integer todaySurplusNum;//今日剩余数量

    private  Fruit fruit;

    private Integer isReceive;//是否领取 0否-1是

}