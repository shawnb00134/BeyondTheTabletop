package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Character class
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class Character {

private static final int MINIMUM_LEVEL = 1;
	
	private String playerOwnerID;
	
	private String characterName;
	private int characterLevel;
	private int characterArmorClass;
	private int characterMaxHealthPoints;
	private int characterCurrentHealthPoints;
	private Attributes characterAttributes;
	private boolean hasInspirationDie;
	
	private Role characterClass;
	private List<String> skillProficencies;
	private Inventory characterInventory;
	private List<String> characterInventoryByString;
	private List<String> weaponMasteries;
	private List<String> characterSpells;
	private List<String> characterFeats;
	
	private Race characterSpecies;
	private String alignment;
	private String faith;
	private String hair;
	private String skin;
	private String eyes;
	private String height;
	private String weight;
	private String age;
	private String gender;
	
	private String campaignNotes;
	
	/**
	 * Base constructor
	 */
	public Character() {
		this.characterName = "";
		this.characterLevel = MINIMUM_LEVEL;
		this.characterArmorClass = 0;
		this.characterMaxHealthPoints = MINIMUM_LEVEL;
		this.characterCurrentHealthPoints = MINIMUM_LEVEL;
		this.characterAttributes = new Attributes();
		this.characterSpecies = null;
		this.hasInspirationDie = false;
		
		this.characterClass = null;
		this.skillProficencies = new ArrayList<String>();
		this.characterInventory = new Inventory();
		this.characterInventoryByString = new ArrayList<String>();
		this.weaponMasteries = new ArrayList<String>();
		this.characterSpells = new ArrayList<String>();
		this.characterFeats = new ArrayList<String>();
		
		this.characterSpecies = null;
		this.alignment = "";
		this.faith = "";
		this.hair = "";
		this.skin = "";
		this.eyes = "";
		this.height = "";
		this.weight = "";
		this.age = "";
		this.gender = "";
		
		this.campaignNotes = "";
		
		this.playerOwnerID = "";
	}
	
	/**
	 * Overloaded constructor
	 * 
	 * @param playerID the owner of the player
	 * @param name Character's name
	 * @param level the level of the character
	 * @param armorClass the armor rating of the character
	 * @param maxHP the max HP of the character
	 * @param currentHP the current HP of the character
	 * @param attributes Character's attributes
	 * @param role the character's class
	 * @param inventory a list of item in the character's inventory
	 * @param weaponMasteries the weapons the character has mastered
	 * @param spells a list of spells the character knows
	 * @param species Character's species
	 * @param alignment the character's alignment
	 * @param faith the character's faith
	 * @param hair the description of the character's hair
	 * @param skin the description of the character's skin
	 * @param eyes the description of the character's eyes
	 * @param height the description of the character's height
	 * @param weight the description of the character's weight
	 * @param age the description of the character's age
	 * @param gender the gender of the character
	 * @param skillProficencies the skill proficencies of the character.
	 * @param feats the character's feats
	 * @param inspiration the character's inspiration
	 * @param notes the notes.
	 */
	public Character(String name, int level, int armorClass, int maxHP, int currentHP, Attributes attributes, 
			Role role, List<String> skillProficencies, Inventory inventory, List<String> weaponMasteries, List<String> spells, List<String> feats, Race species, String alignment,
			String faith, String hair, String skin, String eyes, String height, String weight, String age, String gender, boolean inspiration, String notes, String playerID) {
		if (name == null) {
			throw new NullPointerException();
		} else if (name.isBlank()) {
			throw new IllegalArgumentException();
		} else {
			this.characterName = name;
		}
		
		this.characterLevel = level;
		this.characterArmorClass = armorClass;
		this.characterMaxHealthPoints = maxHP;
		this.characterCurrentHealthPoints = currentHP;
		this.setCharacterAttributes(attributes);
		this.characterClass = role;
		this.skillProficencies = skillProficencies;
		this.characterInventory = inventory;
		this.weaponMasteries = weaponMasteries;
		this.characterSpells = spells;
		this.characterFeats = feats;
		
		this.characterSpecies = species;
		this.alignment = alignment;
		this.faith = faith;
		this.hair = hair;
		this.skin = skin;
		this.eyes = eyes;
		this.height = height;
		this.weight = weight;
		this.age = age;
		this.gender = gender;
		this.hasInspirationDie = inspiration;
		
		this.campaignNotes = notes;
		
		this.playerOwnerID = playerID;
	}
	
	/**
	 * Heals the character by adding health
	 * 
	 * @param healingValue the amount to add to the character's current health points
	 * 
	 * @return true if healingValue > 0, else false
	 */
	public boolean healCharacter(int healingValue) {
		if (healingValue > 0 && this.characterCurrentHealthPoints + healingValue <= this.characterMaxHealthPoints) {
			this.characterCurrentHealthPoints += healingValue;
			return true;
		}
		
		if (healingValue > 0) {
			this.characterCurrentHealthPoints = this.characterMaxHealthPoints;
			return true;
		}
		return false;
	}
	
	/**
	 * Damages the character by removing health
	 * 
	 * @param damageValue the amount to hurt the character
	 * 
	 * @return true if damageValue > 0 && characterCurrentHealthPoints - damageValue >= 0, else false
	 */
	public boolean damageCharacter(int damageValue) {
		if (damageValue > 0 && this.characterCurrentHealthPoints - damageValue >= 0) {
			this.characterCurrentHealthPoints -= damageValue;
			return true;
		}
		if (damageValue > 0) {
			this.characterCurrentHealthPoints = 0;
			return true;
		}
		return false;
	}
	
	//******************* SETTERS *******************
	/**
	 * Sets the character's name
	 * 
	 * @param name the character's name
	 * 
	 * @pre !name.isBlank()
	 * @post this.characterName = name
	 * 
	 * @return true if !name.isBlank, else false
	 */
	public boolean setCharacterName(String name) {
		if (name != null && !name.isBlank()) {
			this.characterName = name;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's level
	 * 
	 * @param charLevel the level to set the character to
	 * 
	 * @return true if charLevel > 0, else false
	 */
	public boolean setCharacterLevel(int charLevel) {
		if (charLevel > 0) {
			this.characterLevel = charLevel;
			return true;
		}
		return false;		
	}
	
	/**
	 * Sets the Maximum health points of the character to a custom value
	 * 
	 * @param healthPoints the health point value to set for the max health points
	 * 
	 * @return true if healthPoints > 0, else false
	 */
	public boolean setCharacterMaxHealthPoints(int healthPoints) {
		if (healthPoints > 0) {
			this.characterMaxHealthPoints = healthPoints;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's current health point status
	 * 
	 * @param healthPoints the value to set the character's health to
	 * 
	 * @return true if healthPoints >= 0, else false
	 */
	public boolean setCharacterCurrentHealthPoints(int healthPoints) {
		if (healthPoints >= 0) {
			this.characterCurrentHealthPoints = healthPoints;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the Armor Class value for the character
	 * 
	 * @param armorClassValue the armor rating which the character has to be hit
	 * 
	 * @return true if armorClassValue > 0 else false
	 */
	public boolean setArmorClass(int armorClassValue) {
		if (armorClassValue > 0) {
			this.characterArmorClass = armorClassValue;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the characters attributes
	 * 
	 * @param attributes the numerical values of the characters attributes
	 * 
	 * @pre attributes != null
	 * @post this.characterAttributes = attributes
	 * 
	 * @return true if attributes != null, else false
	 */
	public boolean setCharacterAttributes(Attributes attributes) {
		if (attributes != null) {
			this.characterAttributes = attributes;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the spell list of the player
	 * 
	 * @param spellList the list of spells the player has if any
	 * 
	 * @return true if spellList != null else false
	 */
	public boolean setSpellsList(List<String> spellList) {
		if (spellList != null) {
			this.characterSpells = spellList;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the weapon masteries for the character if any
	 * 
	 * @param masteries a list of weapons the character has mastered
	 * 
	 * @return true if masteries != null else false
	 */
	public boolean setWeaponMasteries(List<String> masteries) {
		if (masteries != null) {
			this.weaponMasteries = masteries;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the feats for the character if any
	 * 
	 * @param feats a list of feats
	 * 
	 * @return true if feats != null else false
	 */
	public boolean setCharacterFeats(List<String> feats) {
		if (feats != null) {
			this.characterFeats = feats;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's Species/Race
	 * 
	 * @param species the character's race
	 * 
	 * @pre species != null
	 * @post this.characterSPecies = species
	 * 
	 * @return true if species != null, else false
	 */
	public boolean setCharacterSpecies(Race species) {
		if (species != null) {
			this.characterSpecies = species;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's class
	 * 
	 * @param role the character's class
	 * 
	 * @pre role != null
	 * @post this.characterClass = role
	 * 
	 * @return true if role != null, else false
	 */
	public boolean setCharacterClass(Role role) {
		if (role != null) {
			this.characterClass = role;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's inventory
	 * 
	 * @param inventory current inventory
	 * 
	 * @pre inventory != null
	 * @post this.characterInventory = inventory
	 * 
	 * @return true if inventory != null, else false
	 */
	public boolean setInventory(Inventory inventory) {
		if (inventory != null) {
			this.characterInventory = inventory;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets an alternative inventory by string instead of Inventory
	 * 
	 * @param inventory a list of strings of items
	 * 
	 * @return true if inventory != null else false
	 */
	public boolean setInventoryByString(List<String> inventory) {
		if (inventory != null) {
			this.characterInventoryByString = inventory;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's alignment status if any
	 * 
	 * @param alignment the character's good to evil alignment
	 * 
	 * @return true if alignment != null else false
	 */
	public boolean setCharacterAlignment(String alignment) {
		if (alignment != null) {
			this.alignment = alignment;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's faith if any
	 * 
	 * @param faith the faith of the character
	 * 
	 * @return true if faith != null else false
	 */
	public boolean setCharacterFaith(String faith) {
		if (faith != null) {
			this.faith = faith;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's chair. Can be anything
	 * 
	 * @param hair the color of the character's hair
	 * 
	 * @return true if hair != null else false
	 */
	public boolean setCharacterHair(String hair) {
		if (hair != null) {
			this.hair = hair;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the character's skin description
	 * 
	 * @param skin the description of the character's skin
	 * 
	 * @return true if skin != null else false
	 */
	public boolean setCharacterSkin(String skin) {
		if (skin != null) {
			this.skin = skin;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the description of the character's eyes
	 * 
	 * @param eyes the description of the character's eyes
	 * 
	 * @return true if eyes != null else false
	 */
	public boolean setCharacterEyes(String eyes) {
		if (eyes != null) {
			this.eyes = eyes;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the description of the character's height
	 * 
	 * @param height the description or number value of the character's height
	 * 
	 * @return true if height != null else false
	 */
	public boolean setCharacterHeight(String height) {
		if (height != null) {
			this.height = height;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the description of the character's weight
	 * 
	 * @param weight the description or number value of the character's weight
	 * 
	 * @return true if weight != null else false
	 */
	public boolean setCharacterWeight(String weight) {
		if (weight != null) {
			this.weight = weight;
			return true;
		}
		return false;
	}
	
	/**
	 * Set the charater's age description
	 * 
	 * @param age the description or number value of the character's age
	 * 
	 * @return true if age != null else false
	 */
	public boolean setCharacterAge(String age) {
		if (age != null) {
			this.age = age;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the chacater's gender
	 * 
	 * @param gender the gender of the character
	 * 
	 * @return true if gender != null else false
	 */
	public boolean setCharacterGender(String gender) {
		if (gender != null) {
			this.gender = gender;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the value of the boolean if the character has an inspiration die.
	 * 
	 * @param inspirationBool true if awarded inspiration, false if no inspiration die
	 */
	public void setHasInspirationDie(boolean inspirationBool) {
		this.hasInspirationDie = inspirationBool;
	}
	
	/**
	 * Sets the list of skills the character is proficient at
	 * 
	 * @param skills the list  of skills
	 * 
	 * @return true if skills != null else false 
	 */
	public boolean setSkillProficencies(List<String> skills) {
		if (skills != null) {
			this.skillProficencies = skills;
			return true;
		}
		return false;
	}
	
	/**
	 * Sets the creating player as the owner of the character for filtering
	 * 
	 * @param playerID the player ID of the owner of the character
	 * 
	 * @return true if playerID != null && !playerID.isEmpty
	 */
	public boolean setPlayerOwnership(String playerID) {
		if (playerID == null) {
			throw new NullPointerException("playerID cannot be null.");
		} else if (playerID.isEmpty()) {
			throw new IllegalArgumentException("playerID cannot be empty/blank.");
		}
		this.playerOwnerID = playerID;
		return true;
	}
	
	/**
	 * Sets the campaign notes for the Character
	 * 
	 * @param notes the notes to set
	 * @return whether or not notes were set.
	 */
	public boolean setCharacterCampaignNotes(String notes) {
		if (notes != null && !notes.isEmpty()) {
			this.campaignNotes = notes;
			return true;
		}
		return false;
	}
	
	//******************* GETTERS *******************
	/**
	 * Returns the character's name
	 * 
	 * @return characterName
	 */
	public String getCharacterName() {
		return this.characterName;
	}
	
	/**
	 * Returns the current level of the character
	 * 
	 * @return characterLevel
	 */
	public int getCharacterLevel() {
		return this.characterLevel;
	}
	
	/**
	 * Returns the character's current Maximum health points
	 * 
	 * @return characterMaxHealthPoints
	 */
	public int getCharacterMaxHealthPoints() {
		return this.characterMaxHealthPoints;
	}
	
	/**
	 * Returns the character's current health status
	 * 
	 * @return characterCurrentHealthPoints
	 */
	public int getCharacterCurrentHealthPoints() {
		return this.characterCurrentHealthPoints;
	}
	
	/**
	 * Returns the characters armor class value
	 * 
	 * @return characterArmorClass
	 */
	public int getCharacterArmorClass() {
		return this.characterArmorClass;
	}
	
	/**
	 * Returns the character's species/race
	 * 
	 * @return characterAttributes
	 */
	public Attributes getCharacterAttributes() {
		return this.characterAttributes;
	}
	
	/**
	 * Returns the list of feats the character has
	 * 
	 * @return characterFeats
	 */
	public List<String> getCharacterFeats() {
		return this.characterFeats;
	}
	
	/**
	 * Returns a list of spells the character has
	 * 
	 * @return characterSpells
	 */
	public List<String> getCharacterSpells() {
		return this.characterSpells;
	}
	
	/**
	 * Returns the character's species/race
	 * 
	 * @return characterSpeices
	 */
	public Race getCharacterSpecies() {
		return this.characterSpecies;
	}
	
	/**
	 * Returns the character's class
	 * 
	 * @return characterClass
	 */
	public Role getCharacterClass() {
		return this.characterClass;
	}
	
	/**
	 * Returns the entire character inventory.
	 * 
	 * @return this.characterInventory
	 */
	public Inventory getCharacterInventory() {
		return this.characterInventory;
	}
	
	/**
	 * Returns the character's alignment
	 * 
	 * @return alignment
	 */
	public String getCharacterAlignment() {
		return this.alignment;
	}
	
	/**
	 * Returns the character's faith
	 * 
	 * @return faith
	 */
	public String getCharacterFaith() {
		return this.faith;
	}
	
	/**
	 * Returns the character's hair description
	 * 
	 * @return hair
	 */
	public String getCharacterHair() {
		return this.hair;
	}
	
	/**
	 * Returns the character's skin description
	 * 
	 * @return skin
	 */
	public String getCharacterSkin() {
		return this.skin;
	}
	
	/**
	 * Returns the character's eye description
	 * 
	 * @return eyes
	 */
	public String getCharacterEyes() {
		return this.eyes;
	}
	
	/**
	 * Returns the characters height description
	 * 
	 * @return height
	 */
	public String getCharacterHeight() {
		return this.height;
	}
	
	/**
	 * Returns the character's weight description
	 * 
	 * @return weight
	 */
	public String getCharacterWeight() {
		return this.weight;
	}
	
	/**
	 * Returns the character's age
	 * 
	 * @return age
	 */
	public String getCharacterAge() {
		return this.age;
	}
	
	/**
	 * Returns the gender of the character
	 * 
	 * @return gender
	 */
	public String getCharacterGender() {
		return this.gender;
	}
	
	/**
	 * Returns a list of weapons the player has mastered
	 * 
	 * @return weaponMasteries
	 */
	public List<String> getWeaponMasteries() {
		return this.weaponMasteries;
	}
	
	/**
	 * Returns the boolean value if the player has been awarded an inspiration die or none.
	 * 
	 * @return true if hasInspiration, else false
	 */
	public boolean hasInspiration() {
		return this.hasInspirationDie;
	}
	
	/**
	 * Returns a list of items in the character's inventory as a list of strings
	 * 
	 * @return characterInventoryByString
	 */
	public List<String> getInventoryByString() {
		return this.characterInventoryByString;
	}
	
	/**
	 * Returns the list of skills the character has
	 * 
	 * @return skillProficencies
	 */
	public List<String> getSkillProficiencies() {
		return this.skillProficencies;
	}
	
	/**
	 * Returns the notes the player has on the campaign for the character.
	 * 
	 * @return campaignNotes
	 */
	public String getCharacterCampaignNotes() {
		return this.campaignNotes;
	}
	
	/**
	 * Gets the player who owns the character
	 * 
	 * @return playerOwnerID
	 */
	public String getPlayerOwnerID() {
		return this.playerOwnerID;
	}
	
	@Override
	public String toString() {
		return this.characterName;
	}
}
