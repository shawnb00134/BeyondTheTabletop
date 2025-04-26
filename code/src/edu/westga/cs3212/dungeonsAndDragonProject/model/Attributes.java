package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds and returns a character's attributes and bonus values.
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Attributes {

	private static final int BASELINE_STARTING = 8;
	private static final int BASELINE_NORMAL = 10;
	
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;
	
	/**
	 * Base constructor setting all attributes to BASELINE_STARTING
	 */
	public Attributes() {
		this.strength = BASELINE_STARTING;
		this.dexterity = BASELINE_STARTING;
		this.constitution = BASELINE_STARTING;
		this.intelligence = BASELINE_STARTING;
		this.wisdom = BASELINE_STARTING;
		this.charisma = BASELINE_STARTING;
	}
	
	/**
	 * Overloaded constructor to set the values of the character's attributes
	 * 
	 * @pre strength >= 0, dexterity >= 0, constitution >= 0, intelligence >= 0,
	 * 				wisdom >= 0, charisma >= 0
	 * 
	 * @post this.strength = strength, this.dexterity = dexterity, this.constitution = constitution,
	 * 				this.intelligence = intelligence, this.wisdom = wisdom, this.charisma = charisma
	 * 
	 * @param strength strength
	 * @param dexterity dexterity
	 * @param constitution constitution
	 * @param intelligence intelligence
	 * @param wisdom wisdom
	 * @param charisma charisma
	 */
	public Attributes(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
		if (strength >= 0) {
			this.strength = strength;			
		} else {
			this.strength = BASELINE_STARTING;
		}
	
		if (dexterity >= 0) {
			this.dexterity = dexterity;			
		} else {
			this.dexterity = BASELINE_STARTING;
		}
		
		if (constitution >= 0) {
			this.constitution = constitution;			
		} else {
			this.constitution = BASELINE_STARTING;
		}
		
		if (intelligence >= 0) {
			this.intelligence = intelligence;			
		} else {
			this.intelligence = BASELINE_STARTING;
		}
		
		if (wisdom >= 0) {
			this.wisdom = wisdom;			
		} else {
			this.wisdom = BASELINE_STARTING;
		}
		
		if (charisma >= 0) {
			this.charisma = charisma;			
		} else {
			this.charisma = BASELINE_STARTING;
		}
	}
	
	private int calculateBonuses(int attribute) {
		double value = (attribute - BASELINE_NORMAL) / 2;
		return (int) Math.floor(value);
	}
	
	//******************* SETTERS *******************
	/**
	 * Sets Strength to a specific value
	 * 
	 * @pre strength >= 0
	 * @post this.strength = strength
	 * 
	 * @param strength the Character's Strength value
	 * 
	 * @return true if strength is set, false if not
	 */
	public boolean setStrength(int strength) {
		if (strength >= 0) {
			this.strength = strength;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets Dexterity to a specific value
	 * 
	 * @pre dexterity >= 0
	 * @post this.dexterity = dexterity
	 * 
	 * @param dexterity the Character's Dexterity value
	 * 
	 * @return true if dexterity is set, false if not
	 */
	public boolean setDexterity(int dexterity) {
		if (dexterity >= 0) {
			this.dexterity = dexterity;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets Constitution to a specific value
	 * 
	 * @pre constitution >= 0
	 * @post this.constitution = constitution
	 * 
	 * @param constitution the Character's Constitution value
	 * 
	 * @return true if constitution is set, false if not
	 */
	public boolean setConstituion(int constitution) {
		if (constitution >= 0) {
			this.constitution = constitution;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets Intelligence to a specific value
	 * 
	 * @pre intelligence >= 0
	 * @post this.intelligence = intelligence
	 * 
	 * @param intelligence the Character's Intelligence value
	 * 
	 * @return true if intelligence is set, false if not
	 */
	public boolean setIntelligence(int intelligence) {
		if (intelligence >= 0) {
			this.intelligence = intelligence;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets Wisdom to a specific value
	 * 
	 * @pre wisdom >= 0
	 * @post this.wisdom = wisdom
	 * 
	 * @param wisdom the Character's Wisdom value
	 * 
	 * @return true if wisdom is set, false if not
	 */
	public boolean setWisdom(int wisdom) {
		if (wisdom >= 0) {
			this.wisdom = wisdom;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets Charisma to a specific value
	 * 
	 * @pre charisma >= 0
	 * @post this.charisma = charisma
	 * 
	 * @param charisma the Character's Charisma value
	 * 
	 * @return true if charisma is set, false if not
	 */
	public boolean setCharisma(int charisma) {
		if (charisma >= 0) {
			this.charisma = charisma;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets all attributes in one pass
	 * 
	 * 
	 * @param strength the Character's Strength value
	 * @param dexterity the Character's Dexterity value
	 * @param constitution the Character's Constitution value
	 * @param intelligence the Character's Intelligence value
	 * @param wisdom the Character's Wisdom value
	 * @param charisma the Character's Charisma value
	 * 
	 * @pre strength >= 0, dexterity >= 0, constitution >= 0, intelligence >= 0,
	 * 				wisdom >= 0, charisma >= 0
	 * 
	 * @post this.strength = strength, this.dexterity = dexterity, this.constitution = constitution,
	 * 				this.intelligence = intelligence, this.wisdom = wisdom, this.charisma = charisma
	 * 
	 * 
	 * @return true if all parameters are >= 0, false if not
	 */
	public boolean setAllAttributes(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
		if (strength >= 0 && dexterity >= 0 && constitution >= 0 && intelligence >= 0 
				&& wisdom >= 0 && charisma >= 0) {
			this.strength = strength;
			this.dexterity = dexterity;
			this.constitution = constitution;
			this.intelligence = intelligence;
			this.wisdom = wisdom;
			this.charisma = charisma;
			return true;
		}
		return false;
	}
	
	//******************* SETTERS *******************
	/**
	 * Returns Character's current Strength value
	 * @return Strength;
	 */
	public int getStrength() {
		return this.strength;
	}
	
	/**
	 * Returns Character's current Dexterity value
	 * @return Dexterity
	 */
	public int getDexterity() {
		return this.dexterity;
	}
	
	/**
	 * Returns Character's current Constitution value
	 * 
	 * @return Constitution
	 */
	public int getConstitution() {
		return this.constitution;
	}
	
	/**Returns Character's current Intelligence value
	 * 
	 * @return Intelligence
	 */
	public int getIntelligence() {
		return this.intelligence;
	}
	
	/**
	 * Returns Character's current Wisdom value
	 * 
	 * @return Wisdom
	 */
	public int getWisdom() {
		return this.wisdom;
	}
	
	/**
	 * Returns Character's current Charisma value
	 * 
	 * @return Charisma
	 */
	public int getCharisma() {
		return this.charisma;
	}
	
	/**
	 * Get Strength bonus value
	 * 
	 * @return strength bonus value
	 */
	public int getStrengthBonusValue() {
		return this.calculateBonuses(this.strength);
	}
	
	/**
	 * Gets the Dexterity bonus value
	 * 
	 * @return dexterity bonus value
	 */
	public int getDexterityBonusValue() {
		return this.calculateBonuses(this.dexterity);
	}
	
	/**
	 * Gets the Constitution bonus value
	 * 
	 * @return Constitution bonus value
	 */
	public int getConstitutionBonusValue() {
		return this.calculateBonuses(this.constitution);
	}
	
	/**
	 * Gets the Intelligence bonus value
	 * 
	 * @return Intelligence bonus value
	 */
	public int getIntelligenceBonusValue() {
		return this.calculateBonuses(this.intelligence);
	}
	
	/**
	 * Gets the Wisdom bonus value
	 * 
	 * @return Wisdom bonus value
	 */
	public int getWisdomBonusValue() {
		return this.calculateBonuses(this.wisdom);
	}
	
	/**
	 * Gets the Charisma bonus value
	 * 
	 * @return Charisma bonus value
	 */
	public int getCharismaBonusValue() {
		return this.calculateBonuses(this.charisma);
	}
	
	/**
	 * Returns all of Character's current Attribute values
	 * 
	 * @return allAttributes
	 */
	public List<Integer> getAllAttributes() {
		List<Integer> allAttributes = new ArrayList<Integer>();
		allAttributes.add(this.strength);
		allAttributes.add(this.dexterity);
		allAttributes.add(this.constitution);
		allAttributes.add(this.intelligence);
		allAttributes.add(this.wisdom);
		allAttributes.add(this.charisma);
		
		return allAttributes;
	}
	
	/**
	 * Gets a list of all of the bonus values for the Character
	 * 
	 * @return bonusValues
	 */
	public List<Integer> getAllBonusValues() {
		List<Integer> bonusValues = new ArrayList<Integer>();
		
		for (int attribute : this.getAllAttributes()) {
			bonusValues.add(this.calculateBonuses(attribute));
		}
		
		return bonusValues;
	}
	
}
