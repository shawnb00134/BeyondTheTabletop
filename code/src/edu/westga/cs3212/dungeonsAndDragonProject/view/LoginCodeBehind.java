package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Login scene Codebehind Handles user authentication and navigation.
 * 
 * @author Shawn Bretthauer
 * @version Spring 2025
 */
public class LoginCodeBehind {
	private LoginViewModel viewModel;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button buttonClose;

	@FXML
	private Button buttonCreateAccount;

	@FXML
	private Label errorLabel;

	@FXML
	private Button buttonSignIn;

	@FXML
	private AnchorPane loginPane;

	@FXML
	private TextField textFieldPassword;

	@FXML
	private TextField textFieldUsername;

	private SceneController sceneController;

	@FXML
	void initialize() {
		this.sceneController = new SceneController();
		this.viewModel = new LoginViewModel();

		assert this.buttonClose != null : "fx:id=\"buttonClose\" was not injected: check your FXML file 'Login.fxml'.";
		assert this.buttonCreateAccount != null
				: "fx:id=\"buttonCreateAccount\" was not injected: check your FXML file 'Login.fxml'.";
		assert this.buttonSignIn != null
				: "fx:id=\"buttonSignIn\" was not injected: check your FXML file 'Login.fxml'.";
		assert this.loginPane != null : "fx:id=\"loginPane\" was not injected: check your FXML file 'Login.fxml'.";
		assert this.textFieldPassword != null
				: "fx:id=\"textFieldPassword\" was not injected: check your FXML file 'Login.fxml'.";
		assert this.textFieldUsername != null
				: "fx:id=\"textFieldUsername\" was not injected: check your FXML file 'Login.fxml'.";

		this.textFieldUsername.textProperty().bindBidirectional(this.viewModel.usernameProperty());
		this.textFieldPassword.textProperty().bindBidirectional(this.viewModel.passwordProperty());
		this.errorLabel.textProperty().bindBidirectional(this.viewModel.errorProperty());

		this.eventHandler();
		this.buttonSignIn.disableProperty()
				.bind(this.viewModel.usernameProperty().isEmpty().or(this.viewModel.passwordProperty().isEmpty()));
	}

	void eventHandler() {
		this.buttonCreateAccount.setOnAction((ActionEvent event) -> {
			try {
				this.sceneController.switchToAccountCreation(event);
			} catch (IOException exception) {
				this.errorLabel.textProperty().set("error switching to create account scene");
			}
		});

		this.buttonSignIn.setOnAction((ActionEvent event) -> {
			try {
				if (this.viewModel.handleSignIn()) {
					
					this.sceneController.switchToCharacterManager(event);
				}
			} catch (IOException exception) {
				this.errorLabel.textProperty().set("error switching to character manager scene");
			}
		});

		this.buttonClose.setOnAction((ActionEvent event) -> {
			Platform.exit();
		});
	}
}
