package com.rabbitmq.demo1;

import com.rabbitmq.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Producer(生产者)
 *
 * @author panpan gao
 * @date 2020/12/4
 **/
public class Producer {
    /**
     * 生产消息
     */
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 获取连接对象
        Connection connection = RabbitMQUtil.getRabbitConnection();
        // 获取连接通道
        Channel channel = null;
        if (connection != null) {
            channel = connection.createChannel();
        }
        // 通道绑定对应的消息队列
        // 参数1: 队列名称 if 队列不存在自动创建
        // 参数2: 用来顶底队列是否要持久化 false-不持久化, true-持久化
        // 参数3: exclusive 是否独占队列 true-独占对垒, false-不独占
        // 参数4: authDelete 是否在消费完队列后自动删除队列, true-自动删除, false-不自动删除
        // 参数5: 额外的附加参数
        channel.queueDeclare("hello", true, false, false, null);
        // 发布消息
        // 参数1: 交换机名称
        // 参数2: 队列名称
        // 参数3: 传递消息的额外设置
        // 参数4: 消息的具体内容
        for (int i = 1; i <= 20; i++) {
            channel.basicPublish("", "hello", null, (i + "hello rebbitmq").getBytes());
        }

        RabbitMQUtil.closeRabbitConnectionAndChanel(channel, connection);
    }
}
