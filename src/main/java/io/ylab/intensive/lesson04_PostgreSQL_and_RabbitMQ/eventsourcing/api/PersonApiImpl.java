package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.Person;
import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.db.DbApp;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    @Override
    public void deletePerson(Long personId) throws IOException, TimeoutException {
        String exchangeName = "exc";
        String queueName = "que";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "exc");
            String message = "DELT" + String.valueOf(personId);
            channel.basicPublish(exchangeName, "exc", null, message.getBytes());
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException, TimeoutException {
        String exchangeName = "exc";
        String queueName = "que";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "exc");
            String message = "SAVE" + personId + ';' + firstName + ';' + lastName + ';' + middleName;
                    channel.basicPublish(exchangeName, "exc", null, message.getBytes());
        }
    }


    @Override
    public Person findPerson(Long personId) throws SQLException {
        return DbApp.findPerson(personId);
    }

    @Override
    public List<Person> findAll() throws SQLException {
        return DbApp.findAll();
    }
}
