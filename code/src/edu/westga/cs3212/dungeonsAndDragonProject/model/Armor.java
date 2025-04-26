package edu.westga.cs3212.dungeonsAndDragonProject.model;

/**
 * Armor class that extends Item class
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Armor extends Item {

	private static final String LIGHTARMOR = "Light";
	private static final String MEDIUMARMOR = "Medium";
	private static final String HEAVYARMOR = "Heavy";
	
	private String armorType;
	private int armorClass;
	private int strengthRequirement;
	private boolean stealthDisadvantage;
	private int armorWeight;
	
	/**
	 * Armor constructor
	 * 
	 * @param armorName name of the armor
	 * @param armorType type of armor, Light, Medium, Heavy
	 * @param armorClass the boost to armor
	 * @param strengthRequirement any required strength attribute
	 * @param stealthDisadvantage does armor cause disadvantage to stealth checks
	 * @param armorWeight weight of the armor
	 * @param armorCost cost of the armor
	 * @param armorNotes armor flavor text
	 */
	public Armor(String armorName, String armorType, int armorClass, int strengthRequirement, 
			boolean stealthDisadvantage, int armorWeight, int armorCost, String armorNotes) {
		super(armorName, armorCost, armorNotes);
		
		if (armorType == null) {
			throw new NullPointerException("Armor type cannot be null.");
		} else if (armorType.equals(LIGHTARMOR) || armorType.equals(MEDIUMARMOR) || armorType.equals(HEAVYARMOR)) {
			this.armorType = armorType;
		} else {
			throw new IllegalArgumentException("Armor type must be Light, Medium, or Heavy.");
		}
		
		if (armorClass < 0) {
			throw new IllegalArgumentException("Armor class must be 0 or greater.");
		} else {
			this.armorClass = armorClass;
		}
		
		if (strengthRequirement < 0) {
			throw new IllegalArgumentException("Armor strength requirements must be 0 or greater.");
		} else {
			this.strengthRequirement = strengthRequirement;
		}
		
		this.stealthDisadvantage = stealthDisadvantage;
		
		if (armorWeight < 0) {
			throw new IllegalArgumentException("Armor weight cannot be less than 0.");
		} else {
			this.armorWeight = armorWeight;
		}
	}
	
	/**
	 * Returns the armor type, Light, Medium, or Heavy
	 * 
	 * @return armorType
	 */
	public String getArmorType() {
		return this.armorType;
	}
	
	/**
	 * Returns the armor class
	 * 
	 * @return armorClass
	 */
	public int getArmorClass() {
		return this.armorClass;
	}
	
	/**
	 * Returns the level of strength required to wear the armor
	 * 
	 * @return strengthRequirement
	 */
	public int getArmorStrengthRequirement() {
		return this.strengthRequirement;
	}
	
	/**
	 * Returns if the armor gives the character disadvantage on stealth checks.
	 * 
	 * @return stealthDisadvange
	 */
	public boolean getStealthDisadvantage() {
		return this.stealthDisadvantage;
	}
	
	/**
	 * Returns the weight of the armor
	 * 
	 * @return armorWeight
	 */
	public int getArmorWeight() {
		return this.armorWeight;
	}
}
