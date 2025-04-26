package edu.westga.cs3212.dungeonsAndDragonProject;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**Starting point for application
 * 
 * @author Kelis, Shawn, El, Liam
 * @version Spring 2025
 * 
 */
public class Main extends Application {

	private static final String WINDOW_TITLE = "Beyond The Tabletop";
	private static final String GUI_RESOURCE = "view/Login.fxml";
	
	/**
	 * Sets up the GUI loading.
	 * 
	 * @param primaryStage stage
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(Main.GUI_RESOURCE));
		Scene scene = new Scene(parent);
		primaryStage.setTitle(Main.WINDOW_TITLE);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Entry point
	 * @param args arguments
	 */
	public static void main(String[] args) {
		new Thread(()-> {
			JsonObject requestObject = new JsonObject();
			requestObject.addProperty("type", "start");
			String request = new Gson().toJson(requestObject);
			String response = Client.sendRequest(request);
			System.out.println("Response from server: " + response);
		}).start();
		
		Main.launch(args);
	}
	

}
