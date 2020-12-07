package com.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Utils
 *
 * @author panpan gao
 * @date 2020/12/4
 **/
public class RabbitMQUtil {
    private static ConnectionFactory connectionFactory;

    static {
        connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq主机
        connectionFactory.setHost("39.105.176.118");
        // 设置端口
        connectionFactory.setPort(5672);
        // 设置连接那个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置访问虚拟主机的用户名密码
        connectionFactory.setUsername("gaopanpan");
        connectionFactory.setPassword("gaopanpan");
    }

    public static Connection getRabbitConnection(){
        try {
            return connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeRabbitConnectionAndChanel(Channel channel, Connection connection){
        if (channel != null){
            try {
                channel.close();
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
