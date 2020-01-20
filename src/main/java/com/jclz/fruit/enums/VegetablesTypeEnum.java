package com.jclz.fruit.enums;

import com.jclz.fruit.entity.FruitTypeVo;

import java.util.ArrayList;
import java.util.List;

public enum VegetablesTypeEnum {
    baicai(11,"白菜"),
    jiucai(12,"韭菜"),
    tudou(13,"土豆"),
    luobo(14,"萝卜"),
    shanyao(15,"山药"),
    dogngua(16,"冬瓜"),
    haugnga(17,"黄瓜"),
    lajiao(18,"辣椒"),
    xihoignshi(19,"西红柿"),
    jinzhenggu(20,"金针菇"),
    ;
    private int fruit_type_id;
    private String fruit_name;

    VegetablesTypeEnum(int fruit_type_id, String fruit_name) {

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
        for (VegetablesTypeEnum fruit : VegetablesTypeEnum.values()) {
            if (fruit.getFruit_type_id() == id) {
                return fruit.getFruit_name();
            }
        }
        return "";
    }

    public static Integer getFruitTypeId(String fruitName) {
        for (VegetablesTypeEnum fruit : VegetablesTypeEnum.values()) {
            if (fruit.getFruit_name().equals(fruitName)) {
                return fruit.getFruit_type_id();
            }
        }
        return null;
    }
    public static List<FruitTypeVo> getFruitTypes() {
        List<FruitTypeVo> list=new ArrayList<>();
        for (VegetablesTypeEnum fruit : VegetablesTypeEnum.values()) {
            list.add(new FruitTypeVo(fruit.fruit_type_id,fruit.fruit_name));
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(VegetablesTypeEnum.getFruitTypes());
    }
}
