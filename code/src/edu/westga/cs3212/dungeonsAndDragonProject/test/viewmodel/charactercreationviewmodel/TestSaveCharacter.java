//package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
//import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
//import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
//import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
//import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
//import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
//import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
//import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
//
//public class TestSaveCharacter {
//	
//	private AccountInfo account1;
//	private File testCharactersFile;
//	
//	@BeforeEach
//	void setUp() {
//        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
//		this.testCharactersFile = new File("temp-test-characters.json");
//        
//	}
//	
//	@AfterEach
//	void tearDown() {
//		if (this.testCharactersFile.exists()) {
//			testCharactersFile.delete();
//		}
//	}
//	
//	@Test
//	void testValidCall() {
//		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
//		
//		Set<String> featureSet = new HashSet<>();
//		featureSet.add("feature1");
//		
//		Set<String> proficiencySet = new HashSet<>();
//		proficiencySet.add("Animal Handling");
//		
//		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
//		
//		Inventory charInventory = new Inventory(8);
//		charInventory.addCoinToPurse(2);
//		
//		Character newCharacter = new Character("Warrior", 10, 1, 1, 1, charAttributes, charClass, new ArrayList<String>(Arrays.asList("NotEmpty")), charInventory, new ArrayList<String>(Arrays.asList("NotEmpty")), new ArrayList<String>(Arrays.asList("NotEmpty")), new ArrayList<String>(Arrays.asList("NotEmpty")), null, null, null, null, null, null, null, null, null, null, true, "", "acc-123");
//		
//		SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
//		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
//		this.testCharactersFile = new File("temp-test-characters.json");
//		ServerCommunicationHandler.setCharacterFilePath(testCharactersFile.getPath());
//		SystemContextViewModel.getInstance().setCharacterSelection(newCharacter);
//
//		viewModel.saveCharacter();
//		
//		SystemContextViewModel.getInstance().setCharacterSelection(null);
//	}
//}
