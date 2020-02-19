package com.atguigu.rabbit;

import com.atguigu.rabbit.bean.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTestApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    public void contextLoads() {

        User user = new User("lisi", "zhangsan@qq.com");

//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());


        //给指定的交换机按照指定路由键发送指定消息
        rabbitTemplate.convertAndSend("direct_exchanage","world",user);

        System.out.println("消息发送完成....");

    }

    @Test
    public void createQueue(){
        //Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
        Queue queue = new Queue("my-queue-01",true,false,false);
        amqpAdmin.declareQueue(queue);
        System.out.println("队列创建完成...");
    }


    @Test
    public void createExchange(){
        //DirectExchange(String name, boolean durable, boolean autoDelete)
        DirectExchange directExchange = new DirectExchange("my-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        System.out.println("DirectExchange创建完成...");
    }

    @Test
    public void createBinding(){
        //Binding(String destination, 目的地
        //        DestinationType destinationType, 目的地类型
        //       String exchange, 交换机
        //       String routingKey, 路由键
        //	     Map<String, Object> arguments 参数)
        Binding binding = new Binding("my-queue-01",
                Binding.DestinationType.QUEUE,
                "my-exchange",
                "hello",null);
        amqpAdmin.declareBinding(binding);
        System.out.println("Binding创建完成...");
    }



}


