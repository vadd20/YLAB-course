package io.ylab.intensive.lesson05_Spring.eventsourcing.db;

import io.ylab.intensive.lesson05_Spring.DbUtil;
import io.ylab.intensive.lesson05_Spring.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DAOImpl implements DAO {
    private DataSource dataSource;

    @Autowired
    public DAOImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        //initDb();
    }

    @Override
    public void save(String message) throws SQLException {
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

    @Override
    public void delete(String data) throws SQLException {
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

    private boolean isPersonExist(String id) throws SQLException {
        String selectQuery = "SELECT person_id FROM person WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, Long.parseLong(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }
    @Override
    public Person daoFindPerson(Long personId) {
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
            rsToPerson(resultSet, person);
            return person;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> daoFindAll() throws SQLException {
        List<Person> list = new ArrayList<>();
        String selectQuery = "SELECT (person_id, first_name, last_name, middle_name) FROM person";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Person person = new Person();
                rsToPerson(resultSet, person);
                list.add(person);
            }
            return list;
        }
    }

    private static void rsToPerson(ResultSet resultSet, Person person) throws SQLException {
        String[] data = resultSet.getString(1).split(",");
        person.setId(Long.parseLong(data[0].substring(1)));
        person.setName(data[1]);
        person.setLastName(data[2]);
        person.setMiddleName(data[3].substring(0, data[3].length() - 1));
    }

    private void initDb() throws SQLException {
        String ddl = ""
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        this.dataSource = dataSource;
    }
}
