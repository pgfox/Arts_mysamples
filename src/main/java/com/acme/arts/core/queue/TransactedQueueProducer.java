package com.acme.arts.core.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * A simple JMS queue publisher
 */
public class TransactedQueueProducer {

   public static void main(final String[] args) throws Exception {

      ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");

      try (Connection connection = cf.createConnection();) {

        
         Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
         Queue queue = session.createQueue("my.transacted.queue");

         MessageProducer producer = session.createProducer(queue);

         Message message = getMessage(session);

         for (int i = 0; i < 1; i++) {
            producer.send(message);
         }

         session.commit();
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
