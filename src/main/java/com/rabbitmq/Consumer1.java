package com.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Consumer(消费者)1
 * 消费生产者生产的消息
 *
 * @author panpan gao
 * @date 2020/12/4
 **/
public class Consumer1 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getRabbitConnection();
        // 获取连接通道
        Channel channel = connection.createChannel();
        // 通道绑定对应的消息队列
        // 参数1: 队列名称 if 队列不存在自动创建
        // 参数2: 用来顶底队列是否要持久化 false-不持久化, true-持久化
        // 参数3: exclusive 是否独占队列 true-独占对垒, false-不独占
        // 参数4: authDelete 是否在消费完队列后自动删除队列, true-自动删除, false-不自动删除
        // 参数5: 额外的附加参数
        channel.queueDeclare("hello", true, false, false, null);
        channel.basicQos(1); // 每一次只能消费一个消息
        channel.basicConsume("hello", false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1 = " + new String (body));
                // 手动确认
                // 参数1: 手动确认表示
                // 参数2: false 每次确认一个
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
