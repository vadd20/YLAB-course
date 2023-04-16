package io.ylab.intensive.lesson05_Spring.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson05_Spring.RabbitMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageReceiverImpl implements MessageReceiver {
    private ConnectionFactory connectionFactory;

    @Autowired
    public MessageReceiverImpl(ConnectionFactory connectionFactory) throws Exception {
        this.connectionFactory = connectionFactory;
    }

    public void receiveMessage(DAO dao) throws IOException, TimeoutException {
        String queueName = "que";
        connectionFactory.setHost("localhost");
        Connection connection = this.connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String command = message.substring(0, 4);

            if (command.equals("SAVE")) {
                try {
                    dao.save(message.substring(4));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (command.equals("DELT")) {
                try {
                    dao.delete(message.substring(4));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private void initMQ() throws Exception {
        this.connectionFactory = RabbitMQUtil.buildConnectionFactory();
    }
}

