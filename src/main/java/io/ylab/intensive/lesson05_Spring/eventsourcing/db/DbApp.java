package io.ylab.intensive.lesson05_Spring.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DAO dao = applicationContext.getBean(DAO.class);

        MessageReceiver messageReceiver = applicationContext.getBean(MessageReceiver.class);

        messageReceiver.receiveMessage(dao);
    }
}
