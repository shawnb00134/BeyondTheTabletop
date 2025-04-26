package edu.westga.cs3212.dungeonsAndDragonProject.test.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;

public class TestCharacterSetters {
	
	@Test
	void testSetLevel() {
		Character character = new Character();
		character.setCharacterLevel(9);
		assertEquals(9, character.getCharacterLevel());
	}
	
	@Test
	void testSetCharacterHeathLevelNegativeArgument() {
		Character character = new Character();
		assertFalse(character.setCharacterCurrentHealthPoints(-1));
	}
	
	@Test
	void testSetCharacterHeathLevelPositiveArgument() {
		Character character = new Character();
		assertTrue(character.setCharacterCurrentHealthPoints(1));
	}
	
	@Test
	void testSetArmorClassPositiveArgument() {
		Character character = new Character();
		character.setArmorClass(1);
		assertEquals(1, character.getCharacterArmorClass());
	}
	
	@Test
	void testSetRole() {
		Character character = new Character();
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		character.setCharacterClass(charClass);
		assertEquals(charClass, character.getCharacterClass());
	}
	
	@Test
	void testSetRace() {
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Character character = new Character();
		character.setCharacterSpecies(charSpecies);
		assertEquals(charSpecies, character.getCharacterSpecies());
	}
	
	@Test
	void testSetInventory() {
		Inventory inventory = new Inventory();
		
		Character character = new Character();
		character.setInventory(inventory);
		assertEquals(inventory, character.getCharacterInventory());
	}
	
	@Test
	void testSetAlignment() {
		Character character = new Character();
		character.setCharacterAlignment("Good");
		assertEquals("Good", character.getCharacterAlignment());
	}
	
	@Test
	void testSetFaith() {
		Character character = new Character();
		character.setCharacterFaith("Good");
		assertEquals("Good", character.getCharacterFaith());
	}
	
	@Test
	void testSetHair() {
		Character character = new Character();
		character.setCharacterHair("Good");
		assertEquals("Good", character.getCharacterHair());
	}
	
	@Test
	void testSetSkin() {
		Character character = new Character();
		character.setCharacterSkin("Good");
		assertEquals("Good", character.getCharacterSkin());
	}
	
	@Test
	void testSetEyes() {
		Character character = new Character();
		character.setCharacterEyes("Good");
		assertEquals("Good", character.getCharacterEyes());
	}
	
	@Test
	void testSetHeight() {
		Character character = new Character();
		character.setCharacterHeight("Good");
		assertEquals("Good", character.getCharacterHeight());
	}
	
	@Test
	void testSetWeight() {
		Character character = new Character();
		character.setCharacterWeight("Good");
		assertEquals("Good", character.getCharacterWeight());
	}
	
	@Test
	void testSetAge() {
		Character character = new Character();
		character.setCharacterAge("Good");
		assertEquals("Good", character.getCharacterAge());
	}
	
	@Test
	void testSetGender() {
		Character character = new Character();
		character.setCharacterGender("Good");
		assertEquals("Good", character.getCharacterGender());
	}
	
	@Test
	void testSetNotes() {
		Character character = new Character();
		character.setCharacterCampaignNotes("Good");
		assertEquals("Good", character.getCharacterCampaignNotes());
	}
}
