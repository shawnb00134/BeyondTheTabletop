package edu.westga.cs3212.dungeonsAndDragonProject.test.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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

public class TestDamageAndHeal {

	@Test
	void testSingleDamageSingleHeal() {
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
		
		Character character = new Character("John", 1, 1, 10, 10, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		character.damageCharacter(1);
		assertEquals(9, character.getCharacterCurrentHealthPoints());
		
		character.healCharacter(1);
		assertEquals(10, character.getCharacterCurrentHealthPoints());
	}
	
	@Test
	void testCharacterTakesDamageGreaterThanCurrentHealth() {
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
		
		Character character = new Character("John", 1, 1, 10, 10, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		character.damageCharacter(11);
		assertEquals(0, character.getCharacterCurrentHealthPoints());
	}
	
	@Test
	void testCharacterHealsDamageGreaterThanMaxHealth() {
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
		
		Character character = new Character("John", 1, 1, 10, 10, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		character.healCharacter(11);
		assertEquals(10, character.getCharacterCurrentHealthPoints());
	}
	
	@Test
	void testCharacterTakesNegativeDamage() {
		Character character = new Character();
		
		assertFalse(character.damageCharacter(-2));
	}
	
	@Test
	void testCharacterHealsNegativeHealth() {
		Character character = new Character();
		
		assertFalse(character.healCharacter(-2));
	}
}
