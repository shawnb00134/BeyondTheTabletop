package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

/**
 * The viewmodel for the CharacterManager GUI
 * 
 * @author Shawn Bretthauer
 * 
 * @version Spring 2025
 */
public class CharacterManagerViewModel {

	public static final int MAX_CHARACTERS_ALLOWED = 5;
	
	private ListProperty<Character> characterList;
	private BooleanProperty maxReached;
	private Player currentPlayer;

	/**
	 * CharacterManager ViewModel constructor
	 */
	public CharacterManagerViewModel() {
		Player player = new Player(SystemContextViewModel.getInstance().getCurrentAccount());
		this.currentPlayer = player;
		this.characterList = new SimpleListProperty<Character>(
				FXCollections.observableArrayList(new ArrayList<Character>()));
		this.maxReached = new SimpleBooleanProperty(false);
		
		this.loadCharacters();
	}
	
	/**
	 * Gets the newly created character
	 */
	public void getCharacter() {
		return;
	}

	/**
	 * Returns the list of characters created
	 * 
	 * @return characterList
	 */
	public ListProperty<Character> getCharacterList() {
		return this.characterList;
	}
	
	/**
	 * Saves character to the character list
	 * 
	 * @param character the character added
	 */
	public void addCharacterToList(Character character) {
		this.characterList.add(character);
	}
	
	/**
	 * Loads characters
	 * 
	 * @precondition none
	 * @postcondition this.characterList.size() updated
	 * 
	 * @param list list of characters
	 */
	public void loadCharacters(List<Character> list) {
		List<Character> loadCharacterList = list;
		
		for (Character character : loadCharacterList) {
			this.characterList.add(character);
		}
	}
	
	/**
	 * Deletes a selected character from the player's character list
	 * 
	 * @param character the character to be deleted
	 */
	public void deleteCharacter(Character character) {
		this.getCharacterList().remove(character);
		this.setCreateNewCharacterButton();
	}
	
	/**
	 * Gets the player
	 * 
	 * @return current player
	 */
	public Player getPlayerOfCurrentAccount() {
		return this.currentPlayer;
	}
	
	/**
	 * Loads the characters into the GUI
	 */
	public void loadCharacters() {
		AccountInfo currentAccount = SystemContextViewModel.getInstance().getCurrentAccount();
		List<Character> loadedCharacters = ServerCommunicationHandler.loadCharacters(currentAccount);
		this.checkCreatedCharacters(loadedCharacters, currentAccount);
	}
	
	/**
	 * Adds
	 * 
	 * @param loadedCharacters redo
	 * @param currentAccount redo
	 */
	public void checkCreatedCharacters(List<Character> loadedCharacters, AccountInfo currentAccount) {
		for (Character currentCharacter : loadedCharacters) {
			if (currentAccount.getAccountId().equals(currentCharacter.getPlayerOwnerID())) {
				this.characterList.add(currentCharacter);
			}
		}
		this.setCreateNewCharacterButton();
	}
	
	/**
	 * Saves the character
	 * @param character the character
	 */
	public void saveCharacter(Character character) {
		this.characterList.add(character);
		this.currentPlayer.createCharacter(character);
		ServerCommunicationHandler.saveCharacter(character, false);
	}
	
	/**
	 * Sets the true / false value if the player is allowed to create a new character.
	 */
	public void setCreateNewCharacterButton() {
		if (this.characterList.size() >= MAX_CHARACTERS_ALLOWED) {
			this.maxReached.setValue(true);
		} else {
			this.maxReached.setValue(false);
		}
	}
	
	/**
	 * Returns the BooleanProperty if the player is allowed to create a new character.
	 * 
	 * @return maxReached
	 */
	public BooleanProperty getCreateNewCharacterButton() {
		return this.maxReached;
	}
	
	/**
	 * Sets the text label value of how many characters the player currently has and the maximum allowed.
	 * 
	 * @return characterList.size / MAX_CHARACTERS_ALLOWED
	 */
	public String getCharacterLimitText() {
		return "Characters: " + this.characterList.size() + "/" + MAX_CHARACTERS_ALLOWED;
	}
}
