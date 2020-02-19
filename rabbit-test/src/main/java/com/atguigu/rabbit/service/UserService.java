package com.atguigu.rabbit.service;



import com.atguigu.rabbit.bean.Order;
import com.atguigu.rabbit.bean.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 1、消息确认机制；
 *   1）、如果这个消息收到了，在处理期间，出现了运行时异常，默认认为消息没有被正确处理
 *      消息状态unack;队列中感知到有一个unack的消息。
 *      unack的消息队列会再次尝试把这个消息发给其他消费者
 *
 *   2）、我们不要让他认为到底是ack还是unack；手动确认机制；
 *      否则：场景；
 *          我们收到了消息，并且库存扣了，但是出现了未知的异常，导致消息又重新入队了，
 *          这个消息被不断的重复的发给我们；
 *        解决：
 *          1）、手动ack
 *          2）、接口幂等性。在本地维护一个日志表，记录哪些会员哪些商品哪个订单已经减过库存，再来同样的消息就不减了。
 *
 *
 * 2、手动ack；
 *  1)、开启手动ack；spring.rabbitmq.listener.simple.acknowledge-mode=manual
 *  2）、
 *  public void listener(){
 *      try{
 *          //处理消息，回复成功。
 *          channal.basicAck();
 *      }catch(Exception e){
 *          //拒绝消息。
 *          channal.basicNack/Reject( requeue:true);
 *      }
 *  }
 *
 */
@Service
public class UserService {


    /**
     * 方法上可以写以下参数
     * 1、org.springframework.amqp.core.Message：既能获取到消息的内容字节，还能获取到消息的其他属性
     * 2、 User user：如果明确我们这个队列以后都是这个类型对象，直接写这个类型参数
     * 3、com.rabbitmq.client.Channel：通道；
     *
     * 以上无任何顺序，也无数量限制
     */
//    @RabbitListener(queues = {"world"})
//    public void receiveUserMessage(Message message, User user, Channel channel) throws IOException {
//        System.out.println("收到的消息是："+message.getClass());
//        //byte[] body = message.getBody();
//        //MessageProperties messageProperties = message.getMessageProperties();
//        System.out.println("收到的消息是："+user);
//
//        //拒绝：可以把消息拒绝调，让 rabbitmq 再发给别人。
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
//
//    }

    @RabbitListener(queues = {"order-queue"})
    public void receivieOrder(Order order,Message message,Channel channel) throws IOException {
        System.out.println("监听到新的订单生成...."+order);

        Long skuId = order.getSkuId();
        Integer num = order.getNum();
        System.out.println("库存系统正在扣除【"+skuId+"】商品的数量，此次扣除【"+num+"】件");

        if(num%2==0){
            System.out.println("库存系统扣除【"+skuId+"】库存失败");
            //回复消息处理失败，拒绝消息。并且重新入队
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            throw new RuntimeException("库存扣除失败");
        }
        System.out.println("扣除成功....");
        //回复成功 只回复本条消息
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }


    @RabbitListener(queues = {"user.order.queue"})
    public  void closeOrder(Order order,Channel channel,Message message) throws IOException {

        System.out.println("收到过期订单："+order+"正在关闭订单");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
