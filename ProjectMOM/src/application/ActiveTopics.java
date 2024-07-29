package application;

import java.util.ArrayList;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQTopic;

public class ActiveTopics {

	private String url = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	private ArrayList<String> topics = null;
	
	ActiveTopics() throws JMSException{
		this.topics = new ArrayList<String>();
		this.getTopicsName();
	}
	
	public void getTopicsName() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();
		connection.start();
		
		DestinationSource dest = connection.getDestinationSource();
		
		for(ActiveMQTopic topic : dest.getTopics()) {
			this.topics.add(topic.getPhysicalName());
		}
		
		connection.close();
	}
	
	public ArrayList<String> getTopics(){
		return this.topics;
	}
}
