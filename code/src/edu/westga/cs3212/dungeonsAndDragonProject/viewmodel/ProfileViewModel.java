package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The view model for the profile screen.
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class ProfileViewModel {
	private StringProperty usernameProperty;
	private StringProperty errorProperty;
	
	/**
	 * Constructs a new ProfileViewModel
	 * 
	 * @precondition none
	 * @postcondition all properties are initialized
	 */
	public ProfileViewModel() {
		this.usernameProperty = new SimpleStringProperty("");
		this.errorProperty = new SimpleStringProperty("");
		
		this.usernameProperty.setValue(SystemContextViewModel.getInstance().getCurrentAccount().getUsername());
	}
	
	/**
	 * Retrieves the StringProperty representing the username of the account.
	 * 
	 * @return the StringProperty for the username
	 */
	public StringProperty usernameProperty() {
		return this.usernameProperty;
	}
	
	/**
	 * Retrieves the StringProperty representing an error encountered.
	 * 
	 * @return the StringProperty for the error
	 */
	public StringProperty errorProperty() {
		return this.errorProperty;
	}
	
	/**
	 * Handles the event when the logout button is pressed.
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void handleLogout() {
		SystemContextViewModel.getInstance().setCurrentAccount(null);
	}
}
