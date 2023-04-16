package io.ylab.intensive.lesson04_PostgreSQL_and_RabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtil {

  /*
   * Настройки подключения НЕ МЕНЯЕМ!
   * Надо настроить RabbitMQ таким образом, чтобы он работал со следующими
   * настройками
   */
  public static ConnectionFactory buildConnectionFactory() throws IOException, TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(15672);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("/");
    return connectionFactory;
  }
}
