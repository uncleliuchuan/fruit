package com.jclz.fruit.service;

import java.util.Map;

public interface MembershipBenefitsService {
    Map<String, Object> membershipBenefitsList(Integer userId);

    Map<String, Object> membershipBenefitsDetial(Integer userId, Integer id);
}
