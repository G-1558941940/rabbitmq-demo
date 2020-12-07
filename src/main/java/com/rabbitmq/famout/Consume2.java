package com.rabbitmq.famout;

import com.rabbitmq.client.*;
import com.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;

/**
 * Consume1
 *
 * @author panpan gao
 * @date 2020/12/7
 **/
public class Consume2 {
    public static void main(String[] args) throws IOException {
        Connection rabbitConnection = RabbitMQUtil.getRabbitConnection();
        Channel channel = rabbitConnection.createChannel();
        // 通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        // 创建临时队列
        String queueName = channel.queueDeclare().getQueue();
        // 绑定交换机和队列
        channel.queueBind(queueName, "logs", "");

        // 消费消息
        channel.basicConsume(queueName,  true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费2 = " + new String(body));
            }
        });
    }
}
