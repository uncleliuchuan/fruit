package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 我的助力
 */
@Component
@Data
public class Assistance {
    private Integer id;

    private Integer fruitId;//水果ID

    private Integer userId;//发起人ID

    private String createTime;//创建时间

    private Integer assistanceStatus;//助力状态 0-待助力 1-助力成功

    private String successTime;//助力成功时间

    private Fruit fruit;//水果ID
    /**
     *用户地址ID
     */
    private Integer addressId;

    private UserInformation userInformation;//发起人信息

    private List<UserInformation> userAuths;//助力人信息

    private long expire;//剩余秒数

    private String name;//收货人姓名

    private String phone;//收货人手机号

    private String address;//收货人详细地址

}