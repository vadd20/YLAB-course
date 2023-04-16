package io.ylab.intensive.lesson05_Spring.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    private DataSource dataSource;

    @Autowired
    void SQLQueryBuilder (DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public String queryForTable(String tableName)
            throws SQLException {
        String existQuery = "SELECT EXISTS (\n" +
                "   SELECT *\n" +
                "   FROM information_schema.tables \n" +
                "   WHERE table_name = ?\n" +
                ")";
        String selectQuery = "SELECT column_name \n" +
                "FROM information_schema.columns \n" +
                "WHERE table_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatementExist = connection.prepareStatement(existQuery);
             PreparedStatement preparedStatementSelect = connection.prepareStatement(selectQuery)) {

            preparedStatementExist.setString(1, tableName);
            ResultSet resultSetExist = preparedStatementExist.executeQuery();
            resultSetExist.next();

            if (!resultSetExist.getBoolean(1)) {
                return null;
            }
            preparedStatementSelect.setString(1, tableName);
            ResultSet resultSetSelect = preparedStatementSelect.executeQuery();
            String result = "SELECT ";
            while (resultSetSelect.next()) {
                result += resultSetSelect.getString(1) + ", ";
            }
            result = result.substring(0, result.length() - 2);
            result += " FROM " + tableName;
            return result;
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        String selectQuery = "" +
                "SELECT table_name\n" +
                "FROM information_schema.tables\n" +
                "WHERE table_catalog = 'postgres'\n" +
                "  AND table_schema = 'public'\n" +
                "  AND table_type = 'BASE TABLE';";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);

            List<String> listOfTableNames = new ArrayList<>();
            while (resultSet.next()) {
                listOfTableNames.add(resultSet.getString(1));
            }
            return listOfTableNames;
        }
    }
}
