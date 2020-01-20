package com.jclz.fruit.constant;

/**
 * User: NMY
 * Date: 17-5-17
 */
public interface X {

    interface TIME {
        //对应秒
        int DAY_SECOND = 24 * 60 * 60;
        int HOUR_SECOND = 60 * 60;
        int MINUTE = 60;
        //对应毫秒
        int DAY_MILLISECOND = 24 * 60 * 60 * 1000;
        int HOUR_MILLISECOND = 60 * 60 * 1000;
        int MINUTE_MILLISECOND = 60 * 1000;
        int SECOND_MILLISECOND = 1000;
    }

    /**
     * 编码
     */
    interface ENCODING {
        String U = "UTF-8";
        String I = "ISO-8859-1";
    }

    /**
     * 腾讯
     */
    interface TX {
        int TOKEN_EXPIRE_TIME = 7000000;//token缓存时间：毫秒

        String APP_ID = "TIDA7nsr";//"IDAIKohq"

        String USER_ID = "ff4681d660684729a2c9abb1da7629";

        String SECRET = "AEzE0DNnKN2NoNuGXSsKVJPyR0cRp8Jei9c8NY6LQJLatVP4WwUFVYMCTKhahJFq";//"VAxnWhz1mHoenv9vgNG333zXT95ARaIrleu5GCfsPvKuOweREw0BtH593hK6Zn7F"

        String TOKEN_URL = "https://idasc.webank.com/api/oauth2/access_token?app_id=" + APP_ID + "&secret=" + SECRET + "&grant_type=client_credential&version=1.0.0";//获取token url地址

        String TICKET_URL = "https://idasc.webank.com/api/oauth2/api_ticket?app_id=" + APP_ID + "&access_token=%s&type=NONCE&version=1.0.0&user_id=" + USER_ID;
    }


    /**
     * 领签
     */
    interface LINK_SIGN {
        String API_KEY = "e185c02085b850e2cb88b65e";
        String BASE_URL = "https://uat.linksign.cn/ess";
        String CONTENT_TYPE = "application/json;charset=utf-8";
        int ESTIMATED_SIZE = 50000; //预估当前签章域的大小（印章图片40KB左右）
        String TEMP_FILE_PREFIX = "temp__";
    }

    /**
     * 短信配置
     */
    interface SMS {
        String URL = "xx";
        String NAME = "xx";
        String PWD = "xx";
        String SIGN = "xx";
        String TEMPLATE_SMS_CODE = "xx";
    }
}
