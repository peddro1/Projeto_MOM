package application;


import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Subscriber implements MessageListener{
	
	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	private String topicName = "";
	
	private TextArea textArea;
	
	private String nameClient = "";
	
	Subscriber(String topicName, TextArea textArea, String nameClient) throws JMSException{
		this.topicName = topicName;
		this.textArea = textArea;
		this.nameClient = nameClient;
		this.receiveMessage();
		
	}
	
	public void receiveMessage() throws JMSException {
		System.out.println("passou");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	
		Destination destination = session.createTopic(this.topicName);
		
		MessageConsumer subscriber = session.createConsumer(destination);
		
		subscriber.setMessageListener(this);
		
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(message instanceof TextMessage) {
			Platform.runLater(() -> {
				try {
					System.out.println(((TextMessage)message).getText());
					this.textArea.appendText(this.nameClient + ": " + ((TextMessage)message).getText() + "\n");
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
		}
	}

}
