package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitCommentsMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.entity.FruitComments;
import com.jclz.fruit.entity.FruitCommentsVo;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.service.FruitCommentsService;
import com.jclz.fruit.service.OrderInitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("fruitCommentsService")
public class FruitCommentsServiceImpl  implements FruitCommentsService {
    @Autowired
    FruitCommentsMapper fruitCommentsMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Override
    public Map<String, Object> createFruitComments(FruitComments fruitComments) {
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//当前时间
        fruitComments.setCreateTime(time);
        int row = fruitCommentsMapper.insertSelective(fruitComments);
        OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(fruitComments.getOrderId());
        if (row>0){
            OrderInitializingBean.fruitCommentsDelayQueueThread.removeDelay(orderInfo);//默认评价队列删除
            orderInfo.setOrderStatus(4);//已评价
            orderInfoMapper.updateOrderStatus(orderInfo);
            return GenResult.SUCCESS.genResult("评论成功");
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> fruitCommentsList(Integer fruitId, Integer userId,Integer size,Integer page) {
        if (page == null) page = 1;
        if (size == null) size = 6;
        Integer start = size * (page - 1);
        if (null==fruitId){
            //我的所有评论
            List<FruitCommentsVo> fruitComments= fruitCommentsMapper.fruitCommentsList(null,userId,start,size);
            return GenResult.SUCCESS.genResult(fruitComments);
        }
        else {
            //水果的评论
            List<FruitCommentsVo> fruitComments= fruitCommentsMapper.fruitCommentsList(fruitId,null,start,size);
            return GenResult.SUCCESS.genResult(fruitComments);
        }
    }
}
