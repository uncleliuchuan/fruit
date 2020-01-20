package com.jclz.fruit.service;

import com.jclz.fruit.entity.User;

public interface TokenManager {
    boolean validToken(String token);

    String createToken(User record);


}
