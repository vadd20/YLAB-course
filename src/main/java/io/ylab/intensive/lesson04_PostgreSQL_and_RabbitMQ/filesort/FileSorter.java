package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public interface FileSorter {
  File sort(File data) throws IOException, SQLException;
}
