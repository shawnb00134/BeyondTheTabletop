package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.AccountCreationViewModel;
import javafx.beans.property.StringProperty;

public class TestAccountCreationViewModel {

    private AccountCreationViewModel viewModel;

    @BeforeEach
    public void setUp() {
        viewModel = new AccountCreationViewModel();
    }

    @Test
    public void testInitialPropertiesAreEmpty() {
        assertEquals("", viewModel.firstNameProperty().get());
        assertEquals("", viewModel.lastNameProperty().get());
        assertEquals("", viewModel.emailProperty().get());
        assertEquals("", viewModel.usernameProperty().get());
        assertEquals("", viewModel.passwordProperty().get());
        assertEquals("", viewModel.confirmPasswordProperty().get());
    }

    @Test
    public void testSetAndGetFirstNameProperty() {
        StringProperty firstName = viewModel.firstNameProperty();
        firstName.set("John");
        assertEquals("John", viewModel.firstNameProperty().get());
    }

    @Test
    public void testSetAndGetLastNameProperty() {
        StringProperty lastName = viewModel.lastNameProperty();
        lastName.set("Doe");
        assertEquals("Doe", viewModel.lastNameProperty().get());
    }

    @Test
    public void testSetAndGetEmailProperty() {
        StringProperty email = viewModel.emailProperty();
        email.set("john.doe@example.com");
        assertEquals("john.doe@example.com", viewModel.emailProperty().get());
    }

    @Test
    public void testSetAndGetUsernameProperty() {
        StringProperty username = viewModel.usernameProperty();
        username.set("johndoe");
        assertEquals("johndoe", viewModel.usernameProperty().get());
    }

    @Test
    public void testSetAndGetPasswordProperty() {
        StringProperty password = viewModel.passwordProperty();
        password.set("secret123");
        assertEquals("secret123", viewModel.passwordProperty().get());
    }

    @Test
    public void testSetAndGetConfirmPasswordProperty() {
        StringProperty confirmPassword = viewModel.confirmPasswordProperty();
        confirmPassword.set("secret123");
        assertEquals("secret123", viewModel.confirmPasswordProperty().get());
    }

    @Test
    public void testPropertiesAreConsistent() {
        StringProperty firstName = viewModel.firstNameProperty();
        firstName.set("Alice");
        assertSame(firstName, viewModel.firstNameProperty());

        StringProperty lastName = viewModel.lastNameProperty();
        lastName.set("Smith");
        assertSame(lastName, viewModel.lastNameProperty());

        StringProperty email = viewModel.emailProperty();
        email.set("alice.smith@example.com");
        assertSame(email, viewModel.emailProperty());

        StringProperty username = viewModel.usernameProperty();
        username.set("alicesmith");
        assertSame(username, viewModel.usernameProperty());

        StringProperty password = viewModel.passwordProperty();
        password.set("mypassword");
        assertSame(password, viewModel.passwordProperty());

        StringProperty confirmPassword = viewModel.confirmPasswordProperty();
        confirmPassword.set("mypassword");
        assertSame(confirmPassword, viewModel.confirmPasswordProperty());
    }
}
