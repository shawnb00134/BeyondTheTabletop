package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the AddCharacterFeatsToFeatsList method of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestAddCharacterFeatsToFeatsList {
	
	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testValidCall() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		List<String> testFeats = new ArrayList<String>(); 
		testFeats.add("Test Feats");
		viewModel.addCharacterFeatsToFeatsList(testFeats);

		assertAll(() -> {
			assertTrue(viewModel.getFeatsList().contains("Test Feats"));
		});
	}
}
