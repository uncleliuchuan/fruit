package com.jclz.fruit;

import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.service.OrderInitializingBean;
import com.jclz.fruit.util.YouMengUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class RedisTest {
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {

//        List<Fruit> shopingCart= redisTemplate.opsForList().range(""+6, 0, -1);
//        Stream.of(shopingCart).forEach(System.out::println);
       /* for (Fruit fruitTemp: shopingCart) {
            shopingCart.remove(fruitTemp);
        }
        Stream.of(shopingCart).forEach(System.out::println);*/
       /* try {
            YouMengUtil.sendIOSCustomizedcast(1+"","您的助力订单已成功，布洛正为您安排发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
