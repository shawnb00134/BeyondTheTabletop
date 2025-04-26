package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The AccountCreationViewModel is responsible for holding the user-input data
 * required to create a new account. 
 * 
 * @author MoriaEl
 * @version Spring 2025
 */
public class AccountCreationViewModel {
    
    private StringProperty firstNameProperty;
    private StringProperty lastNameProperty;
    private StringProperty emailProperty;
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private StringProperty confirmPasswordProperty;
    
    /**
     * Constructs a new AccountCreationViewModel
     */
    public AccountCreationViewModel() {
        this.firstNameProperty = new SimpleStringProperty("");
        this.lastNameProperty = new SimpleStringProperty("");
        this.emailProperty = new SimpleStringProperty("");
        this.usernameProperty = new SimpleStringProperty("");
        this.passwordProperty = new SimpleStringProperty("");
        this.confirmPasswordProperty = new SimpleStringProperty("");
    }

    /**
     * Retrieves the StringProperty representing the user's first name input.
     * 
     * @return the StringProperty for the user's first name
     */
    public StringProperty firstNameProperty() {
        return this.firstNameProperty;
    }

    /**
     * Retrieves the StringProperty representing the user's last name input.
     * 
     * @return the StringProperty for the user's last name
     */
    public StringProperty lastNameProperty() {
        return this.lastNameProperty;
    }

    /**
     * Retrieves the StringProperty representing the user's email input.
     * 
     * @return the StringProperty for the user's email
     */
    public StringProperty emailProperty() {
        return this.emailProperty;
    }

    /**
     *  Retrieves the StringProperty representing the user's desired username input.
     *  
     * @return the StringProperty for the user's username
     */
    public StringProperty usernameProperty() {
        return this.usernameProperty;
    }

    /**
     * Retrieves the StringProperty representing the user's password input.
     * 
     * @return the StringProperty for the user's password
     */
    public StringProperty passwordProperty() {
        return this.passwordProperty;
    }
    
    /**
     * Retrieves the StringProperty representing the user's confirmed password input.
     * 
     * @return the StringProperty for the user's confirmed password
     */
    public StringProperty confirmPasswordProperty() {
        return this.confirmPasswordProperty;
    }
}
