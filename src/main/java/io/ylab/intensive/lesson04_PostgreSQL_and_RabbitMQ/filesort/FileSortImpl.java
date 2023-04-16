package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.filesort;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
  private DataSource dataSource;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) throws IOException, SQLException {
    String insertQuery = "INSERT INTO numbers (val) values (?)";
    String selectQuery = "SELECT * FROM numbers ORDER BY val DESC";
    File sorted = new File("./src/main/java/io/ylab/intensive/lesson04_PostgreSQL_and_RabbitMQ/filesort/sorted.txt");
    try (BufferedReader br = new BufferedReader(new FileReader(data));
         BufferedWriter bw = new BufferedWriter(new FileWriter(sorted));
         Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatementInsert = connection.prepareStatement(insertQuery);
         PreparedStatement preparedStatementSelect = connection.prepareStatement(selectQuery)
         ) {
      readFileAndSortTable(br, preparedStatementInsert);
      ResultSet resultSet = preparedStatementSelect.executeQuery();
      writeSortedTable(bw, resultSet);
      return sorted;
    }
  }
  public static void readFileAndSortTable (BufferedReader br, PreparedStatement preparedStatement)
          throws IOException, SQLException {
    String currentNumber;
    while ((currentNumber = br.readLine()) != null) {
      preparedStatement.setLong(1, Long.parseLong(currentNumber));
      preparedStatement.addBatch();
    }
    preparedStatement.executeBatch();
  }

  public static void writeSortedTable (BufferedWriter bw, ResultSet resultSet) throws SQLException, IOException {
    while (resultSet.next()) {
      bw.write(resultSet.getString(1));
      bw.newLine();
    }
  }
}
