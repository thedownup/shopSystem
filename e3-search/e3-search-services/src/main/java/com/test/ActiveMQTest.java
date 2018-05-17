package com.test;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActiveMQTest {

	public static void main(String[] args) {
		//		sendMessage();
		testQueueProducer();
		testQueueConsumer();
	}

	//生产者：生产消息，发送端。
	public static void testQueueProducer(){
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://101.132.183.157:61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("test-queue111");
			MessageProducer producer = session.createProducer(queue);
			TextMessage message = session.createTextMessage("hello activeMq,this is my first test.");
			producer.send(message);

			producer.close();
			session.close();
			connection.close();

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	//消费者：接收消息。
	public static void testQueueConsumer(){
		try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://101.132.183.157:61616");
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("test-queue111");

			MessageConsumer consumer = session.createConsumer(queue);

			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message mes) {
					TextMessage textMessage = (TextMessage) mes;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Topic
	public void testTopicProducer(){

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://101.132.183.157:61616");
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = session.createTopic("topic-text");

			MessageProducer producer = session.createProducer(topic);

			TextMessage textMessage = session.createTextMessage("topic ======== ");

			producer.send(textMessage);
			testTopicConsumer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Consumer
	public static void testTopicConsumer() throws IOException{
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://101.132.183.157:61616");
		try {
			Connection connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("topic-text");
			MessageConsumer consumer = session.createConsumer(topic);

			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message mes) {
					TextMessage textMessage = (TextMessage) mes;
					try {
						String text = textMessage.getText();
						System.out.println(text);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.in.read();

	}

//	@Test
//	public void ActiveMQTest(){
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-activemq.xml");
//		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
//		// 第三步：从容器中获得一个Destination对象
//		Queue queue = (Queue) applicationContext.getBean("queueDestination");
//		// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
//		jmsTemplate.send(queue, new MessageCreator() {
//			
//			@Override
//			public Message createMessage(Session session) throws JMSException {
//				TextMessage textMessage = session.createTextMessage("spring activemq test");
//				return textMessage;
//			}
//		});
//	}
	
/*	@Test
	public void test1() throws IOException{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext-activemq.xml");
		System.in.read();
	}
*/
}
