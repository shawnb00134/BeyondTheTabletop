package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the AddCharacterSpellsToSpellList method of the
 * CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestAddCharacterSpellsToSpellList {

	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testValidCall() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		List<String> testSpells = new ArrayList<String>(); 
		testSpells.add("Fire Ball");
		viewModel.addCharacterSpellsToSpellList(testSpells);

		assertAll(() -> {
			assertTrue(viewModel.getSpellList().contains("Fire Ball"));
		});
	}
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
