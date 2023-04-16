package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.rabbitmq.client.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.DbUtil;
import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.Person;

public class DbApp {
    private static final DataSource dataSource;

    static {
        try {
            dataSource = initDb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException, SQLException {

        String queueName = "que";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            String command = message.substring(0, 4);
            if (command.equals("SAVE")) {
                try {
                    save(message.substring(4), dataSource);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (command.equals("DELT")) {
                try {
                    delete(message.substring(4), dataSource);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private static void save(String message, DataSource dataSource) throws SQLException {
        String insertQuery = "INSERT INTO person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?)";
        String[] data = message.split(";");
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setLong(1, Long.parseLong(data[0]));
            preparedStatement.setString(2, data[1]);
            preparedStatement.setString(3, data[2]);
            preparedStatement.setString(4, data[3]);
            preparedStatement.executeUpdate();
            System.out.println("added");
        }
    }

    private static void delete(String data, DataSource dataSource) throws SQLException {
        if (!isPersonExist(data)) {
            System.out.println("Attempt to delete. Person is not found.");
            return;
        }
        String deleteQuery = "DELETE FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setLong(1, Long.parseLong(data));
            preparedStatement.executeUpdate();
            System.out.println("deleted");
        }
    }

    private static boolean isPersonExist(String id) throws SQLException {
        String selectQuery = "SELECT person_id FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, Long.parseLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static Person findPerson(Long personId) {
        String selectQuery = "SELECT (person_id, first_name, last_name, middle_name) FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!isPersonExist(String.valueOf(personId))) {
                return null;
            }
            Person person = new Person();
            resultSet.next();
            String[] data = resultSet.getString(1).split(",");
            person.setId(Long.parseLong(data[0].substring(1)));
            person.setName(data[1]);
            person.setLastName(data[2]);
            person.setMiddleName(data[3].substring(0, data[3].length() - 1));
            return person;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Person> findAll() throws SQLException {
        List<Person> list = new ArrayList<>();
        String selectQuery = "SELECT (person_id, first_name, last_name, middle_name) FROM person";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                String[] data = resultSet.getString(1).split(",");
                person.setId(Long.parseLong(data[0].substring(1)));
                person.setName(data[1]);
                person.setLastName(data[2]);
                person.setMiddleName(data[3].substring(0, data[3].length() - 1));
                list.add(person);
            }
            return list;
        }
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
