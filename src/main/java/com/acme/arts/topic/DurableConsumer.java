package com.acme.arts.topic;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class DurableConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DurableConsumer theApp = new DurableConsumer();
		try{
			theApp.doit();
		}catch(Exception ex){
			ex.printStackTrace(System.out);
		}

	}

	private void doit() throws Exception{

		try( ActiveMQConnectionFactory connectionFactory =  new ActiveMQConnectionFactory("tcp://localhost:61616?reconnectAttempts=-1&user=admin&password=admin");
		Connection connection = connectionFactory.createConnection();)

	{
		connection.setClientID("mytest-durable-core");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic myTopic = session.createTopic("durable_test");
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(myTopic, "durable_test_core");
		topicSubscriber.setMessageListener(new MyMessageListener());

		System.out.println("Press any key to end...");
		System.in.read();
	}

		
	}

	class MyMessageListener implements MessageListener {

		
		public void onMessage(Message message) {
			try {
				System.out.println("receive message "+ message.getJMSMessageID());
				if (message instanceof TextMessage){
					String contents = ((TextMessage) message).getText();
					System.out.println("message is: " + contents);
				}
				else{
					System.out.println("Message as expected");
				}
			} catch (JMSException e) {
				e.printStackTrace(System.out);
			}
			
		}
	}
}
