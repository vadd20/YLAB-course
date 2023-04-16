package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.persistentmap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) throws SQLException {
        String insertQuery = "INSERT INTO persistent_map (map_name, KEY, value) values (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setNull(2, Types.VARCHAR);
            preparedStatement.setNull(3, Types.VARCHAR);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        String selectQuery = "SELECT map_name FROM persistent_map WHERE KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    @Override
    public List<String> getKeys() throws SQLException {
        String selectQuery = "SELECT KEY FROM persistent_map WHERE value <> ?";
        List<String> keys = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setNull(1, Types.VARCHAR);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                keys.add(resultSet.getString(1));
            }
            return keys;
        }
    }

    @Override
    public String get(String key) throws SQLException {
        String selectQuery = "SELECT value FROM persistent_map WHERE KEY = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            if (containsKey(key)) {
                preparedStatement.setString(1, key);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getString(1);
            }
            return null;
        }
    }

    @Override
    public void remove(String key) throws SQLException {
        String updateQuery = "UPDATE persistent_map SET KEY = ?, value = ? WHERE KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            if (containsKey(key)) {
                preparedStatement.setNull(1, Types.VARCHAR);
                preparedStatement.setNull(2, Types.VARCHAR);
                preparedStatement.setString(3, key);
            } else System.out.println("this key doesnt exist");
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        String updateQuery = "UPDATE persistent_map SET key = ?, value = ? WHERE KEY IS NULL";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            if (containsKey(key)) {
                remove(key);
            }
            preparedStatement.setString(1, key);
            preparedStatement.setString(2, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String deleteQuery = "DELETE FROM persistent_map";
        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteQuery);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
