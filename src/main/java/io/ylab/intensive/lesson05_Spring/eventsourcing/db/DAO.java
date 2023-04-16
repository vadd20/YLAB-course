package io.ylab.intensive.lesson05_Spring.eventsourcing.db;

import io.ylab.intensive.lesson05_Spring.eventsourcing.Person;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void save(String message) throws SQLException;
    void delete(String data) throws SQLException;
    Person daoFindPerson(Long personId);
    List<Person> daoFindAll() throws SQLException;
}
