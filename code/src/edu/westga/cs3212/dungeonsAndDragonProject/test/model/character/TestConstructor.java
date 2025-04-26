package edu.westga.cs3212.dungeonsAndDragonProject.test.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Weapon;

public class TestConstructor {

	@Test
	void testDefaultCharacterConstructor() {
		Character character = new Character();
		
		assertEquals("", character.getCharacterName());
		assertEquals(0, character.getCharacterArmorClass());
		assertEquals(1, character.getCharacterLevel());		
		assertEquals(1, character.getCharacterCurrentHealthPoints());
		assertEquals(1, character.getCharacterMaxHealthPoints());
		assertEquals(8, character.getCharacterAttributes().getCharisma());
		assertEquals(8, character.getCharacterAttributes().getDexterity());
		assertEquals(8, character.getCharacterAttributes().getConstitution());
		assertEquals(8, character.getCharacterAttributes().getIntelligence());
		assertEquals(8, character.getCharacterAttributes().getStrength());
		assertEquals(8, character.getCharacterAttributes().getWisdom());
		assertEquals(null, character.getCharacterSpecies());
		assertEquals(null, character.getCharacterClass());
		assertEquals("", character.getPlayerOwnerID());
		assertEquals("", character.getCharacterCampaignNotes());
		assertEquals("", character.getCharacterAlignment());
		assertEquals("", character.getCharacterFaith());
		assertEquals("", character.getCharacterHair());
		assertEquals("", character.getCharacterSkin());
		assertEquals("", character.getCharacterEyes());
		assertEquals("", character.getCharacterHeight());
		assertEquals("", character.getCharacterWeight());
		assertEquals("", character.getCharacterAge());
		assertEquals("", character.getCharacterGender());
		assertFalse(character.hasInspiration());
		assertTrue(character.getWeaponMasteries().isEmpty());
		assertTrue(character.getSkillProficiencies().isEmpty());
		assertTrue(character.getInventoryByString().isEmpty());
		assertTrue(character.getCharacterInventory() != null);
		assertTrue(character.getCharacterFeats().isEmpty());
		assertTrue(character.getCharacterSpells().isEmpty());
	}
	
	@Test
	void testOverloadedCharacterConstructor() {
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
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		assertEquals("John", character.getCharacterName());
		assertEquals(charAttributes, character.getCharacterAttributes());
		assertEquals(charSpecies, character.getCharacterSpecies());
		assertEquals(charClass, character.getCharacterClass());
		assertEquals(charInventory, character.getCharacterInventory());
	}
	
	@Test
	void testOverloadedCharacterConstructorNameBlank() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Character("", 1, 1, 1, 1, null, null, null, null, null, null, null, null, "Alignment",
					"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		});
	}
	
	@Test
	void testOverloadedCharacterConstructorNameNull() {
		assertThrows(NullPointerException.class, ()-> {
			new Character(null, 1, 1, 1, 1, null, null, null, null, null, null, null, null, "Alignment",
					"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		});
	}
	
	@Test
	void testOverloadedCharacterConstructorAttribute() {
		Attributes charAttributes = new Attributes(-1, 2, 3, 4, 5, 6);
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");
		
		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		Inventory charInventory = new Inventory();
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, null, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		assertEquals(8, character.getCharacterAttributes().getStrength());
	}
}
