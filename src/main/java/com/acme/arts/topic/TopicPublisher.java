package com.acme.arts.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

/**
 * Simple JMS/CORE topic pulisher
 */
public class TopicPublisher {

   public static void main(final String[] args) throws Exception {
     

      try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");
          Connection connection = cf.createConnection();)
      {

         Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

         
         Topic topic = session.createTopic("mytopic_1");


         MessageProducer producer = session.createProducer(topic);


         TextMessage message = session.createTextMessage("This is a text message");
        
         for(int i = 0; i < 10 ; i++ ){
            producer.send(message);
         }


         System.out.println("Sent message: " + message.getText());
         System.out.println("Press enter key");
         System.in.read();

      }
      
   }
}
