package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The view model for the login screen.
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class LoginViewModel {
	private StringProperty usernameProperty;
	private StringProperty passwordProperty;
	private StringProperty errorProperty;

	/**
	 * Constructs a new LoginViewModel
	 * 
	 * @precondition none
	 * @postcondition all properties are initialized
	 */
	public LoginViewModel() {
		this.usernameProperty = new SimpleStringProperty("");
		this.passwordProperty = new SimpleStringProperty("");
		this.errorProperty = new SimpleStringProperty("");
	}

	/**
	 * Retrieves the StringProperty representing the username entered.
	 * 
	 * @return the StringProperty for the username
	 */
	public StringProperty usernameProperty() {
		return this.usernameProperty;
	}

	/**
	 * Retrieves the StringProperty representing the password entered.
	 * 
	 * @return the StringProperty for the password
	 */
	public StringProperty passwordProperty() {
		return this.passwordProperty;
	}

	/**
	 * Retrieves the StringProperty representing the error feedback.
	 * 
	 * @return the StringProperty for the error message
	 */
	public StringProperty errorProperty() {
		return this.errorProperty;
	}

	/**
	 * Handles the event when the sign in button is pressed.
	 * 
	 * @precondition none
	 * @postcondition properties are cleared
	 * 
	 * @return whether or not sign in was successful
	 */
	public boolean handleSignIn() {
		this.errorProperty.set("");
		AccountInfo info = ServerCommunicationHandler.authenticateUser(this.usernameProperty.getValue(),
				this.passwordProperty.getValue());
		SystemContextViewModel.getInstance().setCurrentAccount(info);

		if (info == null) {
			this.errorProperty.set("Incorrect username or password.");
			return false;
		}

		return true;
	}

}
