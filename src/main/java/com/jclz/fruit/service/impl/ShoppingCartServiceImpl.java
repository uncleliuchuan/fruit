package com.jclz.fruit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.service.ShoppingCartService;
import com.jclz.fruit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    UserService userService;

    @Override
    public Map<String, Object> findCartList(Integer userId) {
        List<Fruit> fruits= redisTemplate.opsForList().range(""+userId, 0, -1);
        List<Fruit> delList = new ArrayList();
        log.info("用户购物车----"+fruits);
        boolean isVIP=  userService.isVIP(userId);
        if (!fruits.isEmpty()){
         for (Fruit fruit: fruits) {
            if (isVIP){//会员即小计为会员价，，否则非会员价
                fruit.setSubtotal(fruit.getFruitVipPrice().multiply(new BigDecimal(fruit.getShoppingCartNum())));
            }else {
                fruit.setSubtotal(fruit.getFruitPrice().multiply(new BigDecimal(fruit.getShoppingCartNum())));
            }
             Fruit old = fruitMapper.selectByPrimaryKey(fruit.getId());
            if (old.getStatus()<0){
                delList.add(fruit);
            }
         }
            fruits.removeAll(delList);
            redisTemplate.delete(""+userId);
            if (!fruits.isEmpty()){
                redisTemplate.opsForList().rightPushAll(""+userId,fruits);
            }
        }

        return GenResult.SUCCESS.genResult(fruits);
    }

    @Override
    public Map<String, Object> addLeaseCarList(Integer userId, Integer fruitId) {
        List<Fruit> fruits= redisTemplate.opsForList().range(""+userId, 0, -1);
        Fruit fruit = fruitMapper.selectByPrimaryKey(fruitId);
      boolean isVIP=  userService.isVIP(userId);
        Date date = new Date();
        if(null==fruits){//购物车为空新建
            fruit.setShoppingCartTime(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
            fruit.setShoppingCartNum(1);
            if (isVIP){//会员即小计为会员价，，否则非会员价
                fruit.setSubtotal(fruit.getFruitVipPrice());
            }else {
                fruit.setSubtotal(fruit.getFruitPrice());
            }
            fruits.add(fruit);

            redisTemplate.delete(""+userId);
            redisTemplate.opsForList().rightPushAll(""+userId,fruits);
//            redisTemplate.opsForValue().set(""+userId,fruits);
            return GenResult.SUCCESS.genResult();
        }else {
            boolean isHaveFruit=false;
            for (Fruit fruitTemp : fruits) {//判断购物车中是否含此水果
                if (fruitId==fruitTemp.getId()){
                    fruitTemp.setShoppingCartNum(fruitTemp.getShoppingCartNum()+1);
//                    fruitTemp.setShoppingCartTime(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                    if (isVIP){//会员即小计为会员价，，否则非会员价
                        fruitTemp.setSubtotal(fruitTemp.getFruitVipPrice().multiply(new BigDecimal(fruitTemp.getShoppingCartNum())));
                    }else {
                        fruitTemp.setSubtotal(fruitTemp.getFruitPrice().multiply(new BigDecimal(fruitTemp.getShoppingCartNum())));
                    }
                    isHaveFruit=true;
                    break;
                }
            }
            if (!isHaveFruit){//购物车中不否含此水果则添加
                fruit.setShoppingCartTime(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
                fruit.setShoppingCartNum(1);
                if (isVIP){//会员即小计为会员价，，否则非会员价
                    fruit.setSubtotal(fruit.getFruitVipPrice());
                }else {
                    fruit.setSubtotal(fruit.getFruitPrice());
                }
                fruits.add(fruit);
                //购物车按照添加时间排序
                Collections.sort(fruits, new Comparator<Fruit>()
                {
                    public int compare(Fruit a1, Fruit a2)
                    {
                        return a2.getShoppingCartTime().compareTo(a1.getShoppingCartTime());
                    }
                });
            }

            redisTemplate.delete(""+userId);
            redisTemplate.opsForList().rightPushAll(""+userId,fruits);
//            redisTemplate.opsForValue().set(""+userId,fruits);
            return GenResult.SUCCESS.genResult();
        }
    }

    @Override
    public Map<String, Object> deleteCart(Integer userId, Integer fruitId) {
        List<Fruit> fruits= redisTemplate.opsForList().range(""+userId, 0, -1);
        List<Fruit> delList = new ArrayList();
        for (Fruit fruitTemp : fruits) {//判断购物车中是否含此水果
            if (fruitId==fruitTemp.getId()){
                delList.add(fruitTemp);
            }
        }
        fruits.removeAll(delList);
        redisTemplate.delete(""+userId);
        if (!fruits.isEmpty()){
        redisTemplate.opsForList().rightPushAll(""+userId,fruits);
        }
        return GenResult.SUCCESS.genResult();
    }

    @Override
    public Map<String, Object> subtractCart(Integer userId, Integer fruitId) {
        List<Fruit> fruits= redisTemplate.opsForList().range(""+userId, 0, -1);
        boolean isVIP=  userService.isVIP(userId);
        for (Fruit fruitTemp : fruits) {//判断购物车中是否含此水果
            if (fruitId==fruitTemp.getId()){
                if (fruitTemp.getShoppingCartNum()==1){
                    return GenResult.SUCCESS.genResult("数量到底了，不能再减了！");
                }
                fruitTemp.setShoppingCartNum(fruitTemp.getShoppingCartNum()-1);
                if (isVIP){//会员即小计为会员价，，否则非会员价
                    fruitTemp.setSubtotal(fruitTemp.getFruitVipPrice().multiply(new BigDecimal(fruitTemp.getShoppingCartNum())));
                }else {
                    fruitTemp.setSubtotal(fruitTemp.getFruitPrice().multiply(new BigDecimal(fruitTemp.getShoppingCartNum())));
                }
                break;
            }
        }
        redisTemplate.delete(""+userId);
        redisTemplate.opsForList().rightPushAll(""+userId,fruits);
//        redisTemplate.opsForValue().set(""+userId,fruits);
        return GenResult.SUCCESS.genResult();
    }

    @Override
    public Map<String, Object> settlementCart(Integer userId, List<String> fruitIds) {
        List<Fruit> fruits= redisTemplate.opsForList().range(""+userId, 0, -1);
        BigDecimal total=new BigDecimal(0.00);
        for (Fruit fruitTemp : fruits) {//判断购物车中是否含此水果
            if (fruitIds.contains(""+fruitTemp.getId())){
                total=total.add(fruitTemp.getSubtotal());
            }
        }
        JSONObject json=new JSONObject();
        json.put("total",total);
        return GenResult.SUCCESS.genResult(json);
    }
}
