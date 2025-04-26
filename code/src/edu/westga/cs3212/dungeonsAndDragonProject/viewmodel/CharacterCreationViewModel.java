package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Item;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The viewmodel connecting the Character Creation GUI to the rest of the software.
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class CharacterCreationViewModel {
	
	private StringProperty characterName;
	private IntegerProperty characterLevel;
	private IntegerProperty characterArmorClass;
	private IntegerProperty characterMaxHP;
	private IntegerProperty attributeStrength;
	private IntegerProperty attributeDexterity;
	private IntegerProperty attributeConstitution;
	private IntegerProperty attributeIntelligence;
	private IntegerProperty attributeWisdom;
	private IntegerProperty attributeCharisma;
	private IntegerProperty characterGold;
	
	private ListProperty<Role> characterClassList;
	private ObjectProperty<Role> selectedCharacterClass;
	private StringProperty classDetails;
	private StringProperty skillProficency1;
	private StringProperty skillProficency2;
	private ObservableList<String> weaponMasteriesList;
	private StringProperty weaponMastery1;
	private StringProperty weaponMastery2;
	
	private ListProperty<Race> speciesList;
	private ObjectProperty<Race> selectedSpecies;
	private StringProperty raceDetails;
	private StringProperty characterAlignment;
	private StringProperty characterFaith;
	private StringProperty characterHair;
	private StringProperty characterSkin;
	private StringProperty characterEyes;
	private StringProperty characterHeight;
	private StringProperty characterWeight;
	private StringProperty characterAge;
	private StringProperty characterGender;
	
	private StringProperty equipmentToAdd;
	private List<String> equipmentList;
	private ListProperty<String> listOfItems;
	private StringProperty spellToAdd;
	private List<String> spellList;
	private ListProperty<String> listOfSpells;
	private StringProperty featToAdd;
	private List<String> featList;
	private ListProperty<String> listOfFeats;
	private StringProperty itemSelection;
	private StringProperty spellSelection;
	private StringProperty featSelection;
	
	private boolean hasInspirationDie;
	private boolean isPreExistingCharacter;
	
	private Player currentPlayerAccount;
	
	/**
	 * Constructor for NEW CHARACTER BUILD
	 */
	public CharacterCreationViewModel() {
		this.currentPlayerAccount = new Player(SystemContextViewModel.getInstance().getCurrentAccount());
		
		this.characterName = new SimpleStringProperty();
		this.characterLevel = new SimpleIntegerProperty();
		this.characterArmorClass = new SimpleIntegerProperty();
		this.characterMaxHP = new SimpleIntegerProperty();
		this.attributeStrength = new SimpleIntegerProperty();
		this.attributeDexterity = new SimpleIntegerProperty();
		this.attributeConstitution = new SimpleIntegerProperty();
		this.attributeIntelligence = new SimpleIntegerProperty();
		this.attributeWisdom = new SimpleIntegerProperty();
		this.attributeCharisma = new SimpleIntegerProperty();
		this.characterGold = new SimpleIntegerProperty();
		
		this.characterClassList = new SimpleListProperty<Role>(FXCollections.observableArrayList(new ArrayList<>()));
		this.selectedCharacterClass = new SimpleObjectProperty<Role>();
		this.classDetails = new SimpleStringProperty();
		this.skillProficency1 = new SimpleStringProperty();
		this.skillProficency2 = new SimpleStringProperty();
		this.weaponMasteriesList = FXCollections.observableArrayList();
		this.weaponMastery1 = new SimpleStringProperty();
		this.weaponMastery2 = new SimpleStringProperty();
		
		this.speciesList = new SimpleListProperty<Race>(FXCollections.observableArrayList(new ArrayList<>()));
		this.selectedSpecies = new SimpleObjectProperty<Race>();
		this.raceDetails = new SimpleStringProperty();
		this.characterAlignment = new SimpleStringProperty();
		this.characterFaith = new SimpleStringProperty();
		this.characterHair = new SimpleStringProperty();
		this.characterSkin = new SimpleStringProperty();
		this.characterEyes = new SimpleStringProperty();
		this.characterHeight = new SimpleStringProperty();
		this.characterWeight = new SimpleStringProperty();
		this.characterAge = new SimpleStringProperty();
		this.characterGender = new SimpleStringProperty();
		
		this.equipmentToAdd = new SimpleStringProperty();
		this.equipmentList = new ArrayList<String>();
		this.spellToAdd = new SimpleStringProperty();
		this.spellList = new ArrayList<String>();
		this.featToAdd = new SimpleStringProperty();
		this.featList = new ArrayList<String>();
		
		this.listOfItems = new SimpleListProperty<String>(FXCollections.observableArrayList(this.equipmentList));
		this.listOfSpells = new SimpleListProperty<String>(FXCollections.observableArrayList(this.spellList));
		this.listOfFeats = new SimpleListProperty<String>(FXCollections.observableArrayList(this.featList));
		this.itemSelection = new SimpleStringProperty();
		this.spellSelection = new SimpleStringProperty();
		this.featSelection = new SimpleStringProperty();
		
		this.hasInspirationDie = false;
		this.isPreExistingCharacter = false;
		
		if (SystemContextViewModel.getInstance().getCharacterSelection() != null) {
			this.setFields(SystemContextViewModel.getInstance().getCharacterSelection());
		}
		
		this.setClassList();
		this.setSpeciesList();
	}
	
	private void setFields(Character editCharacter) {
		this.characterName.setValue(editCharacter.getCharacterName());
		this.characterLevel.setValue(editCharacter.getCharacterLevel());
		this.characterArmorClass.setValue(editCharacter.getCharacterArmorClass());
		this.characterMaxHP.setValue(editCharacter.getCharacterMaxHealthPoints());
		
		this.attributeStrength.setValue(editCharacter.getCharacterAttributes().getStrength());
		this.attributeDexterity.setValue(editCharacter.getCharacterAttributes().getDexterity());
		this.attributeConstitution.setValue(editCharacter.getCharacterAttributes().getConstitution());
		this.attributeIntelligence.setValue(editCharacter.getCharacterAttributes().getIntelligence());
		this.attributeWisdom.setValue(editCharacter.getCharacterAttributes().getWisdom());
		this.attributeCharisma.setValue(editCharacter.getCharacterAttributes().getCharisma());
		this.characterGoldProperty().setValue(editCharacter.getCharacterInventory().getCharacterCoinPurse());
		
		this.selectedCharacterClass.setValue(editCharacter.getCharacterClass());
		this.setProficencies(editCharacter);
		this.weaponMastery1.setValue(editCharacter.getWeaponMasteries().getFirst());
		this.weaponMastery2.setValue(editCharacter.getWeaponMasteries().getLast());
		
		this.hasInspirationDie = editCharacter.hasInspiration();
		
		this.selectedSpecies.setValue(editCharacter.getCharacterSpecies());
		this.characterAlignment.setValue(editCharacter.getCharacterAlignment());
		this.characterFaith.setValue(editCharacter.getCharacterFaith());
		this.characterHair.setValue(editCharacter.getCharacterHair());
		this.characterSkin.setValue(editCharacter.getCharacterSkin());
		this.characterEyes.setValue(editCharacter.getCharacterEyes());
		this.characterHeight.setValue(editCharacter.getCharacterHeight());
		this.characterWeight.setValue(editCharacter.getCharacterWeight());
		this.characterAge.setValue(editCharacter.getCharacterAge());
		this.characterGender.setValue(editCharacter.getCharacterGender());
		
		this.setCharLists(editCharacter);
		this.updateAllLists();
		
		this.isPreExistingCharacter = true;
	}
	
	private void setCharLists(Character character) {
		if (character.getInventoryByString() != null) {
			this.equipmentList = character.getInventoryByString();
		} else { 
			this.equipmentList = new ArrayList<String>();
		}
		
		if (character.getCharacterSpells() != null) {
			this.spellList = character.getCharacterSpells();
		} else {
			this.spellList = new ArrayList<String>();
		}
		
		if (character.getCharacterFeats() != null) {
			this.featList = character.getCharacterFeats();
		} else {
			this.featList = new ArrayList<String>();
		}
	}
	
	/**
	 * Sets the character class list
	 */
	public void setClassList() {
		this.characterClassList.set(FXCollections.observableArrayList(ServerCommunicationHandler.loadClasses()));
	}
	
	/**
	 * Sets the list of species the character can be 
	 */
	public void setSpeciesList() {
		this.speciesList.set(FXCollections.observableArrayList(ServerCommunicationHandler.loadRaces()));
	}
	
	//******************* GETTERS *******************
	/**
	 * Gets the information for the current player account.
	 * 
	 * @return currentPlayerAccount
	 */
	public Player getPlayerAccount() {
		return this.currentPlayerAccount;
	}
	
	/**
	 * Returns the characterNameProperty for the GUI
	 * 
	 * @return characterName
	 */
	public StringProperty characterNameProperty() {
		return this.characterName;
	}
	
	/**
	 * Returns the character's level for the GUI
	 * 
	 * @return characterLeve
	 */
	public IntegerProperty characterLevelProperty() {
		return this.characterLevel;
	}
	
	/**
	 * Returns the character's current armor class rating for the GUI
	 * 
	 * @return characterArmorClass
	 */
	public IntegerProperty characterArmorClassRatingProperty() {
		return this.characterArmorClass;
	}
	
	/**
	 * Returns the character's max health points for the GUI
	 * 
	 * @return characterMaxHP
	 */
	public IntegerProperty characterMaxHP() {
		return this.characterMaxHP;
	}
	
	/**
	 * Returns the attributeStrengthProperty for the GUI
	 * 
	 * @return attributeStrength
	 */
	public IntegerProperty attributeStrengthProperty() {
		return this.attributeStrength;
	}
	
	/**
	 * Returns the attributeDexterityProperty for the GUI
	 * 
	 * @return attributeDexterity
	 */
	public IntegerProperty attributeDexterityProperty() {
		return this.attributeDexterity;
	}
	
	/**
	 * Returns the attributeConstitutionProperty for the GUI
	 * 
	 * @return attributeConstitution
	 */
	public IntegerProperty attributeConstitutionProperty() {
		return this.attributeConstitution;
	}
	
	/**
	 * Returns the attributeIntelligenceProperty for the GUI
	 * 
	 * @return attributeIntelligence
	 */
	public IntegerProperty attributeIntelligenceProperty() {
		return this.attributeIntelligence;
	}
	
	/**
	 * Returns the attributeWisdomProperty for the GUI
	 * 
	 * @return attributeWisdom
	 */
	public IntegerProperty attributeWisdomProperty() {
		return this.attributeWisdom;
	}
	
	/**
	 * Returns the attributeCharismaProperty for the GUI
	 * 
	 * @return attributeCharisma
	 */
	public IntegerProperty attributeCharismaProperty() {
		return this.attributeCharisma;
	}
	
	/**
	 * Returns the gold in the character's inventory for the GUI
	 * 
	 * @return characterGold
	 */
	public IntegerProperty characterGoldProperty() {
		return this.characterGold;
	}
	
	/**
	 * Returns the characterClassListProperty for the GUI
	 * 
	 * @return characterClassList
	 */
	public ListProperty<Role> getCharacterClassList() {
		return this.characterClassList;
	}
	
	/**
	 * Returns the selected characterClass for the GUI
	 * 
	 * @return characterClass
	 */
	public ObjectProperty<Role> getSelectedCharacterClass() {
		return this.selectedCharacterClass;
	}
	
	/**
	 * Returns skill proficiency 1 for the GUI
	 * 
	 * @return characterClass
	 */
	public StringProperty getSkillProficiency1() {
		return this.skillProficency1;
	}
	
	/**
	 * Returns skill proficiency 2 for the GUI
	 * 
	 * @return characterClass
	 */
	public StringProperty getSkillProficiency2() {
		return this.skillProficency2;
	}
	
	/**
	 * Returns the weaponMasteriesListProperty for the GUI
	 * 
	 * @return weaponMasteriesList
	 */
	public ObservableList<String> getWeaponProficencyList() {
		return this.weaponMasteriesList;
	}
	
	/**
	 * Returns the first weapon mastery for the GUI
	 * 
	 * @return weaponMastery1
	 */
	public StringProperty getWeaponMastery1() {
		return this.weaponMastery1;
	}
	
	/**
	 * Returns the second weapon mastery for the GUI
	 * 
	 * @return weaponMastery2
	 */
	public StringProperty getWeaponMastery2() {
		return this.weaponMastery2;
	}
	
	/**
	 * Returns the speciesListProperty for the GUI
	 * 
	 * @return speciesList
	 */
	public ListProperty<Race> getSpeciesList() {
		return this.speciesList;
	}
	
	/**
	 * Returns the selected characterSpecies for the GUI
	 * 
	 * @return characterSpecies
	 */
	public ObjectProperty<Race> selectedCharacterSpecies() {
		return this.selectedSpecies;
	}

	/**
	 * Returns the character's alignment for the GUI
	 * 
	 * @return characterAlignment
	 */
	public StringProperty characterAlignmentProperty() {
		return this.characterAlignment;
	}
	
	/**
	 * Returns the character's faith for the GUI
	 * 
	 * @return characterFaith
	 */
	public StringProperty characterFaithProperty() {
		return this.characterFaith;
	}
	
	/**
	 * Returns the characterHairProperty for the GUI
	 * 
	 * @return characterHair
	 */
	public StringProperty characterHairProperty() {
		return this.characterHair;
	}
	
	/**
	 * Returns the characterSkinProperty for the GUI
	 * 
	 * @return characterSkin
	 */
	public StringProperty characterSkinProperty() {
		return this.characterSkin;
	}
	
	/**
	 * Returns the characterEyesProperty for the GUI
	 * 
	 * @return characterEyes
	 */
	public StringProperty characterEyesProperty() {
		return this.characterEyes;
	}
	
	/**
	 * Returns the characterHeightProperty for the GUI
	 * 
	 * @return characterHeight
	 */
	public StringProperty characterHeightProperty() {
		return this.characterHeight;
	}
	
	/**
	 * Returns the characterWeightProperty for the GUI
	 * 
	 * @return characterWeight
	 */
	public StringProperty characterWeightProperty() {
		return this.characterWeight;
	}
	
	/**
	 * Returns the characterAgeProperty for the GUI
	 * 
	 * @return characterAge
	 */
	public StringProperty characterAgeProperty() {
		return this.characterAge;
	}
	
	/**
	 * Returns the characterGenderProperty for the GUI
	 * 
	 * @return characterGender
	 */
	public StringProperty characterGenderProperty() {
		return this.characterGender;
	}
	
	/**
	 * Sets the equipment to add
	 * 
	 * @return equipmentToAdd
	 */
	public StringProperty equipmentProperty() {
		return this.equipmentToAdd;
	}
	
	/**
	 * Returns the value of the selected item in a listview
	 * 
	 * @return listSelection
	 */
	public StringProperty getItemSelection() {
		return this.itemSelection;
	}
	
	/**
	 * Returns the value of the selected item in a listview
	 * 
	 * @return spellSelection
	 */
	public StringProperty getSpellSelection() {
		return this.spellSelection;
	}
	
	/**
	 * Returns the value of the selected item in a listview
	 * 
	 * @return featSelection
	 */
	public StringProperty getFeatSelection() {
		return this.featSelection;
	}
	
	private void updateAllLists() {
		for (String item : this.equipmentList) {
			this.listOfItems.add(item);
		}
		for (String spell : this.spellList) {
			this.listOfSpells.add(spell);
		}
		for (String feat : this.featList) {
			this.listOfFeats.add(feat);
		}
	}
	
	/**
	 * Updates the list of equipment for the GUI
	 * 
	 * @pre equipmentToAdd != null && !equipmentToAdd.getValue().isEmpty()
	 * @post listOfItems.size() + 1
	 */
	public void updateEquipmentList() {
		if (this.equipmentToAdd.getValue() != null && !this.equipmentToAdd.getValue().isEmpty()) {
			this.equipmentList.add(this.equipmentToAdd.getValue());
			this.listOfItems.set(FXCollections.observableArrayList(this.equipmentList));
			this.equipmentToAdd.setValue("");
		}
	}
	
	/**
	 * Removes the selected item from the equipment list 
	 * 
	 * @return true if equipmentList.contains(itemSelection)
	 */
	public boolean removeEquipmentFromEquipmentList() {
		if (this.itemSelection.equals(null)) {
			throw new NullPointerException("Item selection cannot be null.");
		}
		if (this.equipmentList.contains(this.itemSelection.getValue())) {
			this.equipmentList.remove(this.itemSelection.getValue());
			this.listOfItems.set(FXCollections.observableArrayList(this.equipmentList));
			this.itemSelection.setValue(null);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds current character inventory to list
	 * 
	 * @param inventory the character's inventory
	 */
	public void addCharacterInventoryToEquipmentList(Inventory inventory) {
		for (Item item : inventory.getInventoryList()) {
			this.equipmentList.add(item.getItemName());
		}
		this.listOfItems.set(FXCollections.observableArrayList(this.equipmentList));
	}
	
	/**
	 * Returns a list of equipment for the GUI
	 * 
	 * @return listOfItems
	 */
	public ListProperty<String> getEquipmentList() {
		return this.listOfItems;
	}
	
	/**
	 * The current spell the player is taking
	 * 
	 * @return spellToAdd
	 */
	public StringProperty spellProperty() {
		return this.spellToAdd;
	}
	
	/**
	 * Updates the list of spells for the GUI
	 * 
	 * @pre spellToAdd != null && !spellToAdd.getValue().isEmpty()
	 * @post listOfSpells.size() + 1
	 */
	public void updateSpellList() {
		if (this.spellToAdd.getValue() != null && !this.spellToAdd.getValue().isEmpty()) {
			this.spellList.add(this.spellToAdd.getValue());
			this.listOfSpells.set(FXCollections.observableArrayList(this.spellList));
			this.spellToAdd.setValue("");
		}
	}
	
	/**
	 * Removes the selected spell from the spell list
	 * 
	 * @return true if spellList.contains(spellSelection)
	 */
	public boolean removeSpellFromSpellList() {
		if (this.spellSelection.equals(null)) {
			throw new NullPointerException("Spell selection cannot be null.");
		}
		if (this.spellList.contains(this.spellSelection.getValue())) {
			this.spellList.remove(this.spellSelection.getValue());
			this.listOfSpells.set(FXCollections.observableArrayList(this.spellList));
			this.spellSelection.setValue(null);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds current character spells to the spell list
	 * 
	 * @param spells a list of the character's spells
	 */
	public void addCharacterSpellsToSpellList(List<String> spells) {
		for (String spell : spells) {
			this.spellList.add(spell);
		}
		this.listOfSpells.set(FXCollections.observableArrayList(this.spellList));
	}
	
	/**
	 * Returns a list of spells for the GUI
	 * 
	 * @return listOfSpells
	 */
	public ListProperty<String> getSpellList() {
		return this.listOfSpells;
	}
	
	/**
	 * The feat the player is taking
	 * 
	 * @return featToAdd
	 */
	public StringProperty featProperty() {
		return this.featToAdd;
	}
	
	/**
	 * Updates the list of feats for the GUI
	 * 
	 * @pre featToAdd != null && !featToAdd.getValue().isEmpty()
	 * @post listOfFeats.size() + 1
	 */
	public void updateFeatList() {
		if (this.featToAdd.getValue() != null && !this.featToAdd.getValue().isEmpty()) {
			this.featList.add(this.featToAdd.getValue());
			this.listOfFeats.set(FXCollections.observableArrayList(this.featList));
			this.featToAdd.setValue("");
		}
	}
	
	/**
	 * Removes the selected feat from the feat list
	 * 
	 * @return true if featList.contains(featSelection)
	 */
	public boolean removeFeatFromFeatList() {
		if (this.featSelection.equals(null)) {
			throw new NullPointerException("Spell selection cannot be null.");
		}
		if (this.featList.contains(this.featSelection.getValue())) {
			this.featList.remove(this.featSelection.getValue());
			this.listOfFeats.set(FXCollections.observableArrayList(this.featList));
			this.featSelection.setValue(null);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds current character feats to the feat list
	 * 
	 * @param feats a list of the character's feats
	 */
	public void addCharacterFeatsToFeatsList(List<String> feats) {
		for (String feat : feats) {
			this.featList.add(feat);
		}
		this.listOfFeats.set(FXCollections.observableArrayList(this.featList));
	}
	
	/**
	 * Returns a list of feats for the GUI
	 * 
	 * @return listOfFeats
	 */
	public ListProperty<String> getFeatsList() {
		return this.listOfFeats;
	}
	
	/**
	 * Sets the selected role's details
	 * 
	 * @param selectedClass the currently selected class
	 */
	public void displayClassInformation(Role selectedClass) {
		this.classDetails.setValue(selectedClass.roleDetails());
	}
	
	/**
	 * Displays the role's details
	 * 
	 * @return classDetails
	 */
	public StringProperty updatedClassInformation() {
		return this.classDetails;
	}
	
	/**
	 * Sets the selected race details
	 * 
	 * @param selectedRace the currently selected race
	 */
	public void displaySpeciesInformation(Race selectedRace) {
		this.raceDetails.setValue(selectedRace.raceDetails());
		System.out.println(selectedRace.raceDetails());
	}
	
	/**
	 * Displays the race's details
	 * 
	 * @return raceDetails
	 */
	public StringProperty updateSpeciesInformation() {
		return this.raceDetails;
	}
	
	private List<String> getSkills() {
		List<String> skills = new ArrayList<String>();
		
		skills.add(this.skillProficency1.getValue());
		skills.add(this.skillProficency2.getValue());
		
		return skills;
	}
	
	private void setProficencies(Character character) {
		if (character != null && character.getSkillProficiencies() != null && !character.getSkillProficiencies().isEmpty()) {
			this.skillProficency1.setValue(character.getSkillProficiencies().get(0));
			if (character.getSkillProficiencies().size() >= 2) {
				this.skillProficency2.setValue(character.getSkillProficiencies().get(1));
			} else {
				this.skillProficency2.setValue("");
			}
		} else {
			this.skillProficency1.setValue("");
			this.skillProficency2.setValue("");
		}
	}
	
	/**
	 * Gets the fully kitted out character
	 * 
	 * @return newCharacter
	 */
	public Character getCharacter() {
		Character newCharacter = new Character();
		
		newCharacter.setCharacterName(this.characterName.getValue());
		newCharacter.setCharacterLevel(this.characterLevel.getValue());
		newCharacter.setArmorClass(this.characterArmorClass.getValue());
		newCharacter.setCharacterMaxHealthPoints(this.characterMaxHP.getValue());
		newCharacter.setCharacterAttributes(this.setAttributes());
		newCharacter.getCharacterInventory().addCoinToPurse(this.characterGold.getValue());
		
		newCharacter.setCharacterSpecies(this.selectedSpecies.getValue());
		newCharacter.setSkillProficencies(this.getSkills());
		newCharacter.setInventoryByString(this.equipmentList);
		newCharacter.setSpellsList(this.spellList);
		newCharacter.setCharacterFeats(this.featList);
		newCharacter.setWeaponMasteries(this.getWeaponMasteries());
		
		newCharacter.setCharacterClass(this.selectedCharacterClass.getValue());
		newCharacter.setCharacterAlignment(this.characterAlignment.getValue());
		newCharacter.setCharacterFaith(this.characterFaith.getValue());
		newCharacter.setCharacterHair(this.characterHair.getValue());
		newCharacter.setCharacterSkin(this.characterSkin.getValue());
		newCharacter.setCharacterEyes(this.characterEyes.getValue());
		newCharacter.setCharacterHeight(this.characterHeight.getValue());
		newCharacter.setCharacterWeight(this.characterWeight.getValue());
		newCharacter.setCharacterAge(this.characterAge.getValue());
		newCharacter.setCharacterGender(this.characterGender.getValue());
		
		newCharacter.setHasInspirationDie(this.hasInspirationDie);
		
		newCharacter.setPlayerOwnership(this.currentPlayerAccount.getAccountInfo().getAccountId());
		
		return newCharacter;
	}
	
	/**
	 * Lets the system know if the character is a pre-existing character.
	 * 
	 * @return true is preExisting, false if new
	 */
	public boolean isPreExistingCharacter() {
		return this.isPreExistingCharacter;
	}
	
	private Attributes setAttributes() {
		Attributes attributes = new Attributes();
		attributes.setStrength(this.attributeStrength.getValue());
		attributes.setDexterity(this.attributeDexterity.getValue());
		attributes.setConstituion(this.attributeConstitution.getValue());
		attributes.setIntelligence(this.attributeIntelligence.getValue());
		attributes.setWisdom(this.attributeWisdom.getValue());
		attributes.setCharisma(this.attributeCharisma.getValue());
		
		return attributes;
	}
	
	private List<String> getSpellListAsArray() {
		List<String> spellListArray = new ArrayList<String>();
		for (String spell : this.spellList) {
			spellListArray.add(spell);
		}
		return spellListArray;
	}
	
	private List<String> getWeaponMasteries() {
		List<String> weaponMasteries = new ArrayList<String>();
		weaponMasteries.add(this.weaponMastery1.getValue());
		weaponMasteries.add(this.weaponMastery2.getValue());
		return weaponMasteries;
	}
	
	/**
	 * Saves the current character for the player in a json file
	 */
	public void saveCharacter() {
		Character newCharacter = this.getCharacter();
		
		this.currentPlayerAccount.createCharacter(newCharacter);
		System.err.println("Look Here");
		System.err.println(newCharacter.getCharacterName());
		SystemContextViewModel.getInstance().getExistingCharacters().add(newCharacter);
		ServerCommunicationHandler.saveCharacter(newCharacter, this.isPreExistingCharacter());
	}
}
