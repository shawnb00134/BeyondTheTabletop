package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Inventory class to hold a character's items.
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Inventory {

	private static final int CAPACITY_MODIFIER = 15;
	
	private List<Item> characterInventory;
	private int inventoryCapacity;
	private int currentInventoryWeight;
	private int characterCoinPurse;
	
	/**
	 * Base Inventory constructor;
	 */
	public Inventory() {
		this.characterInventory = new ArrayList<Item>();
		this.inventoryCapacity = Integer.MIN_VALUE;
		this.currentInventoryWeight = 0;
		this.characterCoinPurse = 0;
		
	}
	
	/**
	 * Overloaded constructor to include if encumbrance  enabled.
	 * 
	 * @param charStrength the character's current strength
	 * 
	 * @pre charStrength > 0
	 * @post none
	 */
	public Inventory(int charStrength) {
		this.characterInventory = new ArrayList<Item>();
		
		if (charStrength > 0) {
			this.inventoryCapacity = charStrength * CAPACITY_MODIFIER;
		}
		
		this.currentInventoryWeight = 0;
		this.characterCoinPurse = 0;
	}
	
	/**
	 * Add item to character inventory
	 *  
	 * @param item the item to add to the inventory
	 * 
	 * @pre item != null
	 * @post characterInventory.size() + 1
	 * 
	 * @return true if item != null, else false
	 */
	public boolean addItemToInventory(Item item) {
		if (item != null) {
			this.characterInventory.add(item);
			
			if (item instanceof Weapon) {
				this.currentInventoryWeight += ((Weapon) item).getWeaponWeight();
			}
			if (item instanceof Armor) {
				this.currentInventoryWeight += ((Armor) item).getArmorWeight();
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * Removes an item from the character's inventory
	 * 
	 * @param item the selected item to remove
	 * 
	 * @pre item != null && characterInventory.contains(item)
	 * @post characterInventory.size() - 1
	 * 
	 * @return true if item != null and characterInventory.contains(item), else false
	 */
	public boolean removeItemFromInventory(Item item) {
		if (item != null && this.characterInventory.contains(item)) {
			this.characterInventory.remove(item);
			
			if (item instanceof Weapon) {
				this.currentInventoryWeight -= ((Weapon) item).getWeaponWeight();
			}
			if (item instanceof Armor) {
				this.currentInventoryWeight -= ((Armor) item).getArmorWeight();
			}
			
			return true;
		}
		return false;
	}
	
	/**
	 * Adds items to the character inventory.
	 * 
	 * @param listOfItems a list of item to add to the inventory
	 * 
	 * @pre listOfItem != null and !listOfItem.isEmpty()
	 * @post characterInventory.size() + listOfItem.size()
	 * 
	 * @return true if listOfItems != null && !listOfItem.isEmpty()
	 */
	public boolean setListOfItems(List<Item> listOfItems) {
		if (listOfItems != null && !listOfItems.isEmpty()) {
			for (Item item : listOfItems) {
				this.addItemToInventory(item);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a list of all of the items currently in the character's inventory
	 * 
	 * @return characterInventory
	 */
	public List<Item> getInventoryList() {
		return this.characterInventory;
	}
	
	/**
	 * Returns the current weight limit of the character's inventory
	 * 
	 * @return inventoryCapacity
	 */
	public int getInventoryCapacityLimit() {
		return this.inventoryCapacity;
	}
	
	/**
	 * Returns the current weight of the character's inventory
	 * 
	 * @return currentInventoryWeight
	 */
	public int getCurrentInventoryWeight() {
		return this.currentInventoryWeight;
	}
	
	/**
	 * Adds coin from the character's purse
	 * 
	 * @param coin the amount to add to the coin purse
	 * 
	 * @pre none
	 * @post characterCoinPurse += coin
	 * 
	 * @return true if characterCointPurse - coin < 0, else false
	 */
	public boolean addCoinToPurse(int coin) {
		if (coin >= 0) {
			this.characterCoinPurse += coin;
			return true;
		}
		return false;
	}
	
	/**
	 * Removes coin from the character's purse
	 * 
	 * @param coin the amount to remove from the coin purse
	 * 
	 * @return true if characterCoinPurse >= 0, else false
	 */
	public boolean removeCoinFromPurse(int coin) {
		if (this.characterCoinPurse - coin >= 0) {
			this.characterCoinPurse -= coin;
			return true;
		}
		return false;
	}
	
	/**
	 * The current amount in the character's coin purse
	 * 
	 * @return characterCoinPurse
	 */
	public int getCharacterCoinPurse() {
		return this.characterCoinPurse;
	}
}
