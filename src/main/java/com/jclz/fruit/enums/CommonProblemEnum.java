package com.jclz.fruit.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.entity.FruitTypeVo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 常见问题
 * @create uncle_lc
 * @date 2020-01-16
 */
public enum CommonProblemEnum {
    one("发货时间","各商品的发货时间","常规类目商品下单后24小时内发货。每日福利商品助力成功后24小内发货。"),
    two("发货时间","商家发货超过承诺时间处理办法","常规类目商品下单后24小时内发货。每日福利商品助力成功后24小内发货。超过此时间未发货可申请退款。"),
    three("商品问题","商品问题如何退货","所有商品因为属于生鲜，发货后将不支持任何退货，希望您在下单时能够充分了解商品后下单。"),
    four("商品问题","商品少发错发如何处理","如商品与订单数量、种类不符，请及时照相，暂不食用，保留凭证，在快递签收商品12小时内及时与客服取得联系协商处理结果，并在【超级果蔬】-【个人中心】-【我的订单】-【待评价】-申请退款页面中，填写信息并上传凭证。客服会依据双方协商结果在24小时内将补偿金额打入用户指定账户。"),
    five("商品问题","收到商品有问题如何处理","如收到商品有质量问题，请及时照相，保留凭证，在快递签收商品12小时内及时与客服取得联系协商处理结果，并在【超级果蔬】-【个人中心】-【我的订单】-【待评价】申请退款页面中，填写信息并上传凭证。客服会依据双方协商结果在24小时内将补偿金额打入用户指定账户。"),
    six("快递物流","我想指定快递物流公司","为了保证商品的新鲜和运输质量，本平台所有快递物流默认平台配送或指定合作快递，暂不接受指定快递。"),
    seven("快递物流","商家发错我的收货地址","发现商家发错用户收货地址，请用户在【超级果蔬】-【个人中心】-【我的订单】中仔细核对订单收货地址。如确实是因为商家问题导致收货地址发错，您未收到货物却在平台显示订单签收、确认收货，请于12小时内及时与客服取得联系协商处理结果，并在【超级果蔬】-【个人中心】-【我的订单】-【待评价】申请退款页面中，填写信息并上传凭证。客服会依据双方协商结果在24小时内将补偿金额打入用户指定账户。"),
    eight("退款售后","订单已经发货，我想退货退款","所有商品因为属于生鲜，发货后将不支持任何退货，希望您在下单时能够充分了解商品后下单。"),
    nine("退款售后","订单还没发货，我想退款","常规类目商品下单后24小时内发货。每日福利商品助力成功后24小内发货。在此时间内未发货可申请退款。"),
    ten("退款售后","退款成功后款项到账时间","所有退款平台会在24小时内将退款打入用户指定账户。"),
    eleven("服务投诉","联系商家不回应","如联系不到客服，为了钱款安全考虑，建议先行申请退款，等待商家联系。"),
    twelve("服务投诉","如何联系商家","您可以打开【超级果蔬】-【个人中心】-【我的订单】，点击该商品，再次点击该商品，就可以通过电话方式联系到客服并进行沟通。如联系不到商家，为了钱款安全考虑，建议先行申请退款，保留凭证，等待商家联系。"),
    ;
    private String type;//问题类型
    private String title;//标题
    private String content;//内容

    CommonProblemEnum(String type, String title, String content) {

        this.type = type;
        this.title = title;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public static List<CommonProblemEnum> getByType(String type) {
        List<CommonProblemEnum> list=new ArrayList<>();
        for (CommonProblemEnum problem : CommonProblemEnum.values()) {
            if (type.equals(problem.type)) {
                list.add(problem);
            }
        }
        return list;
    }
    public static Set<String> getTypes() {
        Set<String> set=new HashSet<>();
        for (CommonProblemEnum problem : CommonProblemEnum.values()) {
            set.add(problem.type);
        }
        return set;
    }

    public static JSONArray getCommonProblem() {
        Set<String> types = getTypes();
        JSONArray array=new JSONArray();
        for (String type: types) {
            JSONObject json=new JSONObject();
            List<CommonProblemEnum> list= getByType(type);
            JSONArray titles=new JSONArray();
            for (CommonProblemEnum p:list) {
                JSONObject title=new JSONObject();
                title.put("title",p.title);
                title.put("content",p.content);
                titles.add(title);
            }
            json.put("type",type);
            json.put("titles",titles);
            array.add(json);
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(CommonProblemEnum.getCommonProblem());
    }
}
