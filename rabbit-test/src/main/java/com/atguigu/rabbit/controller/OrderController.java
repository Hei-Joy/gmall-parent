package com.atguigu.rabbit.controller;

import com.atguigu.rabbit.bean.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/create/order")
    public Order createOrder(Long skuId,Integer num,Integer memeberId){
        Order order = new Order(UUID.randomUUID().toString().replace("-", ""), skuId, num,
                memeberId);

       // rabbitTemplate.convertAndSend("order-exchange","creatOrder",order);


        rabbitTemplate.convertAndSend("user.order.delay.exchange","order_delay",order);

        return order;
    }
}
