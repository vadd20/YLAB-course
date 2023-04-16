package io.ylab.intensive.lesson05_Spring.messagefilter;

import io.ylab.intensive.lesson05_Spring.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Component
public class WordFinderInDBImpl implements WordFinderInDB {
    DataSource dataSource;

    @Autowired
    public WordFinderInDBImpl(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        initDb();
    }

    @Override
    public String findWord(String word) throws SQLException {
        String selectQuery = "SELECT word FROM swear WHERE word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getString(1);
        }
    }

    @Override
    public void fillDb() throws IOException {
        String initialFileName = "./src/main/java/io/ylab/intensive/lesson05_Spring" +
                "/messagefilter/mats";
        String normalizedFileName = "./src/main/java/io/ylab/intensive/lesson05_Spring" +
                "/messagefilter/mats_normalized";
        normalizeFile(initialFileName, normalizedFileName);

        String insertQuery = "INSERT INTO swear (word) values (?)";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(normalizedFileName));
             Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                preparedStatement.setString(1, line);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void normalizeFile(String initial, String normalized) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(initial));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(normalized))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line.split(",");
                for (String word : words) {
                    bufferedWriter.write(word.trim());
                    bufferedWriter.newLine();
                }
            }
        }
    }

    private void initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists swear;"
                + "create table if not exists swear (\n"
                + "word varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        this.dataSource = dataSource;
    }
}
