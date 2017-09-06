package com.acme.arts.queue;

import javax.jms.*;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * A simple JMS queue publisher
 */
public class QueueProducer {

   public static void main(final String[] args) throws Exception {

      ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");

      try (Connection connection = cf.createConnection();) {

         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         Queue queue = session.createQueue("my_ptest");

         MessageProducer producer = session.createProducer(queue);

         Message message = getMessage(session);

         for (int i = 0; i < 50; i++) {
            producer.send(message);
         }

         System.out.println("Sent message: " + message);
         System.out.println("Press enter key");
         System.in.read();

      }
   }

   private static Message getMessage(Session session) throws JMSException {

      StringBuilder stringBuilder = new StringBuilder();

      for (int i = 0; i < 1000; i++) {
         stringBuilder.append("1234567890");
      }

      return session.createTextMessage(stringBuilder.toString());
   }
}
