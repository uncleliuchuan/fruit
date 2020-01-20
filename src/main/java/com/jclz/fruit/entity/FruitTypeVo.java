package com.jclz.fruit.entity;

import lombok.Data;

@Data
public class FruitTypeVo {
    public int fruit_type_id;
    public String fruit_name;

   public FruitTypeVo(int fruit_type_id, String fruit_name) {

        this.fruit_type_id = fruit_type_id;
        this.fruit_name = fruit_name;
    }
}
