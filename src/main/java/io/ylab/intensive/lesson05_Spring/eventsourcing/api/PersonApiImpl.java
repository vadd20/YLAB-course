package io.ylab.intensive.lesson05_Spring.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05_Spring.eventsourcing.Person;
import io.ylab.intensive.lesson05_Spring.eventsourcing.db.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Тут пишем реализацию
 */
@Component
public class PersonApiImpl implements PersonApi {
    private ConnectionFactory connectionFactory;

    @Autowired
    public PersonApiImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) throws IOException, TimeoutException {
        String exchangeName = "exc";
        String queueName = "que";
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
    public Person findPerson(Long personId, DAO dao) throws SQLException {
        return dao.daoFindPerson(personId);
    }

    @Override
    public List<Person> findAll(DAO dao) throws SQLException {
        return dao.daoFindAll();
    }
}




