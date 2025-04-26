package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the constructor of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestConstructor {

	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        
	}
	
	@Test
	void testValidConstructor() {
		SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();

		assertAll(() -> {
			assertEquals(0, viewModel.attributeCharismaProperty().get());
			assertEquals(0, viewModel.attributeConstitutionProperty().get());
			assertEquals(0, viewModel.attributeDexterityProperty().get());
			assertEquals(0, viewModel.attributeIntelligenceProperty().get());
			assertEquals(0, viewModel.attributeStrengthProperty().get());
			assertEquals(0, viewModel.attributeWisdomProperty().get());
			assertEquals(0, viewModel.characterGoldProperty().get());
			assertEquals(0, viewModel.characterLevelProperty().get());
			assertEquals(0, viewModel.characterArmorClassRatingProperty().get());
			
			assertEquals(null, viewModel.characterNameProperty().get());
			assertEquals(null, viewModel.characterAgeProperty().get());
			assertEquals(null, viewModel.characterAlignmentProperty().get());
			assertEquals(null, viewModel.characterEyesProperty().get());
			assertEquals(null, viewModel.characterFaithProperty().get());
			assertEquals(null, viewModel.characterGenderProperty().get());
			assertEquals(null, viewModel.characterHairProperty().get());
			assertEquals(null, viewModel.characterHeightProperty().get());
			assertEquals(null, viewModel.characterSkinProperty().get());
			assertEquals(null, viewModel.characterWeightProperty().get());
			assertEquals(null, viewModel.equipmentProperty().get());
			assertEquals(null, viewModel.featProperty().get());
			assertEquals(null, viewModel.spellProperty().get());
			assertEquals(null, viewModel.getWeaponMastery1().get());
			assertEquals(null, viewModel.getWeaponMastery2().get());
			
			assertEquals(0, viewModel.getCharacterClassList().get().size());
			assertEquals(null, viewModel.getSelectedCharacterClass().get());
			assertEquals(null, viewModel.selectedCharacterSpecies().get());
			assertTrue(viewModel.getWeaponProficencyList().isEmpty());
			assertEquals(0, viewModel.getSpeciesList().get().size());			
		});
	}
	
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
