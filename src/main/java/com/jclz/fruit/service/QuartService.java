package com.jclz.fruit.service;

import com.jclz.fruit.dao.MembershipBenefitsMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.VipRechargeLogMapper;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.VipRechargeLog;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api
@Component
@Slf4j

public class QuartService {
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    VipRechargeLogMapper vipRechargeLogMapper;
    @Autowired
    MembershipBenefitsMapper membershipBenefitsMapper;

    @Scheduled(cron = " 0 0 5 * * ?")
    @Transactional
    public void clearExpiredOrders() {//清除过期订单

        List<OrderInfo> orderInfos = orderInfoMapper.selectExpiredOrders();
        System.out.println("定时清除过期订单-------" + orderInfos);
        try {
            for (OrderInfo orderInfo : orderInfos) {
                orderInfoMapper.deleteByPrimaryKey(orderInfo.getId());
            }

        } catch (Exception e) {
            log.error("定时清除过期订单报错>>" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        }
        List<VipRechargeLog> vipRechargeLogs = vipRechargeLogMapper.selectExpiredOrders();
        System.out.println("定时清除过期订单-------" + vipRechargeLogs);
        try {
            for (VipRechargeLog vipRechargeLog : vipRechargeLogs) {
                vipRechargeLogMapper.deleteByPrimaryKey(vipRechargeLog.getId());
            }

        } catch (Exception e) {
            log.error("定时清除过期订单报错>>" + new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
        }
    }
    @Scheduled(cron = " 0 0 1 * * ?")
    @Transactional
    public void reSetMembershipBenefits() {//重置会员福利
        membershipBenefitsMapper.reSetMembershipBenefits();

    }
}
