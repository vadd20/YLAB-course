package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    persistentMap.init("name");
    persistentMap.put("key", "value");
    System.out.println(persistentMap.containsKey("key"));
    System.out.println(persistentMap.get("key"));
    persistentMap.clear();
    System.out.println(persistentMap.get("key"));
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" + "drop table if exists persistent_map; "
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
