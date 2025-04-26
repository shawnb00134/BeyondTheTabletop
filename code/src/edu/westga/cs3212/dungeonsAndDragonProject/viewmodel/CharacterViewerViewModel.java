package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import edu.westga.cs3212.dungeonsAndDragonProject.view.CampaignManagerCodeBehind;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * The Character Sheet Viewer
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class CharacterViewerViewModel {

	private Character loadedCharacter;
	
	private StringProperty charName;
	private StringProperty charLevel;
	private StringProperty charRace;
	private StringProperty charClass;
	private StringProperty charStr;
	private StringProperty charStrBonus;
	private StringProperty charDex;
	private StringProperty charDexBonus;
	private StringProperty charCon;
	private StringProperty charConBonus;
	private StringProperty charInt;
	private StringProperty charIntBonus;
	private StringProperty charWis;
	private StringProperty charWisBonus;
	private StringProperty charCha;
	private StringProperty charChaBonus;
	private StringProperty charHealth;
	private StringProperty charAC;
	private StringProperty proficiencyBonus;
	private ListProperty<String> charAbilities;
	private ListProperty<String> charInventory;
	private ListProperty<String> charSpells;
	private ListProperty<String> charFeats;
	private ListProperty<String> charBackground;
	private BooleanProperty hasInspirationDie;
	private StringProperty healthModifier;
	private StringProperty coinPurse;
	private StringProperty coinModifier;
	private StringProperty characterCampaignNotes;
	
	/**
	 * Base constructor for the viewer
	 */
	public CharacterViewerViewModel() {
		this.loadedCharacter = SystemContextViewModel.getInstance().getCharacterSelection();
		
		this.charName = new SimpleStringProperty();
		this.charLevel = new SimpleStringProperty();
		this.charRace = new SimpleStringProperty();
		this.charClass = new SimpleStringProperty();
		this.charStr = new SimpleStringProperty();
		this.charStrBonus = new SimpleStringProperty();
		this.charDex = new SimpleStringProperty();
		this.charDexBonus = new SimpleStringProperty();
		this.charCon = new SimpleStringProperty();
		this.charConBonus = new SimpleStringProperty();
		this.charInt = new SimpleStringProperty();
		this.charIntBonus = new SimpleStringProperty();
		this.charWis = new SimpleStringProperty();
		this.charWisBonus = new SimpleStringProperty();
		this.charCha = new SimpleStringProperty();
		this.charChaBonus = new SimpleStringProperty();
		this.charHealth = new SimpleStringProperty();
		this.charAC = new SimpleStringProperty();
		this.proficiencyBonus = new SimpleStringProperty();
		this.charAbilities = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.charInventory = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.charSpells = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.charFeats = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.charBackground = new SimpleListProperty<String>(FXCollections.observableArrayList());
		this.hasInspirationDie = new SimpleBooleanProperty();
		this.healthModifier = new SimpleStringProperty();
		this.coinPurse = new SimpleStringProperty();
		this.coinModifier = new SimpleStringProperty();
		this.characterCampaignNotes = new SimpleStringProperty();
		
		this.setCharBasics();
		this.setCharAttrBonus();
		this.updateCharHealth();
		this.setSkillBonus();
		this.getAdditionalComponents();
		this.uploadLists();
		this.updateCoinPurse();
	}
	
	private void setCharBasics() {
		this.charName.setValue(this.loadedCharacter.getCharacterName());
		this.charLevel.setValue("Level: " + Integer.toString(this.loadedCharacter.getCharacterLevel()));
		this.charRace.setValue("Race: " + this.loadedCharacter.getCharacterSpecies());
		this.charClass.setValue("Class: " + this.loadedCharacter.getCharacterClass());
		this.charAC.setValue("AC: " + this.loadedCharacter.getCharacterArmorClass());
	}
	
	private void setCharAttrBonus() {
		this.charStr.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getStrength()));
		this.charStrBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getStrengthBonusValue()));
		this.charDex.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getDexterity()));
		this.charDexBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getDexterityBonusValue()));
		this.charCon.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getConstitution()));
		this.charConBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getConstitutionBonusValue()));
		this.charInt.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getIntelligence()));
		this.charIntBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue()));
		this.charWis.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getWisdom()));
		this.charWisBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue()));
		this.charCha.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getCharisma()));
		this.charChaBonus.setValue(Integer.toString(this.loadedCharacter.getCharacterAttributes().getCharismaBonusValue()));
	}
	
	private void updateCharHealth() {
		this.charHealth.setValue(Integer.toString(this.loadedCharacter.getCharacterCurrentHealthPoints()) + "/" 
				+ Integer.toString(this.loadedCharacter.getCharacterMaxHealthPoints()));
	}
	
	private void setSkillBonus() {
		this.proficiencyBonus.setValue(this.loadedCharacter.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getStrengthBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.loadedCharacter.getCharacterAttributes().getWisdomBonusValue());
	}
	
	/**
	 * Updates the status of the player having an inspiration die or not.
	 */
	public void updateHasInspiration() {
		if (this.loadedCharacter.hasInspiration()) {
			this.hasInspirationDie.setValue(false);
			this.loadedCharacter.setHasInspirationDie(false);
		} else {
			this.hasInspirationDie.setValue(true);
			this.loadedCharacter.setHasInspirationDie(true);
		}
	}
	
	/**
	 * Heals the player's HP by the amount entered in the textfield.
	 */
	public void healCharacter() {
		if (this.healthModifier.getValue() == null || this.healthModifier.getValue().isEmpty()) {
			return;
		}
		this.loadedCharacter.healCharacter(Integer.parseInt(this.healthModifier.getValue()));
		this.updateCharHealth();
		this.healthModifier.setValue("");
	}
	
	/**
	 * Damages the player's HP by the amount entered in the textfield.
	 */
	public void damageCharacter() {
		if (this.healthModifier.getValue() == null || this.healthModifier.getValue().isEmpty()) {
			return;
		}
		this.loadedCharacter.damageCharacter(Integer.parseInt(this.healthModifier.getValue()));
		this.updateCharHealth();
		this.healthModifier.setValue("");
	}
	
	private void uploadLists() {
		this.uploadAbilities();
		for (String item : this.loadedCharacter.getInventoryByString()) {
			this.charInventory.add(item);
		}
		for (String spell : this.loadedCharacter.getCharacterSpells()) {
			this.charSpells.add(spell);
		}
		for (String feat : this.loadedCharacter.getCharacterFeats()) {
			this.charFeats.add(feat);
		}
		this.uploadCharBackground();
	}
	
	private void uploadAbilities() {
		this.charAbilities.add("Class:\n");
		for (String ability : this.loadedCharacter.getCharacterClass().getFeatures()) {
			this.charAbilities.add("\t" + ability + "\n");
		}
		
		this.charAbilities.add("Race:\n");
		for (String ability : this.loadedCharacter.getCharacterSpecies().getTraits()) {
			this.charAbilities.add("\t" + ability + "\n");
		}
		
		this.charAbilities.add("Speed: " + this.loadedCharacter.getCharacterSpecies().getSpeed());
		
		this.charAbilities.add("Skill Proficiencies: ");
		for (String skill : this.loadedCharacter.getSkillProficiencies()) {
			this.charAbilities.add("\t" + skill + '\n');
		}
	}
	
	private void uploadCharBackground() {
		String alignment = "Alignment: " + this.loadedCharacter.getCharacterAlignment();
		String faith = "Faith: " + this.loadedCharacter.getCharacterFaith();
		String hair = "Hair: " + this.loadedCharacter.getCharacterHair();
		String eyes = "Eyes: " + this.loadedCharacter.getCharacterEyes();
		String skin = "Skin: " + this.loadedCharacter.getCharacterSkin();
		String height = "Height: " + this.loadedCharacter.getCharacterHeight();
		String weight = "Weight: " + this.loadedCharacter.getCharacterWeight();
		String age = "Age: " + this.loadedCharacter.getCharacterAge();
		String gender = "Gender: " + this.loadedCharacter.getCharacterGender();

		this.charBackground.add(alignment);
		this.charBackground.add(faith);
		this.charBackground.add(hair);
		this.charBackground.add(eyes);
		this.charBackground.add(skin);
		this.charBackground.add(height);
		this.charBackground.add(weight);
		this.charBackground.add(age);
		this.charBackground.add(gender);
	}
	
	private void getAdditionalComponents() {
		if (this.loadedCharacter.getCharacterCampaignNotes() != null && 
				!this.loadedCharacter.getCharacterCampaignNotes().isEmpty()) {
			this.characterCampaignNotes.setValue(this.loadedCharacter.getCharacterCampaignNotes());
		}
		
		if (this.loadedCharacter.hasInspiration()) {
			this.hasInspirationDie.setValue(this.loadedCharacter.hasInspiration());
		}
	}
	
	/**
	 * Adds coin to the character coin purse
	 */
	public void addCoinToPurse() {
		if (this.coinModifier != null && !this.coinModifier.getValue().isEmpty()) {
			this.loadedCharacter.getCharacterInventory().addCoinToPurse(Integer.parseInt(this.coinModifier.getValue()));
		}
		this.updateCoinPurse();
	}
	
	/**
	 * Removes coin from the character coin purse
	 */
	public void removeCoinFromPurse() {
		if (this.coinModifier != null && !this.coinModifier.getValue().isEmpty()) {
			this.loadedCharacter.getCharacterInventory().removeCoinFromPurse(Integer.parseInt(this.coinModifier.getValue()));
		}
		this.updateCoinPurse();
	}
	
	/**
	 * Updates the character's coin purse output
	 */
	public void updateCoinPurse() {
		this.coinPurse.setValue("Coin: " + this.loadedCharacter.getCharacterInventory().getCharacterCoinPurse());
		this.coinModifier.setValue("");
	}
	
	/**
	 * Returns the character's name for the UI.
	 * 
	 * @return charName
	 */
	public StringProperty getCharName() {
		return this.charName;
	}
	
	/**
	 * Returns the character's level for the UI
	 * 
	 * @return charLevel
	 */
	public StringProperty getCharLevel() {
		return this.charLevel;
	}
	
	/**
	 * Returns the character's race for the UI
	 * 
	 * @return charRace
	 */
	public StringProperty getCharRace() {
		return this.charRace;
	}
	
	/**
	 * Returns the character's class for the UI
	 * 
	 * @return charClass
	 */
	public StringProperty getCharClass() {
		return this.charClass;
	}
	
	/**
	 * Returns the character's strength attribute for the UI
	 * 
	 * @return charStr
	 */
	public StringProperty getCharStr() {
		return this.charStr;
	}
	
	/**
	 * Returns the characer's strength bonus modifier for the UI
	 * 
	 * @return charStrBonus
	 */
	public StringProperty getCharStrBonus() {
		return this.charStrBonus;
	}
	
	/**
	 * Returns the character's dexterity attribute for the UI
	 * 
	 * @return charDex
	 */
	public StringProperty getCharDex() {
		return this.charDex;
	}
	
	/**
	 * Returns the character's dexterity modifier for the UI
	 * 
	 * @return charDexBonus
	 */
	public StringProperty getCharDexBonus() {
		return this.charDexBonus;
	}
	
	/**
	 * Returns the character's constitution attribute for the UI
	 * 
	 * @return charCon
	 */
	public StringProperty getCharCon() {
		return this.charCon;
	}
	
	/**
	 * Returns the character's constitution modifier for the UI
	 * 
	 * @return charConBonus
	 */
	public StringProperty getCharConBonus() {
		return this.charConBonus;
	}
	
	/**
	 * Returns the character's intelligence attribute for the UI
	 * 
	 * @return charInt
	 */
	public StringProperty getCharInt() {
		return this.charInt;
	}
	
	/**
	 * Returns the character's intelligence modifier for the UI
	 * 
	 * @return charIntBonus
	 */
	public StringProperty getCharIntBonus() {
		return this.charIntBonus;
	}
	
	/**
	 * Returns the character's wisdom attribute for the UI
	 * 
	 * @return charWis
	 */
	public StringProperty getCharWis() {
		return this.charWis;
	}
	
	/**
	 * Returns the character's wisdom modifier for the UI
	 * 
	 * @return charWisBonus
	 */
	public StringProperty getCharWisBonus() {
		return this.charWisBonus;
	}
	
	/**
	 * Returns the character's charisma attribute for the UI
	 * 
	 * @return charCha
	 */
	public StringProperty getCharCha() {
		return this.charCha;
	}
	
	/**
	 * Returns the character's charisma modifier for the UI
	 * 
	 * @return charChaBonus
	 */
	public StringProperty getCharChaBonus() {
		return this.charChaBonus;
	}
	
	/**
	 * Returns the character's character's armor class rating for the UI
	 * 
	 * @return charAC
	 */
	public StringProperty getCharAC() {
		return this.charAC;
	}
	
	/**
	 * Returns the character's current / max health values for the UI
	 * 
	 * @return charHelath
	 */
	public StringProperty getCharHealth() {
		return this.charHealth;
	}
	
	/**
	 * Retrieves the player input value in which to modifier the character's health
	 * 
	 * @return healthModifier
	 */
	public StringProperty getHealthModifier() {
		return this.healthModifier;
	}
	
	/**
	 * Returns the value of the player having an inspiration die
	 * 
	 * @return hasInspirationDie
	 */
	public BooleanProperty getHasInspirationDie() {
		return this.hasInspirationDie;
	}
	
	/**
	 * Returns the proficiency modifiers for the UI
	 * 
	 * @return proficencyBonus
	 */
	public StringProperty getProficiencyBonus() {
		return this.proficiencyBonus;
	}
	
	public ListProperty<String> getAbilityList() {
		return this.charAbilities;
	}
	
	/**
	 * Returns the inventory list for the UI
	 * 
	 * @return charInventory
	 */
	public ListProperty<String> getInventoryList() {
		return this.charInventory;
	}
	
	/**
	 * Returns the spell list for the UI
	 * 
	 * @return charSpells
	 */
	public ListProperty<String> getSpellList() {
		return this.charSpells;
	}
	
	/**
	 * Returns the feats list for the UI
	 * 
	 * @return charFeats
	 */
	public ListProperty<String> getFeatList() {
		return this.charFeats;
	}
	
	/**
	 * Returns the background list for the UI
	 * 
	 * @return charBackground
	 */
	public ListProperty<String> getBackgroundList() {
		return this.charBackground;
	}
	
	/**
	 * Returns the value of how much gold is in the character's coin purse
	 * @return
	 */
	public StringProperty getCoinPurse() {
		return this.coinPurse;
	}
	
	/**
	 * Holds the value in which to modifier the character's coin purse
	 * 
	 * @return coinModifier
	 */
	public StringProperty getCoinModifier() {
		return this.coinModifier;
	}
	
	/**
	 * Returns the current notes the character has on the campaign.
	 * 
	 * @return characterCampaignNotes
	 */
	public StringProperty getCharacterCampaignNotes() {
		return this.characterCampaignNotes;
	}
	
	public boolean saveCharacterCurrentStatus() {
		System.out.println("Question");
		if (this.loadedCharacter != null) {
			ServerCommunicationHandler.removeCharacter(this.loadedCharacter);
			
			this.loadedCharacter.setCharacterCurrentHealthPoints(this.loadedCharacter.getCharacterCurrentHealthPoints());
			this.loadedCharacter.setHasInspirationDie(this.loadedCharacter.hasInspiration());
			this.loadedCharacter.setCharacterCampaignNotes(this.getCharacterCampaignNotes().getValue());
			
			ServerCommunicationHandler.saveCharacter(this.loadedCharacter, true);
			return true;
		}
		return false;
	}
}
