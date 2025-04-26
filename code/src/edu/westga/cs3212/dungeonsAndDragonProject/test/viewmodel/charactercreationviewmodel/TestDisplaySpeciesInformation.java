package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the DisplaySpeciesInformation method of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestDisplaySpeciesInformation {

	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        
	}
	
	@Test
	void testValidCall() {
		SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		Race testRace = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "numerous and the most versatile and culturally diverse species", 
				new String[] {"Resourceful", "Skillful", "Versatile"});
		
		viewModel.displaySpeciesInformation(testRace);

		assertAll(() -> {
			assertEquals("dwarf" + "\n" + "Species: Race" + "\n" + "Type: HUMANOID   Speed: 30  Size: MEDIUM" + "\n" + "numerous and the most versatile and culturally diverse species" + "\n" + "Traits: Resourceful, Skillful, Versatile", viewModel.updateSpeciesInformation().get());
		});
	}
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
