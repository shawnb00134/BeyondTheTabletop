package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Weapon;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterManagerViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.collections.ObservableList;

class TestCharacterManagerViewModel {
	private CharacterManagerViewModel viewModel;
	private Player mockPlayer;

    @BeforeEach
    void setUp() {
    	AccountInfo account1 = new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe1", "securepassword");
    	SystemContextViewModel.getInstance().setCurrentAccount(account1);
    	this.mockPlayer = new Player(account1); 
        this.viewModel = new CharacterManagerViewModel();
    }

    @Test
    void testConstructorInitializesProperties() {
        assertNotNull(this.viewModel.getCharacterList());
        assertTrue(this.viewModel.getCharacterList().isEmpty());
    }
	
    @Test
    void testAddCharacterToList() {
Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory();
		
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Weapon 1");
		weaponMastery.add("Weapon 2");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 2, 10, 10,charAttributes, charClass, weaponMastery, charInventory, 
				weaponMastery, null, weaponMastery, charSpecies, "", "", "", "", "", "", "", "", "", false, "", "1" );
		
        
        this.viewModel.addCharacterToList(character);
        assertTrue(this.viewModel.getCharacterList().contains(character));
    }
    
    @Test
    void testGetCharacter() {
        this.viewModel.getCharacter();
        ObservableList<Character> characterList = this.viewModel.getCharacterList();
        assertTrue(characterList.isEmpty());
    }
    
    @Test
    void testLoadCharacters() {
    	Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory();
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Weapon 1");
		weaponMastery.add("Weapon 2");
		
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 2, 10, 10,charAttributes, charClass, weaponMastery, charInventory, 
				weaponMastery, null, weaponMastery, charSpecies, "", "", "", "", "", "", "", "", "", false, "", "1");
		
    	 List<Character> fakeCharacters = new ArrayList<>();
         fakeCharacters.add(character);

         this.viewModel.loadCharacters(fakeCharacters);

         assertEquals(1, this.viewModel.getCharacterList().size());
    }
    
    @Test
    void testDeleteCharacter() {
    	Character char1 = new Character();
    	Character char2 = new Character();
    	Character char3 = new Character();
    	
    	this.viewModel.addCharacterToList(char1);
    	this.viewModel.addCharacterToList(char2);
    	this.viewModel.addCharacterToList(char3);
    	assertEquals(3, this.viewModel.getCharacterList().size());
    	
    	this.viewModel.deleteCharacter(char2);
    	assertEquals(2, this.viewModel.getCharacterList().size());
    }
    
    @Test
    void testGetAccountInformation() {
    	assertEquals(this.mockPlayer.toString(), this.viewModel.getPlayerOfCurrentAccount().toString());
    }
    
    @Test
    void testCharacterCreationButtonEnable() {
    	Character char1 = new Character();
    	Character char2 = new Character();
    	Character char3 = new Character();
    	Character char4 = new Character();
    	Character char5 = new Character();
    	Character char6 = new Character();
    	
    	String limit1 = "Characters: 4/5";
    	String limit2 = "Characters: 5/5";
    	String limit3 = "Characters: 6/5";
    	
    	this.viewModel.addCharacterToList(char1);
    	this.viewModel.addCharacterToList(char2);
    	this.viewModel.addCharacterToList(char3);
    	this.viewModel.addCharacterToList(char4);
    	assertFalse(this.viewModel.getCreateNewCharacterButton().getValue());
    	assertEquals(limit1, this.viewModel.getCharacterLimitText());
    	
    	this.viewModel.addCharacterToList(char5);
    	assertFalse(this.viewModel.getCreateNewCharacterButton().getValue());
    	assertEquals(limit2, this.viewModel.getCharacterLimitText());
    	
    	this.viewModel.addCharacterToList(char6);
    	assertFalse(this.viewModel.getCreateNewCharacterButton().getValue());
    	assertEquals(limit3, this.viewModel.getCharacterLimitText());
    }
}
