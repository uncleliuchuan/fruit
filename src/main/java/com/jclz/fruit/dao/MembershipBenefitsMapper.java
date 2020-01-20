package com.jclz.fruit.dao;

import com.jclz.fruit.entity.MembershipBenefits;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MembershipBenefitsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MembershipBenefits record);

    int insertSelective(MembershipBenefits record);

    MembershipBenefits selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MembershipBenefits record);

    int updateByPrimaryKey(MembershipBenefits record);

    List<MembershipBenefits> membershipBenefitsList();

    int reSetMembershipBenefits();

    int receiveBenefits(Integer fruitId);

    MembershipBenefits selectByFruitId(Integer fruitId);
}