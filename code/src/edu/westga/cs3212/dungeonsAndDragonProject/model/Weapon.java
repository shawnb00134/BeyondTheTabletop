package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.List;

/**
 * Weapon class that extends Item class
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Weapon extends Item {
	
	private String weaponDamage;
	private List<String> weaponProperties;
	private String weaponMastery;
	private int weaponWeight;

	/**
	 * Weapon constructor
	 * 
	 * @param weaponName the name of the weapon
	 * @param weaponDamage what die to roll for how much and the type of damage
	 * @param weaponProperties any properties the weapon may have
	 * @param weaponMastery types of status affects 
	 * @param weaponWeight the weight of the weapon
	 * @param weaponCost how much the weapon costs to buy
	 * @param weaponNotes any notes the weapon may have
	 */
	public Weapon(String weaponName, String weaponDamage, List<String> weaponProperties, String weaponMastery, 
			int weaponWeight, int weaponCost, String weaponNotes) {
		super(weaponName, weaponCost, weaponNotes);
		
		if (!weaponDamage.isBlank()) {
			this.weaponDamage = weaponDamage;
		} else {
			throw new IllegalArgumentException("WeaponDamage cannot be blank. Must fit a #d# and type of damage.");
		}
		
		if (weaponProperties != null) {
			this.weaponProperties = weaponProperties;
		} else {
			throw new IllegalArgumentException("WeaponProperties cannot be null. It may be empty.");
		}
		
		if (!weaponMastery.isBlank()) {
			this.weaponMastery = weaponMastery;
		} else {
			throw new IllegalArgumentException("WeaponMastery cannot be blank.");
		}
		
		if (weaponWeight >= 0) {
			this.weaponWeight = weaponWeight;
		} else {
			throw new IllegalArgumentException("WeaponWeight cannot be less than 0.");
		}
	}

	/**
	 * Returns the weapons damage in the format of #d# and type of damage (1d4 Piercing)
	 * 
	 * @return weaponDamage
	 */
	public String getWeaponDamage() {
		return this.weaponDamage;
	}
	
	/**
	 * Returns a list of the properties that the weapon has if any
	 * 
	 * @return weaponProperties
	 */
	public List<String> getWeaponProperties() {
		return this.weaponProperties;
	}
	
	/**
	 * Returns the weapon mastery affect
	 * 
	 * @return weaponMastery
	 */
	public String getWeaponMastery() {
		return this.weaponMastery;
	}
	
	/**
	 * Returns the weight of the weapon.
	 * 
	 * @return weaponWeight
	 */
	public int getWeaponWeight() {
		return this.weaponWeight;
	}
}
