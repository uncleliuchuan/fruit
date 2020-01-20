package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class AssistanceLog {
    private Integer id;

    private Integer assistanceId;//助力id

    private Integer sponsorId;//发起人ID

    private Integer authId;//助力人id

    private String createTime;//创建时间
}