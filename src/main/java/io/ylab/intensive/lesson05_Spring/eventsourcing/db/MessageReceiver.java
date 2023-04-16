package io.ylab.intensive.lesson05_Spring.eventsourcing.db;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
public interface MessageReceiver {
    void receiveMessage(DAO dao) throws IOException, TimeoutException;
}
