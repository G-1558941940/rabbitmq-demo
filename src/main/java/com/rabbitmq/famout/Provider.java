package com.rabbitmq.famout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.utils.RabbitMQUtil;

import java.io.IOException;

/**
 * Provider
 *
 * @author panpan gao
 * @date 2020/12/7
 **/
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection rabbitConnection = RabbitMQUtil.getRabbitConnection();
        Channel channel = rabbitConnection.createChannel();
        //将通道声明指定的交换机
        // 参数1: 交换机名称
        // 参数2: 交换机类型 fanout 广播类型
        channel.exchangeDeclare("logs", "fanout");
        //发送消息
        channel.basicPublish("logs", "", null, "fanout type message".getBytes());

        RabbitMQUtil.closeRabbitConnectionAndChanel(channel, rabbitConnection);
    }
}
