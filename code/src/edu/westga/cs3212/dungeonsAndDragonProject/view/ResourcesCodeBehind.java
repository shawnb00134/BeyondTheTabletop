package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Resources Codebehind
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class ResourcesCodeBehind {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buittonResources;

    @FXML
    private Button buttonCampaignManager;

    @FXML
    private Button buttonCharacterManager;

    @FXML
    private Button buttonProfile;
    
    private SceneController sceneController;

    @FXML
    void initialize() {
    	this.sceneController = new SceneController();
        this.eventHandlers();
    }

    private void eventHandlers() {
    	this.buttonCampaignManager.setOnAction((ActionEvent event) -> {
    		try {
				this.sceneController.switchToCampaignManager(event);
			} catch (IOException exception) {
				System.err.println("error switching to Account Creation scene");
			}
    	});
    	
		this.buttonProfile.setOnAction((event)-> {
			try {
				this.sceneController.switchToProfile(event);
			} catch (IOException exception) {
				System.err.println("error switching to Profile scene");
			}
		});
    }
}
