package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.*;
import com.jclz.fruit.entity.*;
import com.jclz.fruit.service.AssistanceService;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.util.DateUtil;
import com.jclz.fruit.util.YouMengUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("assistanceService")
public class AssistanceServiceImpl implements AssistanceService {
    @Autowired
    AssistanceMapper assistanceMapper;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserAuthsMapper userAuthsMapper;
    @Autowired
    AssistanceLogMapper assistanceLogMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    UserInformationMapper userInformationMapper;
    @Autowired
    MembershipBenefitsMapper membershipBenefitsMapper;
    @Autowired
    UserAddressMapper userAddressMapper;
    @Override
    public Map<String, Object> assistanceList(Integer userId,Integer size,Integer page) {
        if (page == null) page = 1;
        if (size == null) size = 4;
        Integer start = size * (page - 1);
       List<Assistance> list= assistanceMapper.assistanceList(userId,start, size);
        for (Assistance assistance: list) {
            Fruit fruit = fruitMapper.selectByPrimaryKey(assistance.getFruitId());
            assistance.setFruit(fruit);
        }
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> insertAssistance(Assistance assistance) {

        Integer userId = assistance.getUserId();
        boolean vip = userService.isVIP(userId);
        if (!vip){
            return GenResult.FAILED.genResult("非会员不能领取会员福利！！！");
        }
        Assistance stay= assistanceMapper.selectByUserId(userId);
        if (null!=stay){
            return GenResult.FAILED.genResult("您已存在待助力订单，请前往微信分享，5个新用户助力成功即可免费领取会员福利！！！");
        }
        //查询本月是否存在已成功助力
        String firstDayOfMonth = DateUtil.getFirstDayOfMonth();
        Assistance success= assistanceMapper.selectMonthSuccess(userId,firstDayOfMonth);
        if (null!=success){
            return GenResult.FAILED.genResult("您本月已领取过会员福利，请下月再来领取！！！");
        }
        MembershipBenefits membershipBenefits = membershipBenefitsMapper.selectByFruitId(assistance.getFruitId());
        if (null==membershipBenefits){
            return GenResult.FAILED.genResult("该会员福利不存在！！！");
        }else {
            if (membershipBenefits.getSurplusNum()<=0){
                return GenResult.FAILED.genResult("该会员福利已领完，请领取其他会员福利！！！");
            }
            if (membershipBenefits.getTodaySurplusNum()<=0){
                return GenResult.FAILED.genResult("该会员福利今日领取已达上限，请领取其他会员福利！！！");
            }
        }
        membershipBenefits.setSurplusNum(membershipBenefits.getSurplusNum()-1);
        membershipBenefits.setTodaySurplusNum(membershipBenefits.getTodaySurplusNum()-1);
        membershipBenefitsMapper.updateByPrimaryKey(membershipBenefits);//领取以后，会员福利总数量和今日数量减一
        assistance.setCreateTime(DateUtil.getTime());
        assistance.setAssistanceStatus(0);
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(assistance.getAddressId());
        assistance.setName(userAddress.getName());
        assistance.setPhone(userAddress.getPhone());
        assistance.setAddress(userAddress.getArea()+" "+userAddress.getAddress());
        int row = assistanceMapper.insertSelective(assistance);
        if (row>0){
            redisTemplate.opsForValue().set("assistance:"+assistance.getId()+"user:"+userId,assistance.getId(),2, TimeUnit.DAYS);
            return GenResult.SUCCESS.genResult(assistance.getId());
        }
        return GenResult.FAILED.genResult();
    }
/*
    @Override
    public Map<String, Object> addAssistance(Integer assistanceId, UserAuths userAuths) throws Exception {
        Assistance assistance = assistanceMapper.selectByPrimaryKey(assistanceId);
        if (null==assistance){
            return GenResult.FAILED.genResult("该助力已过期！！！");
        }
        UserAuths old = userAuthsMapper.selectByUnionid(userAuths.getUnionid());
        if (null!=old){
            AssistanceLog assistanceLog = assistanceLogMapper.selectByUnionidAssistanceId(assistanceId, old.getId());
            if (null!=assistanceLog){
                return GenResult.FAILED.genResult("您已助力过该订单！！！");
            }
            return GenResult.FAILED.genResult("只有新用户才可以助力，您不是新用户，或已助力过其他订单！！！");
        }
        userAuthsMapper.insertSelective(userAuths);
        AssistanceLog assistanceLog =new AssistanceLog();
        assistanceLog.setAssistanceId(assistanceId);
        assistanceLog.setAuthId(userAuths.getId());
        assistanceLog.setSponsorId(assistance.getUserId());
        assistanceLog.setCreateTime(DateUtil.getTime());
        int row = assistanceLogMapper.insertSelective(assistanceLog);
        if (row>0){
            List<AssistanceLog> logs=assistanceLogMapper.selectByAssistanceId(assistanceId);
            if (logs.size()==5){//助力已满5人，助力成功
                assistance.setAssistanceStatus(1);
                assistance.setSuccessTime(DateUtil.getTime());
                assistanceMapper.updateByPrimaryKey(assistance);
                Fruit fruit = fruitMapper.selectByPrimaryKey(assistance.getFruitId());
                OrderInfo orderInfo=new OrderInfo();
                orderInfo.setOrderNo(UUID.randomUUID().toString());
                orderInfo.setShopId(fruit.getFruitType());
                orderInfo.setFruitCount(1);
                orderInfo.setFruitAmountTotal(new BigDecimal(0));
                orderInfo.setOrderStatus(1);
                orderInfo.setCreateTime(DateUtil.getTime());
                orderInfo.setCreateUser(assistance.getUserId());
                orderInfo.setFruitId(assistance.getFruitId());
                orderInfo.setBody("助力已满5人，助力成功！！！");
                orderInfo.setFruitName(fruit.getFruitName());
                orderInfo.setAddressId(assistance.getAddressId());
                orderInfo.setOrderInfoType(1);//助力订单
                orderInfo.setName(assistance.getName());
                orderInfo.setPhone(assistance.getPhone());
                orderInfo.setAddress(assistance.getAddress());
                orderInfoMapper.insertSelective(orderInfo);
                redisTemplate.delete("assistance:"+assistance.getId()+"user:"+assistance.getUserId());
                fruit.setFruitAcceptedNum(fruit.getFruitAcceptedNum()+1);
                fruitMapper.updateQuantitySold(fruit);
                YouMengUtil.sendAndroidCustomizedcast(assistance.getUserId(),"您的助力订单已成功，布洛正为您安排发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
                YouMengUtil.sendIOSCustomizedcast(assistance.getUserId()+"","您的助力订单已成功，布洛正为您安排发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
            }
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }*/
    @Override
    public Map<String, Object> addAssistance(Integer assistanceId, Integer userId) throws Exception {
        Assistance assistance = assistanceMapper.selectByPrimaryKey(assistanceId);
        if (null==assistance){
            return GenResult.FAILED.genResult("该助力已过期！！！");
        }else {
            if (userId==assistance.getUserId()){
                return GenResult.FAILED.genResult("发起人无法助力！！！");
            }
        }

        AssistanceLog old = assistanceLogMapper.selectByAuthId(userId);
        if (null!=old){
            return GenResult.FAILED.genResult("您已助力过订单，不是新用户！！！");
        }
        AssistanceLog assistanceLog =new AssistanceLog();
        assistanceLog.setAssistanceId(assistanceId);
        assistanceLog.setAuthId(userId);
        assistanceLog.setSponsorId(assistance.getUserId());
        assistanceLog.setCreateTime(DateUtil.getTime());
        int row = assistanceLogMapper.insertSelective(assistanceLog);
        if (row>0){
            List<AssistanceLog> logs=assistanceLogMapper.selectByAssistanceId(assistanceId);
            if (logs.size()==5){//助力已满5人，助力成功
                assistance.setAssistanceStatus(1);
                assistance.setSuccessTime(DateUtil.getTime());
                assistanceMapper.updateByPrimaryKey(assistance);
                Fruit fruit = fruitMapper.selectByPrimaryKey(assistance.getFruitId());
                OrderInfo orderInfo=new OrderInfo();
                orderInfo.setOrderNo(UUID.randomUUID().toString());
                orderInfo.setShopId(fruit.getFruitType());
                orderInfo.setFruitCount(1);
                orderInfo.setFruitAmountTotal(new BigDecimal(0));
                orderInfo.setOrderStatus(1);
                orderInfo.setCreateTime(DateUtil.getTime());
                orderInfo.setCreateUser(assistance.getUserId());
                orderInfo.setFruitId(assistance.getFruitId());
                orderInfo.setBody("助力已满5人，助力成功！！！");
                orderInfo.setFruitName(fruit.getFruitName());
                orderInfo.setAddressId(assistance.getAddressId());
                orderInfo.setOrderInfoType(1);//助力订单
                orderInfo.setName(assistance.getName());
                orderInfo.setPhone(assistance.getPhone());
                orderInfo.setAddress(assistance.getAddress());
                orderInfoMapper.insertSelective(orderInfo);
                redisTemplate.delete("assistance:"+assistance.getId()+"user:"+assistance.getUserId());
                fruit.setFruitAcceptedNum(fruit.getFruitAcceptedNum()+1);
                fruitMapper.updateQuantitySold(fruit);
                YouMengUtil.sendAndroidCustomizedcast(assistance.getUserId(),"您的助力订单已成功，我们正为您安排发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
                YouMengUtil.sendIOSCustomizedcast(assistance.getUserId()+"","您的助力订单已成功，我们正为您安排发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
            }
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }
    @Override
    public Map<String, Object> assistanceDetail(Integer assistanceId) {
        Assistance assistance = assistanceMapper.selectByPrimaryKey(assistanceId);
        if (null==assistance){
            return GenResult.FAILED.genResult("该助力已过期！！！");
        }
        List<AssistanceLog> logs=assistanceLogMapper.selectByAssistanceId(assistanceId);
        UserInformation information = userInformationMapper.selectByUserId(assistance.getUserId());
        assistance.setUserInformation(information);
        List<UserInformation> list=new ArrayList<>();
        for (AssistanceLog assistanceLog: logs) {
            UserInformation auth = userInformationMapper.selectByUserId(assistanceLog.getAuthId());
            list.add(auth);
        }
        assistance.setUserAuths(list);
        Fruit fruit = fruitMapper.selectByPrimaryKey(assistance.getFruitId());
        assistance.setFruit(fruit);
        long expire = redisTemplate.getExpire("assistance:"+assistance.getId()+"user:"+assistance.getUserId(), TimeUnit.SECONDS);
        assistance.setExpire(expire);
        return GenResult.SUCCESS.genResult(assistance);
    }
}
