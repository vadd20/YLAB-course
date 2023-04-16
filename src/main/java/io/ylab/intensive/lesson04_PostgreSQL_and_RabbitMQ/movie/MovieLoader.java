package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.movie;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public interface MovieLoader {
  void loadData(File file) throws IOException, SQLException;
}
