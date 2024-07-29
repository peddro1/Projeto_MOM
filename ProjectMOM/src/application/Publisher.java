package application;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher {
	
private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	private String topicName = "";
	
	Publisher(String topicName){
		this.topicName = topicName;
	}
	
	
	public void sendMessage(String type, String value ) throws JMSException {
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination destination = session.createTopic(this.topicName);
		
		MessageProducer publisher = session.createProducer(destination);
		
		
		TextMessage message = session.createTextMessage();
		
		message.setText(this.topicName + " " + type + " " + value);
		
		publisher.send(message);
		
		publisher.close();
		session.close();
		connection.close();
	}
	
	
}
