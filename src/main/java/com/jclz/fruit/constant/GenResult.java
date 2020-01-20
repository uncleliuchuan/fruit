package com.jclz.fruit.constant;

import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public enum GenResult {
    SUCCESS(1000, "请求成功"),

    FAILED(1001, "请求失败"),

    FILE_ALIBABA_SMS(1002, "阿里云短信异常"),

    PARAMS_ERROR(1003, "请求参数错误"),

    SMS_CODE_ERROR(1004, "验证码错误！"),

    NOT_BIND_SMS(1005, "您未绑定手机号，请前往绑定手机号！！！"),

    SMS_CODE_INVALID(1006, "验证码失效，请重新获取验证码!"),

    SMS_HAD_BIND(1007, "手机号已被占用！！！"),

    TOKEN_INVALID(1008, "TOKEN失效!!!"),

    ORDER_EXPIRED(1009, "订单已失效!!!"),

    ORDER_NOT_RECEIVED(1010, "订单未入库，还未完成支付，请前往支付!!!"),

    WECHAT_PAYMENT_ERROR(1011, "微信支付错误，请重新进入充值界面后再试!!!"),

    ORDER_NOT_EXIST(1012, "订单不存在，请联系客服!!!"),

    UNKNOWN_ERROR(9999, "未知异常"),
    ;

    public int msgCode;
    public String message;

    private GenResult(int msgCode, String message) {
        this.msgCode = msgCode;
        this.message = message;
    }

    public Map<String, Object> genResult() {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        return map;
    }

    public Map<String, Object> genResult(Object data) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", msgCode);
        map.put("msg", message);
        map.put("data", data);
        return map;
    }

    public String toJson() {
        JSONObject object = new JSONObject();
        object.put("code", msgCode);
        object.put("msg", message);
        return object.toString();
    }
}
