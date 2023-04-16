package io.ylab.intensive.lesson05_Spring.messagefilter;

import java.io.IOException;
import java.sql.SQLException;

public interface WordFinderInDB {
    String findWord(String message) throws IOException, SQLException;
    void fillDb() throws IOException;
}
