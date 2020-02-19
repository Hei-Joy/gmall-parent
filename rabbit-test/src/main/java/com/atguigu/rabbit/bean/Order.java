package com.atguigu.rabbit.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Order implements Serializable {

    private String orderSn;//订单号
    private Long skuId;//购买的商品id
    private Integer num;//购买的个人
    private Integer memberId;//购买者的id
}
