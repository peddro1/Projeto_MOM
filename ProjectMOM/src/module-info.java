module ProjectMOM {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires jakarta.jms.api;
	requires activemq.client;
	requires java.management;

	
	opens application to javafx.graphics, javafx.fxml;
}
