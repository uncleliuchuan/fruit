package com.jclz.fruit;

import com.jclz.fruit.wxpay.util.WxPayCore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.SortedMap;
import java.util.UUID;

@SpringBootTest
class FruitApplicationTests {

    @Test
    void contextLoads() {
       /* String orderType = "fruit";
        String order = "fruit" + UUID.randomUUID().toString().substring(27);
        String total_fee = "";//充值所耗费的rmb   单位为元
        DecimalFormat df = new DecimalFormat("#0.00");
        total_fee = df.format(0.010001);
        SortedMap<Object, Object> wxAppparameters = WxPayCore.createWxPayParam(order, total_fee, orderType);
        System.out.println(wxAppparameters);*/
    }

}
