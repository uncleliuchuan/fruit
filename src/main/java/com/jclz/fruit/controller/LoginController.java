package com.jclz.fruit.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.service.UserService;
import com.jclz.fruit.util.SmsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Api(tags = "用户登录API")
public class LoginController {
    /*
     * 用户登录
     */

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "用户登录")
    public Map<String, Object> login(@ApiParam(required = true, value = "手机号") @RequestParam(required = true, value = "phone")String phone,
                        @ApiParam(required = true, value = "验证码") @RequestParam(required = true, value = "code")String code) {
        System.out.println("进入登录程序");
        if (null == phone || code == null) {
            return GenResult.PARAMS_ERROR.genResult();
        }
        try {
            if (null != phone && code != null) {
                return userService.login(phone, code);
            }
        } catch (Exception e) {
            log.error("用户登录异常----",e);
            return GenResult.FAILED.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    /**
     * 短信验证码
     * @return
     */
    @PostMapping("/getVerificationCode")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "获取短信验证码")
    public Map<String, Object> getVerificationCode(@ApiParam(required = true, value = "手机号") @RequestParam(required = true, value = "telephone") String telephone) {
        try {
            SmsUtils.setNewcode();
            int newcode = SmsUtils.getNewcode();
            String newCode = newcode + "";
            redisTemplate.opsForValue().set(telephone, newcode, 60 * 5, TimeUnit.SECONDS);
            SendSmsResponse sendSms = SmsUtils.sendSms(telephone, newCode);
            if (sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
                // System.out.println("短信发送成功！");
                return GenResult.SUCCESS.genResult("短信发送成功！");
            } else {
                //System.out.println("短信发送失败！");
                log.error("验证码获取失败----"+sendSms);
                return GenResult.FAILED.genResult("验证码获取失败！");
            }
        } catch (ClientException e) {
            log.error("阿里云短信异常----",e);
            return GenResult.FILE_ALIBABA_SMS.genResult("验证码获取失败！");
        }
    }


}
	
