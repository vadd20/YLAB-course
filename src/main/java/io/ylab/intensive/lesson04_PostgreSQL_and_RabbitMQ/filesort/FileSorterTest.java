package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ.DbUtil;

public class FileSorterTest {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = initDb();
        File data = new Generator().generate("./src/main/java/io/ylab/intensive/lesson04_PostgreSQL_and_RabbitMQ/filesort/data.txt", 1000);
        FileSorter fileSorter = new FileSortImpl(dataSource);
        File res = fileSorter.sort(data);
    }

    public static DataSource initDb() throws SQLException {
        String createSortTable = ""
                + "drop table if exists numbers;"
                + "CREATE TABLE if not exists numbers (\n"
                + "\tval bigint\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createSortTable, dataSource);
        return dataSource;
    }
}
