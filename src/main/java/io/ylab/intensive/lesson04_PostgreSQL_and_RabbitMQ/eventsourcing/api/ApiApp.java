package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.RabbitMQUtil;
import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.eventsourcing.Person;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class ApiApp {
    public static void main(String[] args) throws IOException, TimeoutException, EOFException, SQLException {
        ConnectionFactory connectionFactory = initMQ();
        PersonApi personApi = new PersonApiImpl();
        personApi.savePerson(1L, "Vadim", "Podogov", "Alexandrovich");
        personApi.savePerson(2L, "Maksim", "Ivanov", "Dmitrievich");
        personApi.savePerson(3L, "Alexander", "Gozhan", "Igorevich");
        personApi.savePerson(4L, "Alexey", "Petrov", "Ivanovich");
        personApi.deletePerson(2L);
        List<Person> list = personApi.findAll();
        for (Person person : list) {
            System.out.println(person.getId() + " " + person.getName() + " " + person.getLastName() + " " + person.getMiddleName());
        }
    }

    private static ConnectionFactory initMQ() throws IOException, TimeoutException {
        return RabbitMQUtil.buildConnectionFactory();
    }
}