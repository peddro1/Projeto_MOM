package application;



import javax.jms.JMSException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SampleController {
	
	private Publisher sensorTemp;
	private Publisher sensorUmi;
	private Publisher sensorVelo;
	
    @FXML
    private CheckBox checkBoxTemp;

    @FXML
    private CheckBox checkBoxUmi;

    @FXML
    private CheckBox checkBoxVel;

    @FXML
    private TextField clientName;

    @FXML
    private Label flagSensorTemp;

    @FXML
    private Label flagSensorUmi;

    @FXML
    private Label flagSensorVel;

    @FXML
    private TextArea history;

    @FXML
    private TextField maxTemp;

    @FXML
    private TextField maxUmi;

    @FXML
    private TextField maxVel;

    @FXML
    private TextField minTemp;

    @FXML
    private TextField minUmi;

    @FXML
    private TextField minVel;

    @FXML
    private Button saveConfig;

    @FXML
    private Slider tempSlider;

    @FXML
    private Slider umiSlider;

    @FXML
    private Slider veloSlider;
    
    @FXML
    private Button createClient;

	
    public void saveConfig() {
    	int minTemp = Integer.valueOf(this.minTemp.getText());
    	int maxTemp = Integer.valueOf(this.maxTemp.getText());
		this.tempSlider.setMax(maxTemp);
		this.tempSlider.setMin(minTemp);
		this.tempSlider.setValue((maxTemp + minTemp)/2);
		
		int minUmi = Integer.valueOf(this.minUmi.getText());
    	int maxUmi = Integer.valueOf(this.maxUmi.getText());
		this.umiSlider.setMax(maxUmi);
		this.umiSlider.setMin(minUmi);
		this.umiSlider.setValue((maxUmi + minUmi)/2);
		
		int minVel = Integer.valueOf(this.minVel.getText());
    	int maxVel = Integer.valueOf(this.maxVel.getText());
		this.veloSlider.setMax(maxVel);
		this.veloSlider.setMin(minVel);
		this.veloSlider.setValue((maxVel + minVel)/2);
		
    }
    

    public void createTempSensor() throws JMSException {
    	this.sensorTemp = new Publisher("TEMPERATURA");
    	this.flagSensorTemp.setText("Criado V");
    	
    }
    
    public void createUmiSensor() throws JMSException {
    	this.sensorUmi = new Publisher("UMIDADE");
    	this.flagSensorUmi.setText("Criado V");
    }
    
    
    public void createVelSensor() throws JMSException {
    	this.sensorVelo = new Publisher("VELOCIDADE");
    	this.flagSensorVel.setText("Criado V");
    }
    
    public void checkLimitsTemp() throws JMSException {
    	System.out.println(this.minTemp.getText());
    	System.out.println(this.tempSlider.getValue());
    	if(this.tempSlider.getValue() == Double.valueOf(this.minTemp.getText())) {
	    	this.sensorTemp.sendMessage("min", this.minTemp.getText());
			
    	}
    	else if(this.tempSlider.getValue() == Double.valueOf(this.maxTemp.getText())) {
	    	this.sensorTemp.sendMessage("max", this.maxTemp.getText());
			
    	}
    }

    public void checkLimitsUmi() throws JMSException {
    	System.out.println(this.minUmi.getText());
    	System.out.println(this.umiSlider.getValue());
    	if(this.umiSlider.getValue() == Double.valueOf(this.minUmi.getText())) {
	    	this.sensorUmi.sendMessage("min", this.minUmi.getText());
			
    	}
    	else if(this.umiSlider.getValue() == Double.valueOf(this.maxUmi.getText())) {
    		this.sensorUmi.sendMessage("max", this.maxUmi.getText());
			
    	}
    }
    
    public void checkLimitsVel() throws JMSException {
    	System.out.println(this.minVel.getText());
    	System.out.println(this.veloSlider.getValue());
    	if(this.veloSlider.getValue() == Double.valueOf(this.minVel.getText())) {
	    	this.sensorVelo.sendMessage("min", this.minVel.getText());
			
    	}
    	else if(this.veloSlider.getValue() == Double.valueOf(this.maxVel.getText())) {
	    	this.sensorVelo.sendMessage("max", this.maxVel.getText());
			
    	}
    }

    public void loadTopics() throws JMSException {
    	ActiveTopics activeTopics = new ActiveTopics();
    	for( String topicName : activeTopics.getTopics()) {
    		System.out.println(topicName);
    		if(this.checkBoxTemp.getText().toUpperCase().equals(topicName)) {
    			this.checkBoxTemp.setDisable(false);
    		}
    		if(this.checkBoxUmi.getText().toUpperCase().equals(topicName)) {
    			this.checkBoxUmi.setDisable(false);
    		}
    		if(this.checkBoxVel.getText().toUpperCase().equals(topicName)) {
    			this.checkBoxVel.setDisable(false);
    		}
    	}
    }
    
    public void createClient() throws JMSException {
    	if(this.checkBoxTemp.isSelected()) {
    		new Subscriber("TEMPERATURA", history, this.clientName.getText());
    	}
    	if(this.checkBoxUmi.isSelected()) {
    		new Subscriber("UMIDADE", history, this.clientName.getText());
    	}
    	if(this.checkBoxVel.isSelected()) {
    		new Subscriber("VELOCIDADE", history, this.clientName.getText());
    	}
    }
}
