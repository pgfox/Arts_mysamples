package com.acme.arts.core.queue.api;

import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.apache.activemq.artemis.api.core.client.ClientConsumer;
import org.apache.activemq.artemis.api.core.client.ClientMessage;

import org.apache.activemq.artemis.api.core.client.ClientSession;
import org.apache.activemq.artemis.api.core.client.ClientSessionFactory;
import org.apache.activemq.artemis.api.core.client.ServerLocator;

public class QueueCoreConsumer {

   public static void main(final String[] args) throws Exception {

      try ( ServerLocator locator = ActiveMQClient.createServerLocator("tcp://localhost:61616"); ClientSessionFactory factory = locator.createSessionFactory(); ClientSession session = factory.createSession()) {

         ClientConsumer consumer = session.createConsumer("example_two::t2");

         session.start();

         while (true) {

            ClientMessage msgReceived = consumer.receive();

            System.out.println("message = " + msgReceived.getBodyBuffer().readString());
            System.out.println("message = " + msgReceived.getMessageID());
            msgReceived.acknowledge();
         }

      }
   }
}
