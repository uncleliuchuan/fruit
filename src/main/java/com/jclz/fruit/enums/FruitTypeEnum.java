package com.jclz.fruit.enums;

import com.jclz.fruit.entity.FruitTypeVo;

import java.util.ArrayList;
import java.util.List;

public enum  FruitTypeEnum {
    chengzi(1,"橙子"),
    pingguo(2,"苹果"),
    xiangjiao(3,"香蕉"),
    huolongguo(4,"火龙果"),
    xigua(5,"西瓜"),
    caomei(6,"草莓"),
    liulian(7,"榴莲"),
    dongzao(8,"冬枣"),
    putao(9,"葡萄"),
    ganzhe(10,"甘蔗"),
    ;
    private int fruit_type_id;
    private String fruit_name;

    FruitTypeEnum(int fruit_type_id, String fruit_name) {

        this.fruit_type_id = fruit_type_id;
        this.fruit_name = fruit_name;
    }

    public int getFruit_type_id() {
        return fruit_type_id;
    }

    public void setFruit_type_id(int fruit_type_id) {
        this.fruit_type_id = fruit_type_id;
    }

    public String getFruit_name() {
        return fruit_name;
    }

    public void setFruit_name(String fruit_name) {
        this.fruit_name = fruit_name;
    }
    public static String getFruitName(int id) {
        for (FruitTypeEnum fruit : FruitTypeEnum.values()) {
            if (fruit.getFruit_type_id() == id) {
                return fruit.getFruit_name();
            }
        }
        return "";
    }

    public static Integer getFruitTypeId(String fruitName) {
        for (FruitTypeEnum fruit : FruitTypeEnum.values()) {
            if (fruit.getFruit_name().equals(fruitName)) {
                return fruit.getFruit_type_id();
            }
        }
        return null;
    }
    public static List<FruitTypeVo> getFruitTypes() {
        List<FruitTypeVo> list=new ArrayList<>();
        for (FruitTypeEnum fruit : FruitTypeEnum.values()) {
            list.add(new FruitTypeVo(fruit.fruit_type_id,fruit.fruit_name));
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(FruitTypeEnum.getFruitTypes());
    }
}
