
package com.acme.arts.queue;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * A simple JMS queue consumer
 */
public class QueueConsumer {

   public static void main(final String[] args) throws Exception {

      ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");

      try (Connection connection = cf.createConnection();) {

         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
         Queue queue = (Queue) session.createQueue("my_ptest");

         MessageConsumer messageConsumer = session.createConsumer(queue);
         connection.start();

         while (true) {
            // Step 11. Receive the message
            Message messageReceived = messageConsumer.receive();

            System.out.println("Received message: " + messageReceived.getJMSMessageID());
         }
      }

   }
}
