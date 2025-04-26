package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Armor;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Item;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Tools;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Weapon;

public class TestInventory {

	@Test
	void testBaseConstructor() {
		Inventory characterInventory = new Inventory();
		
		assertTrue(characterInventory.getInventoryList().isEmpty());
		assertEquals(Integer.MIN_VALUE, characterInventory.getInventoryCapacityLimit());
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
		assertEquals(0, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testConstructorWithCharacterStrength() {
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.getInventoryList().isEmpty());
		assertEquals(150, characterInventory.getInventoryCapacityLimit());
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
		assertEquals(0, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testAddWeapon() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(weapon));
		assertEquals(weapon.getWeaponWeight(), characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testAddArmor() {
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(armor));
		assertEquals(armor.getArmorWeight(), characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testAddTool() {
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(tool));
	}
	
	@Test
	void testAddNullItem() {
		Item item = null;
		Inventory characterInventory = new Inventory(10);
		
		assertFalse(characterInventory.addItemToInventory(item));
	}
	
	@Test
	void testAddWeaponArmorTool() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(weapon));
		assertTrue(characterInventory.addItemToInventory(armor));
		assertTrue(characterInventory.addItemToInventory(tool));
	}
	
	@Test
	void testRemoveWeapon() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(weapon));
		assertTrue(characterInventory.removeItemFromInventory(weapon));
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testRemoveArmor() {
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(armor));
		assertTrue(characterInventory.removeItemFromInventory(armor));
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testRemoveTool() {
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(tool));
		assertTrue(characterInventory.removeItemFromInventory(tool));
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testRemoveNullItem() {
		Item item = null;
		Inventory characterInventory = new Inventory(10);
		
		assertFalse(characterInventory.removeItemFromInventory(item));
	}
	
	@Test
	void testRemoveWeaponArmorTool() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		Inventory characterInventory = new Inventory(10);
		
		int weight = weapon.getWeaponWeight() + armor.getArmorWeight();
		
		assertTrue(characterInventory.addItemToInventory(weapon));
		assertTrue(characterInventory.addItemToInventory(armor));
		assertTrue(characterInventory.addItemToInventory(tool));
		
		assertEquals(weight, characterInventory.getCurrentInventoryWeight());
		
		assertTrue(characterInventory.removeItemFromInventory(weapon));
		assertTrue(characterInventory.removeItemFromInventory(armor));
		assertTrue(characterInventory.removeItemFromInventory(tool));
		
		assertEquals(0, characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testAddPositiveGoldToCoinPurse() {
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addCoinToPurse(10));
		assertEquals(10, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testAddZeroGoldToCoinPurse() {
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addCoinToPurse(0));
		assertEquals(0, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testAddNegativeGoldToCoinPurse() {
		Inventory characterInventory = new Inventory(10);
		
		assertFalse(characterInventory.addCoinToPurse(-1));
	}
	
	@Test
	void testRemoveGoldFromPurseValid() {
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addCoinToPurse(50));
		assertTrue(characterInventory.removeCoinFromPurse(10));
		assertEquals(40, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testRemoveGoldFromPurseInvalid() {
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addCoinToPurse(50));
		assertFalse(characterInventory.removeCoinFromPurse(60));
		assertEquals(50, characterInventory.getCharacterCoinPurse());
	}
	
	@Test
	void testSetInventoryList() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		
		int weight = weapon.getWeaponWeight() + armor.getArmorWeight();
		
		List<Item> listOfItems = new ArrayList<Item>();
		listOfItems.add(weapon);
		listOfItems.add(armor);
		listOfItems.add(tool);
		
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.setListOfItems(listOfItems));
		assertEquals(weight, characterInventory.getCurrentInventoryWeight());
		
	}
	
	@Test
	void testSetInventoryListNull() {
		List<Item> listOfItems = null;
		Inventory characterInventory = new Inventory(10);
		
		assertFalse(characterInventory.setListOfItems(listOfItems));
	}
	
	@Test
	void testGetInventoryListAddedAsList() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		
		int weight = weapon.getWeaponWeight() + armor.getArmorWeight();
		
		List<Item> listOfItems = new ArrayList<Item>();
		listOfItems.add(weapon);
		listOfItems.add(armor);
		listOfItems.add(tool);
		
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.setListOfItems(listOfItems));
		assertEquals(listOfItems, characterInventory.getInventoryList());
		assertEquals(weight, characterInventory.getCurrentInventoryWeight());
	}
	
	@Test
	void testGetInventoryListAddedIndividually() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		
		int weight = weapon.getWeaponWeight() + armor.getArmorWeight();
		
		List<Item> listOfItems = new ArrayList<Item>();
		listOfItems.add(weapon);
		listOfItems.add(armor);
		listOfItems.add(tool);
		
		Inventory characterInventory = new Inventory(10);
		
		assertTrue(characterInventory.addItemToInventory(weapon));
		assertTrue(characterInventory.addItemToInventory(armor));
		assertTrue(characterInventory.addItemToInventory(tool));
		assertEquals(listOfItems, characterInventory.getInventoryList());
		assertEquals(weight, characterInventory.getCurrentInventoryWeight());		
	}
}
