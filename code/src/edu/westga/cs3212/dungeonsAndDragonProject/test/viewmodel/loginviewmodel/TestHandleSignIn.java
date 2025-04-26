package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.loginviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.LoginViewModel;

/**
 * Tests the handle sign in method.
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestHandleSignIn {
	
	@Test
	void testInvalidUsernameAndPassword() {
		LoginViewModel viewModel = new LoginViewModel();
		viewModel.usernameProperty().set("bad user");
		viewModel.passwordProperty().set("bad password");
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");

		assertAll(() -> {
			assertFalse(viewModel.handleSignIn());
			assertEquals("Incorrect username or password.", viewModel.errorProperty().get());
		});
	}
	
	@Test
	void testValidUsernameAndPassword() {
		LoginViewModel viewModel = new LoginViewModel();
		viewModel.usernameProperty().set("one");
		viewModel.passwordProperty().set("password");
		
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");

		assertAll(() -> {
			assertTrue(viewModel.handleSignIn());
		});
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Client.sendRequest("exit");
	}
	
	public static class TestResponseUtil {
		private static String fakeResponse = "";

		public static void setFakeResponse(String response) {
			fakeResponse = response;
		}

		public static String getFakeResponse(String request) {
			return fakeResponse;
		}
	}
}
