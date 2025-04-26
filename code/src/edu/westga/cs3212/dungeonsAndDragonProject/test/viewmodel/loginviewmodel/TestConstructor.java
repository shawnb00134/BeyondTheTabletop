package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.loginviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.LoginViewModel;

/**
 * Tests the constructor of the LoginViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestConstructor {

	@Test
	void testValidConstructor() {
		LoginViewModel viewModel = new LoginViewModel();
		
		assertAll(() -> {
			assertTrue(viewModel.usernameProperty().get().isEmpty());
			assertTrue(viewModel.passwordProperty().get().isEmpty());
			assertTrue(viewModel.errorProperty().get().isEmpty());
		});
	}
}
