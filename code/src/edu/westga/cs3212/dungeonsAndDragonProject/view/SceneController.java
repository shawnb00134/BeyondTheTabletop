package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Controls the loading/switching of scene for the app.
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class SceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	/**
	 * Loads the Account Creation scene from the Login scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToAccountCreation(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("AccountCreation.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Login scene from the Profile scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToLogin(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Profile scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToProfile(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Character Manager scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToCharacterManager(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("CharacterManager.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Switches from the Character Creation to the Character Manager
	 * 
	 * @param event event
	 * 
	 * @throws IOException
	 */
	public void switchToCharacterManagerFromCreator(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("CharacterManager.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Campaign Manager scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToCampaignManager(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("CampaignManager.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Campaign Creation scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToCampaignCreation(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("CampaignCreation.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Resources scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToResources(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("Resources.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Character Creation scene
	 * 
	 * @param event event
	 * @throws IOException
	 */
	public void switchToCharacterCreation(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("CharacterCreation.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
	
	/**
	 * Loads the Character Viewer scene
	 * @param event event
	 * @throws IOException
	 */
	public void switchToCharacterViewer(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("CharacterViewer.fxml"));
		this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		this.scene = new Scene(this.root);
		this.stage.setScene(this.scene);
		this.stage.show();
	}
}
