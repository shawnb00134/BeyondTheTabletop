package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.profileviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.ProfileViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the handle logout method.
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestHandleLogout {

	@Test
	void testValidUsage() {	
//		AccountInfo info = ServerCommunicationHandler.authenticateUser("testcreation2", "testcreation2");
		AccountInfo testInfo = new AccountInfo("testcreation", "testcreation", "testcreation", "testc@gmail.com", "testcreation", "testcreation");
		SystemContextViewModel.getInstance().setCurrentAccount(testInfo);
		ProfileViewModel viewModel = new ProfileViewModel();
		viewModel.handleLogout();

		assertAll(() -> {
			assertTrue(SystemContextViewModel.getInstance().getCurrentAccount() == null);
		});
	}
}
