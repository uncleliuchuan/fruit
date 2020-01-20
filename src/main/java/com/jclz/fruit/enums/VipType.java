package com.jclz.fruit.enums;

/**
 * VIP充值套餐
 */
public enum VipType {
    month(1, 10),//月

    season(2, 30),//季

    year(3, 120),//年
    ;

    public int vip_Id;//枚举ID
    public int vip_fee;//费用（元RMB）

    VipType(int vip_Id, int vip_fee) {
        this.vip_Id = vip_Id;
        this.vip_fee = vip_fee;
    }

    public int getVip_Id() {
        return vip_Id;
    }

    public void setVip_Id(int vip_Id) {
        this.vip_Id = vip_Id;
    }

    public int getVip_fee() {
        return vip_fee;
    }

    public void setVip_fee(int vip_fee) {
        this.vip_fee = vip_fee;
    }

    public static Integer getFee(int id) {

        for (VipType vip : VipType.values()) {
            if (id == vip.getVip_Id()) {
                return vip.getVip_fee();
            }
        }

        return null;

    }

    public static Integer getId(Integer type) {
        for (VipType vip : VipType.values()) {
            if (type == vip.getVip_fee()) {
                return vip.getVip_Id();
            }
        }
        return null;

    }
}
