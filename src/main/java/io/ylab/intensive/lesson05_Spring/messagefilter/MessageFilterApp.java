package io.ylab.intensive.lesson05_Spring.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class MessageFilterApp {
  public static void main(String[] args) throws IOException, TimeoutException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    WordFinderInDB wordFinderInDB = applicationContext.getBean(WordFinderInDB.class);
    wordFinderInDB.fillDb();

    MessageFilter messageFilter = applicationContext.getBean(MessageFilter.class);
    messageFilter.doFilter();
  }
}
