package com.jclz.fruit.listener;

import com.jclz.fruit.dao.*;
import com.jclz.fruit.entity.*;
import com.jclz.fruit.util.YouMengUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听所有db的过期事件__keyevent@*__:expired"
 */
@Component
public class RedisExpiredListener extends KeyExpirationEventMessageListener {
    private static Logger log = Logger.getLogger(KeyExpirationEventMessageListener.class);
    @Autowired
    VipRechargeLogMapper vipRechargeLogMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    AssistanceMapper assistanceMapper;
    @Autowired
    UserAuthsMapper userAuthsMapper;
    @Autowired
    AssistanceLogMapper assistanceLogMapper;
    @Autowired
    MembershipBenefitsMapper membershipBenefitsMapper;


    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public RedisExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     *
     * @param message
     * @param pattern
     */

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        log.info("key过期触发----");
        if (expiredKey.contains("fruit")){
            //水果订单
            if (expiredKey.startsWith("list:")){
                OrderInfo orderInfo= orderInfoMapper.selectByOrderNo(expiredKey.substring(5));
                if (null != orderInfo) {//订单已入库
                    if (orderInfo.getOrderStatus()== 0) {//订单已入库>>>订单未支付
                        List<OrderInfo> list=orderInfoMapper.selectByOuterTradeNo(orderInfo.getOuterTradeNo());
                        for (OrderInfo o: list) {//过期未支付删除订单
                            o.setOrderStatus(-1);//修改订单状态为支付失败
                            orderInfoMapper.updateOrderStatus(o);
                        }

                    }
                }
            }else {
                OrderInfo orderInfo= orderInfoMapper.selectByOrderNo(expiredKey);
                //根据是否支付改变订单状态 》》1》支付操作完成时改变订单状态为待消费》》2》 超出支付有效期且支付状态仍为待支付 改变状态为订单已失效
                if (null != orderInfo) {//订单已入库
                    if (orderInfo.getOrderStatus()== 0) {//订单已入库>>>订单未支付
                        orderInfo.setOrderStatus(-1);//修改订单状态为支付失败
                        orderInfoMapper.updateOrderStatus(orderInfo);
                    }
                }
            }

        }
        else if (expiredKey.startsWith("vip")){//vip过期删除
            VipRechargeLog vipRechargeLog = vipRechargeLogMapper.selectByOrderNo(expiredKey);
            if (vipRechargeLog!=null){
              if (vipRechargeLog.getOrderStatus()==0){
                  vipRechargeLogMapper.delete(expiredKey);
              }
          }
        }else if (expiredKey.startsWith("assistance")){//助力订单过期删除
            Integer assistanceId=Integer.parseInt(expiredKey.substring(11,expiredKey.indexOf("user:")));
            Assistance assistance = assistanceMapper.selectByPrimaryKey(assistanceId);
            if (null!=assistance&&assistance.getAssistanceStatus()==0){
                assistanceMapper.deleteByPrimaryKey(assistanceId);//删除助力
//                List<AssistanceLog> logs=assistanceLogMapper.selectByAssistanceId(assistanceId);
                assistanceLogMapper.deleteByAssistanceId(assistanceId);//删除助力日志
               /* for (AssistanceLog assistanceLog: logs) {
                    UserAuths userAuths = userAuthsMapper.selectByPrimaryKey(assistanceLog.getAuthId());
                    if (null==userAuths.getUserId()){//没有绑定手机号可以删除
                        userAuthsMapper.deleteByPrimaryKey(assistanceLog.getAuthId());//删除新用户
                    }
                }*/
                MembershipBenefits membershipBenefits = membershipBenefitsMapper.selectByFruitId(assistance.getFruitId());
                if (null!=membershipBenefits){
                    membershipBenefits.setSurplusNum(membershipBenefits.getSurplusNum()+1);
                    membershipBenefitsMapper.updateByPrimaryKey(membershipBenefits);//领取失败，会员福利总数量加一
                }
                try {
                    YouMengUtil.sendAndroidCustomizedcast(assistance.getUserId(),"您的助力订单已超时，您可以重新领取会员福利，感谢您的支持(＾－＾)V","系统通知");
                    YouMengUtil.sendIOSCustomizedcast(assistance.getUserId()+"","您的助力订单已超时，您可以重新领取会员福利，感谢您的支持(＾－＾)V","系统通知");
                } catch (Exception e) {
                    log.error("友盟推送错误：----",e);                }

            }
        }
    }
}
