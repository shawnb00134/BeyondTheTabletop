package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;

import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the get character method of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestGetCharacter {

	@Test
	void TestValidCharacter() {
		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");
		
		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory(8);
		charInventory.addCoinToPurse(2);
		
		Character newCharacter = new Character("Warrior", 10, 1, 1, 1, charAttributes, charClass, null, charInventory, null, null, null, null, null, null, null, null, null, null, null, null, null, true, "", "testcreation");

		AccountInfo testInfo = new AccountInfo("testcreation", "testcreation", "testcreation", "testc@gmail.com", "testcreation", "testcreation");
		
		SystemContextViewModel.getInstance().setCurrentAccount(testInfo);
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		SystemContextViewModel.getInstance().setCharacterSelection(newCharacter);
		
		
		Character character = viewModel.getCharacter();
		
		assertAll(() -> {
			assertEquals(0, character.getCharacterAttributes().getStrength());
			assertEquals(0, character.getCharacterAttributes().getDexterity());
			assertEquals(0, character.getCharacterAttributes().getConstitution());
			assertEquals(0, character.getCharacterAttributes().getIntelligence());
			assertEquals(0, character.getCharacterAttributes().getWisdom());
			assertEquals(0, character.getCharacterAttributes().getCharisma());
			assertEquals(0, character.getCharacterInventory().getCharacterCoinPurse());
			assertEquals(1, character.getCharacterLevel());
			assertEquals(0, character.getCharacterArmorClass());
			assertEquals("", character.getCharacterName());
		});
		
		SystemContextViewModel.getInstance().setCharacterSelection(null);
	}
	
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
