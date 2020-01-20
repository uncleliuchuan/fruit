package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.AssistanceMapper;
import com.jclz.fruit.dao.FruitCommentsMapper;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.dao.MembershipBenefitsMapper;
import com.jclz.fruit.entity.Assistance;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.FruitCommentsVo;
import com.jclz.fruit.entity.MembershipBenefits;
import com.jclz.fruit.service.MembershipBenefitsService;
import com.jclz.fruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("membershipBenefitsService")
public class MembershipBenefitsServiceImpl implements MembershipBenefitsService {
    @Autowired
    MembershipBenefitsMapper membershipBenefitsMapper;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    AssistanceMapper assistanceMapper;
    @Autowired
    FruitCommentsMapper fruitCommentsMapper;
    @Autowired
    UserService userService;
    @Override
    public Map<String, Object> membershipBenefitsList(Integer userId) {
      List<MembershipBenefits> list= membershipBenefitsMapper.membershipBenefitsList();
        for (MembershipBenefits membershipBenefits:list) {
            Integer fruitId = membershipBenefits.getFruitId();
            Fruit fruit = fruitMapper.selectByPrimaryKey(fruitId);
            membershipBenefits.setFruit(fruit);
            membershipBenefits.setIsReceive(0);//默认未领取
            if (null!=userId){
                Assistance assistance= assistanceMapper.selectStayByUserId(userId,fruitId);
                if (null!=assistance){
                    membershipBenefits.setIsReceive(1);
                }
            }
        }
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> membershipBenefitsDetial(Integer userId, Integer id) {
        /*boolean vip = userService.isVIP(userId);
        if (!vip){
            return GenResult.FAILED.genResult("请先开会员再查看会员福利！！！");
        }*/
        MembershipBenefits membershipBenefits=membershipBenefitsMapper.selectByPrimaryKey(id);
        if (null==membershipBenefits){
            return GenResult.FAILED.genResult("该会员福利不存在！！！");
        }
        Integer fruitId = membershipBenefits.getFruitId();
        Fruit fruit = fruitMapper.selectByPrimaryKey(fruitId);
        fruit.setFruitVipPrice(new BigDecimal(0));
        //评价数量
        Integer commentsNum= fruitCommentsMapper.selectCommentsNum(fruitId);
        FruitCommentsVo fruitComments=fruitCommentsMapper.selectNewCommentsByFruitId(fruitId);
        //水果推荐
        List<Fruit> fruitRecommend=fruitMapper.selectFruitRecommend();
        fruit.setCommentsNum(commentsNum);
        fruit.setFruitComment(fruitComments);
        fruit.setFruitRecommend(fruitRecommend);
        membershipBenefits.setFruit(fruit);
        membershipBenefits.setIsReceive(0);//默认未领取
        if (null!=userId){
            Assistance assistance= assistanceMapper.selectByUserId(userId);
            if (null!=assistance){
                membershipBenefits.setIsReceive(1);
            }
        }
        return GenResult.SUCCESS.genResult(membershipBenefits);
    }
}
