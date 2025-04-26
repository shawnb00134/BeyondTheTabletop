package edu.westga.cs3212.dungeonsAndDragonProject.model;

/**
 * Abstract class for Item
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public abstract class Item {

	private String itemName;
	private int itemCost;
	private String itemNotes;
	
	/**
	 * Abstract item class for Weapon, Armor, and Tools
	 * 
	 * @param itemName the name of the item
	 * @param itemCost the cost of the item in GP (gold)
	 * @param itemNotes the flavor text of the item
	 */
	public Item(String itemName, int itemCost, String itemNotes) {
		if (itemName == null) {
			throw new NullPointerException("Item name cannot be null.");
		} else if (itemName.isBlank()) {
			throw new IllegalArgumentException("Item name cannot be blank.");
		} else {
			this.itemName = itemName; 
		}
		
		if (itemCost < 0) {
			throw new IllegalArgumentException("Item cost cannot be less than 0.");
		} else {
			this.itemCost = itemCost;
		}
		
		if (itemNotes == null) {
			throw new NullPointerException("Item notes cannot be null. It may be empty.");
		} else {
			this.itemNotes = itemNotes;
		}
	}
	
	/**
	 * Gets the name of the item
	 * 
	 * @return itemName
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Get the cost of the item
	 * 
	 * @return itemCost
	 */
	public int getItemCost() {
		return this.itemCost;
	}
	
	/**
	 * Get notes for the item
	 * 
	 * @return itemNotes
	 */
	public String getItemNotes() {
		return this.itemNotes;
	}
}
