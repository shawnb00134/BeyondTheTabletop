package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.ProfileViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/** 
 * The profile code behind
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class ProfileCodeBehind {

	@FXML
	private Button buttonCampaignManager;

	@FXML
	private Button buttonCharacterManager;

	@FXML
	private Button buttonLogout;

	@FXML
	private Button buttonProfile;
	
    @FXML
    private Label errorLabel;

	@FXML
	private AnchorPane profilePane;

	@FXML
	private ImageView imageViewProfilePicture;

	@FXML
	private Label usernameLabel;
	
	private SceneController sceneController;
	
	private ProfileViewModel viewModel;
	
	@FXML
	void initialize() {
		this.sceneController = new SceneController();
		this.viewModel = new ProfileViewModel();

    	assert this.buttonCampaignManager != null : "fx:id=\"buttonCampaignManager\" was not injected: check your FXML file 'Profile.fxml'.";
        assert this.buttonCharacterManager != null : "fx:id=\"buttonCharacterManager\" was not injected: check your FXML file 'Profile.fxml'.";
        assert this.buttonProfile != null : "fx:id=\"buttonProfile\" was not injected: check your FXML file 'Profile.fxml'.";
        assert this.buttonLogout != null : "fx:id=\"buttonLogout\" was not injected: check your FXML file 'Profile.fxml'.";
        
        this.usernameLabel.textProperty().bindBidirectional(this.viewModel.usernameProperty());
        this.errorLabel.textProperty().bindBidirectional(this.viewModel.errorProperty());
		this.eventHandler();
	}
	
	void eventHandler() {
		this.buttonLogout.setOnAction((ActionEvent event) -> {
			try {
				this.viewModel.handleLogout();
				this.sceneController.switchToLogin(event);
			} catch (IOException exception) {
				this.errorLabel.textProperty().set("error logging out");
			}
		});
		
		this.buttonCampaignManager.setOnAction((ActionEvent event) -> {
			try {
				this.sceneController.switchToCampaignManager(event);
			} catch (IOException exception) {
				this.errorLabel.textProperty().set("error switching to campaign manager scene");
			}
		});
		
		this.buttonCharacterManager.setOnAction((ActionEvent event) -> {
			try {
				this.sceneController.switchToCharacterManager(event);
			} catch (IOException exception) {
				this.errorLabel.textProperty().set("error switching to character manager scene");
			}
		});
		
	}
}