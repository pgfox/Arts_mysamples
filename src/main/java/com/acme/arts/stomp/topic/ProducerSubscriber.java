package com.acme.arts.stomp.topic;

import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

public class ProducerSubscriber {

   public static void main(String[] args) throws Exception {
      ProducerSubscriber producer = new ProducerSubscriber();
      producer.doit();
   }

   private void doit() throws Exception {

      StompConnection connection = new StompConnection();
      connection.open("localhost", 61613);
      connection.connect("admin", "admin");

      //create a subscriber
      connection.subscribe("/topic/test", Subscribe.AckModeValues.CLIENT);

      //send 100 messages
      for (int i = 0; i < 10; i++) {
         connection.send("/topic/test", "message" + i);
      }


      //receive 100 messages
      for (int i = 0; i < 10; i++) {
         StompFrame message = connection.receive();
         System.out.println(message.getBody());
         connection.ack(message);
      }
      
      //close
      connection.disconnect();
   }

}