package io.ylab.intensive.lesson05_Spring.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

@Component
public class MessageFilterImpl implements MessageFilter {
    private final ConnectionFactory connectionFactory;
    private final WordFinderInDB wordFinderInDB;

    @Autowired
    public MessageFilterImpl(ConnectionFactory connectionFactory, WordFinderInDB wordFinderInDB) {
        this.connectionFactory = connectionFactory;
        this.wordFinderInDB = wordFinderInDB;
    }

    private void recieveFromInputQueue() throws IOException, TimeoutException {
        Connection connection = this.connectionFactory.newConnection();
        Channel inputChannel = connection.createChannel();
        inputChannel.queueDeclare("INPUT_QUEUE", true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received: ");
            System.out.println("'" + message + "'");
            try {
                sendToOutputQueue(filterMessage(message));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        System.out.println(" [x] Message expected");
        inputChannel.basicConsume("INPUT_QUEUE", true, deliverCallback, consumerTag -> { });
    }

    private void sendToOutputQueue(String censoredMessage) throws IOException, SQLException {
        try (Connection connection = this.connectionFactory.newConnection();
        Channel channel = connection.createChannel()) {
            channel.queueDeclare("OUTPUT_QUEUE", true, false, false, null);
            channel.basicPublish("", "OUTPUT_QUEUE", null, censoredMessage.getBytes());
            System.out.println(" [x] Sent '");
            System.out.println("'" + censoredMessage + "'");
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private String filterMessage(String message) throws SQLException, IOException {
        ArrayList<String> words = splitWords(message);
        ArrayList<String> swears = new ArrayList<>();
        for (String word : words) {
            String result = wordFinderInDB.findWord(word);
            if (result != null) {
                swears.add(result);
            }
        }

        for (String swear : swears) {
            String censoredWord = swear.charAt(0) +
                    swear.substring(1, swear.length() - 1).replaceAll(".", "*") +
                    swear.charAt(swear.length() - 1);
            message = message.replaceAll(swear, censoredWord);
        }
        return message;
    }

    private ArrayList<String> splitWords(String sentence) {
        String [] words = sentence.replaceAll("[^a-zA-Zа-яА-ЯА-Я0-9\\s+]", "").split("\\s+");
        return new ArrayList<> (Arrays.asList(words));
    }


    @Override
    public void doFilter() throws IOException, TimeoutException {
        recieveFromInputQueue();
    }
}
