package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.UserAddressMapper;
import com.jclz.fruit.entity.UserAddress;
import com.jclz.fruit.service.UserAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    UserAddressMapper userAddressMapper;

    @Override
    public Map<String, Object> userAddressList(Integer userId) {
        List<UserAddress> list=userAddressMapper.userAddressList(userId);
        return GenResult.SUCCESS.genResult(list);
    }

    @Override
    public Map<String, Object> addUserAddress(UserAddress userAddress) {
        int row = userAddressMapper.insertSelective(userAddress);
        if (row>0){
            return GenResult.SUCCESS.genResult(userAddress.getId());
        }
        return GenResult.FAILED.genResult("用户收货地址添加错误！！！！");
    }

    @Override
    public Map<String, Object> updateUserAddress(UserAddress userAddress) {
        int row = userAddressMapper.updateByPrimaryKeySelective(userAddress);
        if (row>0){
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult("用户收货地址修改错误！！！！");
    }

    @Override
    public Map<String, Object> deleteUserAddress(Integer id) {
        int row = userAddressMapper.deleteByPrimaryKey(id);
        if (row>0){
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult("用户收货地址删除错误！！！！");
    }

    @Transactional
    @Override
    public Map<String, Object> setdefaultUserAddress(Integer userId, Integer id) {
        UserAddress userAddress=userAddressMapper.getDefaultUserAddress(userId);
        if (userAddress!=null){
            userAddressMapper.unDefaultUserAddress(userAddress.getId());
        }
        int row = userAddressMapper.defaultUserAddress(id);
        if (row>0){
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult("默认收货地址修改错误！！！！");
    }

    @Override
    public Map<String, Object> defaultUserAddress(Integer userId) {
        UserAddress userAddress=userAddressMapper.getDefaultUserAddress(userId);
        if (userAddress!=null){
            return GenResult.SUCCESS.genResult(userAddress);
        }else {
            List<UserAddress> list=userAddressMapper.userAddressList(userId);
            if (list.size()>0) return GenResult.SUCCESS.genResult(list.get(0));
            else
                return GenResult.FAILED.genResult("您未添加收货地址，请添加收货地址！！！！");
        }
    }
}
