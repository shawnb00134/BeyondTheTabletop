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
 * Tests the updateSpellList of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestUpdateSpellList {
	
	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testWhenValidSpellToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.spellProperty().set("Fire Ball");
		viewModel.updateSpellList();

		assertAll(() -> {
			assertEquals("Fire Ball", viewModel.getSpellList().get().getFirst());
			assertEquals(1, viewModel.getSpellList().size());
		});
	}
	
	@Test
	void testWhenNullSpellToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.spellProperty().setValue(null);;
		viewModel.updateSpellList();

		assertAll(() -> {
			assertEquals(0, viewModel.getSpellList().size());
		});
	}
	
	@Test
	void testWhenEmptyEmptyToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.spellProperty().setValue("");
		viewModel.updateSpellList();

		assertAll(() -> {
			assertEquals(0, viewModel.getSpellList().size());
		});
	}
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
