package io.ylab.intensive.lesson05_Spring.eventsourcing.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.ylab.intensive.lesson05_Spring.eventsourcing.Person;
import io.ylab.intensive.lesson05_Spring.eventsourcing.db.DAO;

public interface PersonApi {
  void deletePerson(Long personId) throws IOException, TimeoutException;

  void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException, TimeoutException;

  Person findPerson(Long personId, DAO dao) throws SQLException;
  List<Person> findAll(DAO dao) throws SQLException;
}
