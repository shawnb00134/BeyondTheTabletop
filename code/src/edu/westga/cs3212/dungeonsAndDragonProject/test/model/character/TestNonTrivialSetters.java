package edu.westga.cs3212.dungeonsAndDragonProject.test.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class TestNonTrivialSetters {
	
//	@Test
//	void testSetCharacterName() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterName("John Doe"));
//		assertEquals("John Doe", character.getCharacterName());
//	}
//	
//	@Test
//	void testSetCharacternameBlank() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterName(""));
//	}
//	
//	@Test
//	void testSetCharacterNameNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterName(null));
//	}
//	
//	@Test
//	void testSetCharacterAttributesNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterAttributes(null));
//	}
//	
//	@Test
//	void testSetCharacterSpecies() {
//		Character character = new Character();
//		Race charRace = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
//				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
//		
//		assertTrue(character.setCharacterSpecies(charRace));
//		assertEquals(charRace, character.getCharacterSpecies());
//	}
//	
//	@Test
//	void testSetCharacterSpeciesNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterSpecies(null));
//	}
//	
//	@Test
//	void testSetCharacterInventory() {
//		Character character = new Character();
//		Inventory charInventory = new Inventory();
//		
//		assertTrue(character.setInventory(charInventory));
//	}
//	
//	@Test
//	void testSetCharacterInventoryNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setInventory(null));
//	}
//	
//	@Test
//	void testSetCharacterClass() {
//		Character character = new Character();
//		
//		Set<String> featureSet = new HashSet<>();
//		featureSet.add("feature1");
//		
//		Set<String> proficiencySet = new HashSet<>();
//		proficiencySet.add("Animal Handling");
//		
//		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
//		
//		assertTrue(character.setCharacterClass(charClass));
//	}
//	
//	@Test
//	void testSetCharacterClassNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterClass(null));
//	}
//	
//	@Test
//	void testCharacterHealthvalid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterMaxHealthPoints(20));
//		assertTrue(character.setCharacterCurrentHealthPoints(10));
//		assertTrue(character.damageCharacter(8));
//		assertTrue(character.healCharacter(15));
//		
//		assertEquals(20, character.getCharacterMaxHealthPoints());
//		assertEquals(17, character.getCharacterCurrentHealthPoints());
//	}
//	
//	@Test
//	void testCharacterHealthInvalid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterMaxHealthPoints(20));
//		assertTrue(character.setCharacterCurrentHealthPoints(10));
//		assertTrue(character.setCharacterCurrentHealthPoints(0));
//		
//		assertFalse(character.damageCharacter(-1));
//		assertFalse(character.healCharacter(-1));
//		assertFalse(character.setCharacterMaxHealthPoints(0));
//		assertFalse(character.setCharacterMaxHealthPoints(-1));
//		assertFalse(character.setCharacterCurrentHealthPoints(-1));
//	}
//	
//	@Test
//	void testCharacterHealthZeroOut() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterMaxHealthPoints(20));
//		assertTrue(character.setCharacterCurrentHealthPoints(10));
//		
//		assertTrue(character.damageCharacter(30));
//		assertEquals(0, character.getCharacterCurrentHealthPoints());
//	}
//	
//	@Test
//	void testCharacterLevelValid() {
//		Character character = new Character();
//		
//		assertEquals(1, character.getCharacterLevel());
//		
//		assertTrue(character.setCharacterLevel(5));
//		assertEquals(5, character.getCharacterLevel());
//	}
//	
//	@Test
//	void testCharacterLevelInvalid() {
//		Character character = new Character();
//		assertFalse(character.setCharacterLevel(-1));
//		assertFalse(character.setCharacterLevel(0));
//	}
//	
//	@Test
//	void testArmorClassValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setArmorClass(1));
//		assertEquals(1, character.getCharacterArmorClass());
//	}
//	
//	@Test
//	void testSettingArmorClassFail() {
//		Character character = new Character();
//		
//		assertFalse(character.setArmorClass(0));
//	}
//	
//	@Test
//	void testCharacterFaithValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterFaith("Dragon God"));
//		assertEquals("Dragon God", character.getCharacterFaith());
//	}
//	
//	@Test
//	void testCharacterFaithBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterFaith(""));
//		assertEquals("", character.getCharacterFaith());
//	}
//	
//	@Test
//	void testCharacterFaithNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterFaith(null));
//	}
//	
//	@Test
//	void testCharacterAlignmentValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterAlignment("Chaotic Evil"));
//		assertEquals("Chaotic Evil", character.getCharacterAlignment());
//	}
//	
//	@Test
//	void testCharacterAlignmentBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterAlignment(""));
//		assertEquals("", character.getCharacterAlignment());
//	}
//	
//	@Test
//	void testCharacterAlignmentNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterAlignment(null));
//	}
//	
//	@Test
//	void testCharacterHairValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterHair("Spiky Blue"));
//		assertEquals("Spiky Blue", character.getCharacterHair());
//	}
//	
//	@Test
//	void testCharacterHairBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterHair(""));
//		assertEquals("", character.getCharacterHair());
//	}
//	
//	@Test
//	void testCharacterHairNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterHair(null));
//	}
//	
//	@Test
//	void testCharacterSkinValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterSkin("Scaley green"));
//		assertEquals("Scaley green", character.getCharacterSkin());
//	}
//	
//	@Test
//	void testCharacterSkinBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterSkin(""));
//		assertEquals("", character.getCharacterSkin());
//	}
//	
//	@Test
//	void testCharacterSkinNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterSkin(null));
//	}
//	
//	@Test
//	void testCharacterEyesValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterEyes("Dark Blue"));
//		assertEquals("Dark Blue", character.getCharacterEyes());
//	}
//	
//	@Test
//	void testCharacterEyesBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterEyes(""));
//		assertEquals("", character.getCharacterEyes());
//	}
//	
//	@Test
//	void testCharacterEyesNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterEyes(null));
//	}
//	
//	@Test
//	void testCharacterHeightValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterHeight("Big"));
//		assertEquals("Big", character.getCharacterHeight());
//	}
//	
//	@Test
//	void testCharacterHeightBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterHeight(""));
//		assertEquals("", character.getCharacterHeight());
//	}
//	
//	@Test
//	void testCharacterHeightNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterHeight(null));
//	}
//	
//	@Test
//	void testCharacterWeightValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterWeight("Fat"));
//		assertEquals("Fat", character.getCharacterWeight());
//	}
//	
//	@Test
//	void testCharacterWeightBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterWeight(""));
//		assertEquals("", character.getCharacterWeight());
//	}
//	
//	@Test
//	void testCharacterWeightNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterWeight(null));
//	}
//	
//	@Test
//	void testCharacterAgeValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterAge("Old"));
//		assertEquals("Old", character.getCharacterAge());
//	}
//	
//	@Test
//	void testCharacterAgeBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterAge(""));
//		assertEquals("", character.getCharacterAge());
//	}
//	
//	@Test
//	void testCharacterAgeNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterAge(null));
//	}
//	
//	@Test
//	void testCharacterGenderValid() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterGender("Female"));
//		assertEquals("Female", character.getCharacterGender());
//	}
//	
//	@Test
//	void testCharacterGenderBlank() {
//		Character character = new Character();
//		
//		assertTrue(character.setCharacterGender(""));
//		assertEquals("", character.getCharacterGender());
//	}
//	
//	@Test
//	void testCharacterGenderNull() {
//		Character character = new Character();
//		
//		assertFalse(character.setCharacterGender(null));
//	}
//	
//	@Test
//	void testCharacterSpellsValid() {
//		Character character = new Character();
//		
//		List<String> spells = new ArrayList<String>();
//		spells.add("Fireball");
//		spells.add("Blast");
//		
//		assertTrue(character.setSpellsList(spells));
//		assertEquals(spells, character.getCharacterSpells());
//	}
//	
//	@Test
//	void testCharacterSpellsBlank() {
//		Character character = new Character();
//		
//		List<String> spells = new ArrayList<String>();
//		
//		assertTrue(character.setSpellsList(spells));
//		assertEquals(spells, character.getCharacterSpells());
//	}
//	
//	@Test
//	void testCharacterSpellsNull() {
//		Character character = new Character();
//		
//		List<String> spells = null;
//		
//		assertFalse(character.setSpellsList(spells));
//	}
//	
//	@Test
//	void testCharacterWeaponMasteriesValid() {
//		Character character = new Character();
//		
//		List<String> mastery = new ArrayList<String>();
//		mastery.add("Axe");
//		mastery.add("Club");
//		
//		assertTrue(character.setWeaponMasteries(mastery));
//		assertEquals(mastery, character.getWeaponMasteries());
//	}
//	
//	@Test
//	void testCharacterWeaponMasteriesBlank() {
//		Character character = new Character();
//		
//		List<String> mastery = new ArrayList<String>();
//		
//		assertTrue(character.setWeaponMasteries(mastery));
//		assertEquals(mastery, character.getWeaponMasteries());
//	}
//	
//	@Test
//	void testCharacterWeaponMasteriesNull() {
//		Character character = new Character();
//		
//		List<String> mastery = null;
//		
//		assertFalse(character.setWeaponMasteries(mastery));
//	}
//	
//	@Test
//	void testCharacterFeatsValid() {
//		Character character = new Character();
//		
//		List<String> feats = new ArrayList<String>();
//		feats.add("Running");
//		feats.add("Swimming");
//		
//		assertTrue(character.setCharacterFeats(feats));
//		assertEquals(feats, character.getCharacterFeats());
//	}
//	
//	@Test
//	void testCharacterFeatsBlank() {
//		Character character = new Character();
//		
//		List<String> feats = new ArrayList<String>();
//
//		assertTrue(character.setCharacterFeats(feats));
//		assertEquals(feats, character.getCharacterFeats());
//	}
//	
//	@Test
//	void testCharacterFeatsNull() {
//		Character character = new Character();
//		
//		List<String> feats = null;
//
//		assertFalse(character.setCharacterFeats(feats));
//	}
//	
//	@Test
//	void testSetInventoryByStringValid() {
//		Character character = new Character();
//		
//		List<String> inventory = new ArrayList<String>();
//		inventory.add("Axe");
//		inventory.add("Sword");
//		
//		assertTrue(character.setInventoryByString(inventory));
//		assertEquals(inventory, character.getInventoryByString());
//	}
//	
//	@Test
//	void testSetInventoryByStringBlank() {
//		Character character = new Character();
//		
//		List<String> inventory = new ArrayList<String>();
//		
//		assertTrue(character.setInventoryByString(inventory));
//		assertEquals(inventory, character.getInventoryByString());
//	}
//	
//	@Test
//	void testSetInventoryByStringNull() {
//		Character character = new Character();
//		
//		List<String> inventory = null;
//		
//		assertFalse(character.setInventoryByString(inventory));
//	}
	
	@Test
	void testSetGetPlayerID() {
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
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, null, charInventory, weaponMastery, spells, null, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "");
		
		assertTrue(character.setPlayerOwnership("playerID"));
		assertEquals("playerID", character.getPlayerOwnerID());
	}
	
	@Test
	void testSetPlayerIDNull() {
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
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, null, charInventory, weaponMastery, spells, null, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "",  "");
		
		assertThrows(NullPointerException.class, ()-> {
			character.setPlayerOwnership(null);
		});
	}
	
	@Test
	void testSetPlayerIDEmpty() {
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
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, null, charInventory, weaponMastery, spells, null, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "");
		
		assertThrows(IllegalArgumentException.class, ()-> {
			character.setPlayerOwnership("");
		});
	}
}
