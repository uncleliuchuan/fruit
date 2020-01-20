package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private Integer id;
    /**
     * 账号
     */
    private String account;
    /**
     * 权限id 0/普通用户--1/会员
     */
    private Integer roleId;
    /**
     * vip用户过期时间
     */
    private String vipOutTime;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * vip用户充值时间 注：vip用户续费时，用户vip未过期则不覆盖该字段的值
     */
    private String vipTime;

    /**
     * 昵称
     */
    private String userName;
    /**
     * 头像url
     */
    private String userIconUrl;


}