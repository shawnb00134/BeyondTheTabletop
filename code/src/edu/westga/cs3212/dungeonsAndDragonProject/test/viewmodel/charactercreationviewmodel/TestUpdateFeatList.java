package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the updateFeatList method of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestUpdateFeatList {

	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testWhenValidFeatToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.featProperty().set("Test Feat");
		viewModel.updateFeatList();

		assertAll(() -> {
			assertEquals("Test Feat", viewModel.getFeatsList().get().getFirst());
			assertEquals(1, viewModel.getFeatsList().size());
		});
	}
	
	@Test
	void testWhenNullFeatToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.featProperty().setValue(null);;
		viewModel.updateFeatList();

		assertAll(() -> {
			assertEquals(0, viewModel.getFeatsList().size());
		});
	}
	
	@Test
	void testWhenEmptyFeatsToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.featProperty().setValue("");
		viewModel.updateFeatList();

		assertAll(() -> {
			assertEquals(0, viewModel.getFeatsList().size());
		});
	}
	
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
