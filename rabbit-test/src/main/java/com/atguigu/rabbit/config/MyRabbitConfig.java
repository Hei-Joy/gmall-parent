package com.atguigu.rabbit.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRabbitConfig {

    /**
     * 如果RabbitMq没有这个队列、交换机、绑定关系会自动创建。
     * @return
     */
    @Bean
    public Queue helloQueue(){
        return new Queue("order-queue",true,false,false,null);
    }

    @Bean
    public Exchange orderExchange(){
        return new DirectExchange("order-exchange",true,false,null);
    }
    @Bean
    public Binding orderbinding(){
        return new Binding("order-queue", Binding.DestinationType.QUEUE,
                "order-exchange","creatOrder",null);
    }
    /**
     * 其他都一样。。。
     */

}
