package com.acme.arts.core.queue.api;

import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientMessage;
import org.apache.activemq.artemis.api.core.client.ClientProducer;
import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

public class QueueCoreProducer {

   public static void main(final String[] args) throws Exception {

      try (ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616"); ClientSessionFactory factory = locator.createSessionFactory(); ClientSession session = factory.createSession()) {

         ClientProducer producer = session.createProducer("example_two");
         ClientMessage message = session.createMessage(true);
         message.getBodyBuffer().writeString("Hello");

         for (int i = 0; i < 20; i++) {
            producer.send(message);
         }

      }
   }
}
