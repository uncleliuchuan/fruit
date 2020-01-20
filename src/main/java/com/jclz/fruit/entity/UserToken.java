package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
public class UserToken {
    private Integer id;
    /**
     * 用户token
     */
    private String token;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 过期时间
     */
    private Date expirationTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}