package edu.westga.cs3212.dungeonsAndDragonProject.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.AccountCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Account Creation Codebehind
 * 
 * @author Shawn Bretthauer && El
 * 
 * @version Spring 2025
 */
public class AccountCreationCodeBehind {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane accountCreationPane;

	@FXML
	private Button buttonCancelCreation;

	@FXML
	private Button buttonCreateAccount;

	@FXML
	private TextField testFieldFirstName;

	@FXML
	private TextField testFieldLastName;

	@FXML
	private TextField textFieldEmail;

	@FXML
	private TextField textfieldPassword;

	@FXML
	private TextField textfieldUserName;

	@FXML
	private Label emailErrorLabel;

	@FXML
	private Label userNameErroLabel;

	@FXML
	private Label firstNameErrorLabel;

	@FXML
	private Label lastNameErrorLabel;

	@FXML
	private Label labelMessage;

	@FXML
	private Label passWordErroLabel;

	@FXML
	private TextField textfieldConfirmPassword;

	@FXML
	private Label verifyPassWordErroLabel;

	private AccountCreationViewModel viewModel;

	@FXML
	void initialize() {
		this.viewModel = new AccountCreationViewModel();

		this.testFieldFirstName.textProperty().bindBidirectional(this.viewModel.firstNameProperty());
		this.testFieldLastName.textProperty().bindBidirectional(this.viewModel.lastNameProperty());
		this.textFieldEmail.textProperty().bindBidirectional(this.viewModel.emailProperty());
		this.textfieldPassword.textProperty().bindBidirectional(this.viewModel.passwordProperty());
		this.textfieldUserName.textProperty().bindBidirectional(this.viewModel.usernameProperty());
		this.textfieldConfirmPassword.textProperty().bindBidirectional(this.viewModel.confirmPasswordProperty());

		this.buttonCreateAccount.setOnAction(event -> this.handleCreateAccount());
		this.buttonCancelCreation.setOnAction(event -> this.handleCancel());
	}

	private void handleCreateAccount() {
		this.clearErrorLabels();

		boolean isValid = this.validateFields();
		if (!isValid) {
			System.out.println("Do not create an account");
		}

		String firstName = this.viewModel.firstNameProperty().get().trim();
        String lastName = this.viewModel.lastNameProperty().get().trim();
        String email = this.viewModel.emailProperty().get().trim();
        String username = this.viewModel.usernameProperty().get().trim();
        String password = this.viewModel.passwordProperty().get().trim();
        String confirmedPassword  = this.viewModel.confirmPasswordProperty().get().trim();

        try {
            JsonObject requestObject = new JsonObject();
            requestObject.addProperty("type", "create account");
            requestObject.addProperty("accountId", UUID.randomUUID().toString());
            requestObject.addProperty("firstName", firstName);
            requestObject.addProperty("lastName", lastName);
            requestObject.addProperty("email", email);
            requestObject.addProperty("username", username);
            requestObject.addProperty("password", password);
            requestObject.addProperty("confirmPassword", confirmedPassword);
            
            String request = new Gson().toJson(requestObject);
            String response = Client.sendRequest(request);

            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
            String successCode = jsonResponse.get("success code").getAsString();

            if ("fail".equalsIgnoreCase(successCode)) {
                String errorDesc = jsonResponse.get("error description").getAsString();
                this.labelMessage.setText(errorDesc);
                this.labelMessage.setStyle("-fx-text-fill: red;");
                return;
            }
            
            JsonObject accountInfoObj = jsonResponse.getAsJsonObject("accountInfo");
            AccountInfo createdAccount = new Gson().fromJson(accountInfoObj, AccountInfo.class);
            SystemContextViewModel.getInstance().setCurrentAccount(createdAccount);
            
            Parent root = FXMLLoader.load(getClass().getResource("CharacterManager.fxml"));
            Stage stage = (Stage) this.buttonCreateAccount.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            this.showError("Account creation failed: " + ex.getMessage(), this.labelMessage);
        }
    }

	private boolean validateFields() {
		boolean hasError = false;

		String firstName = this.viewModel.firstNameProperty().get().trim();
		String lastName = this.viewModel.lastNameProperty().get().trim();
		String email = this.viewModel.emailProperty().get().trim();
		String username = this.viewModel.usernameProperty().get().trim();
		String password = this.viewModel.passwordProperty().get().trim();

		// If truly everything is empty:
		if (firstName.isBlank() && lastName.isBlank() && email.isBlank() && username.isBlank() && password.isBlank()) {
			this.labelMessage.setStyle("-fx-text-fill: red;");
			this.labelMessage.setText("Please fill in all fields.");
			return false;
		}

		// FIRST NAME
		if (firstName.isBlank()) {
			this.showError("First name is required.", this.firstNameErrorLabel);
			hasError = true;
		} else if (firstName.length() < 3) {
			this.showError("Must be at least 3 characters.", this.firstNameErrorLabel);
			hasError = true;
		} else {
			this.firstNameErrorLabel.setText("");
		}

		// LAST NAME
		if (lastName.isBlank()) {
			this.showError("Last name is required.", this.lastNameErrorLabel);
			hasError = true;
		} else if (lastName.length() < 3) {
			this.showError("Must be at least 3 characters.", this.lastNameErrorLabel);
			hasError = true;
		} else {
			this.lastNameErrorLabel.setText("");
		}

		// EMAIL
		if (email.isBlank()) {
			this.showError("Email is required.", this.emailErrorLabel);
			hasError = true;
		} else if (!Pattern.matches(EMAIL_REGEX, email)) {
			this.showError("Invalid email.", this.emailErrorLabel);
			hasError = true;
		} else {
			this.emailErrorLabel.setText("");
		}

		// USERNAME
		if (username.isBlank()) {
			this.showError("Username is required.", this.userNameErroLabel);
			hasError = true;
		} else {
			this.userNameErroLabel.setText("");
		}

		// PASSWORD
		if (password.isBlank()) {
			this.showError("Password is required.", this.passWordErroLabel);
			hasError = true;
		} else if (password.length() < 8) {
			this.showError("Must be at least 8 characters.", this.passWordErroLabel);
			hasError = true;
		} else {
			this.passWordErroLabel.setText("");
		}
		
		String confirmPassword = this.viewModel.confirmPasswordProperty().get().trim();
		if (!password.equals(confirmPassword)) {
		    this.showError("Passwords do not match.", this.verifyPassWordErroLabel);
		    hasError = true;
		} else {
		    this.verifyPassWordErroLabel.setText("");
		}
		return !hasError;
	}

	private void handleCancel() {
		Platform.exit();
	}

	private void showError(String message, Label label) {
		if (label != null) {
			label.setStyle("-fx-text-fill: red;");
			label.setText(message);
		} else {
			System.err.println(message);
		}
	}

	private void clearErrorLabels() {
		if (this.labelMessage != null) {
			this.labelMessage.setText("");
		}
		this.firstNameErrorLabel.setText("");
		this.lastNameErrorLabel.setText("");
		this.emailErrorLabel.setText("");
		this.userNameErroLabel.setText("");
	}

}
