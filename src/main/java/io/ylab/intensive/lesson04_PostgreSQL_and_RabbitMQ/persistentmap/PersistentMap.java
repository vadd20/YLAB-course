package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.persistentmap;

import java.sql.SQLException;
import java.util.List;

public interface PersistentMap {

  void init(String name) throws SQLException;

  boolean containsKey(String key) throws SQLException;

  List<String> getKeys() throws SQLException;

  String get(String key) throws SQLException;

  void remove(String key) throws SQLException;

  void put(String key, String value) throws SQLException;

  void clear() throws SQLException;
}
