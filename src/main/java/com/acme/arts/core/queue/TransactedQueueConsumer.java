
package com.acme.arts.core.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import java.util.concurrent.TimeUnit;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * A simple JMS queue consumer
 */
public class TransactedQueueConsumer {

   private static final int BATCH_SIZE = 1;

   public static void main(final String[] args) throws Exception {

      ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");

      try (Connection connection = cf.createConnection();) {

         Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
         Queue queue = (Queue) session.createQueue("my.transacted.queue");

         MessageConsumer messageConsumer = session.createConsumer(queue);
         connection.start();

         for(int i =0; i < BATCH_SIZE; i++) {
            Message messageReceived = messageConsumer.receive(TimeUnit.SECONDS.toSeconds(2000));

            if(messageReceived == null){
               break;
            }

            System.out.println("Received message: " + messageReceived.getJMSMessageID());
         }

         System.out.println("calling commit");

         session.commit();

      }

   }
}
