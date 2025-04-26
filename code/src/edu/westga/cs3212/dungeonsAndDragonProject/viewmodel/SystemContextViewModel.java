package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import java.util.ArrayList;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

/**
 * Keeps track of current account
 * 
 * @author kg00281
 * @version Spring 2025
 */
public final class SystemContextViewModel {

	private static SystemContextViewModel instance;

	private final ObjectProperty<AccountInfo> currentAccount = new SimpleObjectProperty<>();
	private ListProperty<Campaign> existingCampaigns;
	private ListProperty<AccountInfo> existingAccounts;
	private ListProperty<Character> existingCharacters;
	private Character characterSelection;

	private SystemContextViewModel() {
		this.existingCampaigns = new SimpleListProperty<Campaign>(
				FXCollections.observableArrayList(new ArrayList<Campaign>()));
		for (Campaign curr : ServerCommunicationHandler.loadCampaigns()) {
			this.existingCampaigns.add(curr);
		}

		this.existingAccounts = new SimpleListProperty<AccountInfo>(
				FXCollections.observableArrayList(new ArrayList<AccountInfo>()));
		for (AccountInfo curr : ServerCommunicationHandler.loadAccounts()) {
			this.existingAccounts.add(curr);
		}

		this.existingCharacters = new SimpleListProperty<Character>(
				FXCollections.observableArrayList(new ArrayList<Character>()));

		this.characterSelection = null;
	}

	/**
	 * Gets instance of class
	 * 
	 * @return instance of class
	 */
	public static SystemContextViewModel getInstance() {
		if (instance == null) {
			instance = new SystemContextViewModel();
		}
		return instance;
	}

	/**
	 * Gets the current account
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return gets current account
	 */
	public AccountInfo getCurrentAccount() {
		return this.currentAccount.get();
	}

	/**
	 * Sets current account in system
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param account the account that is using system
	 */
	public void setCurrentAccount(AccountInfo account) {
		this.currentAccount.set(account);
	}

	/**
	 * Gets the existing campaigns in system
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return a list of existing campaigns
	 */
	public ListProperty<Campaign> getExistingCampaigns() {
		return this.existingCampaigns;
	}

	/**
	 * Overwrites campaign json file specifically when one campaign is updated
	 * 
	 * @precondition none
	 * @postcondition campaigns.json is overwritten
	 *
	 * @param campaign the campaign that is being overwritten
	 */
	public void overWriteCampaign(Campaign campaign) {
		this.existingCampaigns.removeIf(curr -> campaign.getName().equals(curr.getName()));
		this.existingCampaigns.add(campaign);
		ServerCommunicationHandler.saveCampaigns(this.existingCampaigns);
	}

	/**
	 * Overwrites campaign json file specifically when one campaign needs to be
	 * removed
	 * 
	 * @precondition none
	 * @postcondition campaigns.json is overwritten
	 *
	 * @param campaign the campaign that is being overwritten
	 */
	public void removeCampaign(Campaign campaign) {
		this.existingCampaigns.removeIf(curr -> campaign.getName().equals(curr.getName()));
		ServerCommunicationHandler.saveCampaigns(this.existingCampaigns);
	}

	/**
	 * Overwrites accounts json file specifically for invites
	 * 
	 * @precondition none
	 * @postcondition accounts.json is overwritten
	 * 
	 * @param recipient     account that invite is being sent to
	 * @param dungeonMaster dm that is sending out invite
	 */
	public void overWriteAccounts(AccountInfo recipient, AccountInfo dungeonMaster) {

		this.existingAccounts.removeIf(curr -> recipient.getUsername().equals(curr.getUsername()));
		this.existingAccounts.add(recipient);

		this.existingAccounts.removeIf(curr -> dungeonMaster.getUsername().equals(curr.getUsername()));
		this.existingAccounts.add(dungeonMaster);

		ServerCommunicationHandler.saveAccounts(this.existingAccounts);

	}

	/**
	 * Gets a list of existing characters
	 * 
	 * @return existingCharacters
	 */
	public ListProperty<Character> getExistingCharacters() {
		for (Character currentCharacter : ServerCommunicationHandler.loadCharacters(this.getCurrentAccount())) {
			this.existingCharacters.add(currentCharacter);
		}

		return this.existingCharacters;
	}

	/**
	 * Overwrites campaign json file specifically when one character needs to be
	 * removed
	 * 
	 * @precondition none
	 * @postcondition character.json is overwritten
	 *
	 * @param character the character that is being overwritten
	 */
	public void removeCharacter(Character character) {
		this.existingCharacters.removeIf(curr -> character.getCharacterName().equals(curr.getCharacterName()));
		ServerCommunicationHandler.removeCharacter(character);
	}

	/**
	 * Sets the character to be viewed or edited.
	 * 
	 * @param character the selection character
	 */
	public void setCharacterSelection(Character character) {
		this.characterSelection = character;
	}

	/**
	 * Returns the selected character
	 * 
	 * @return characterSelection
	 */
	public Character getCharacterSelection() {
		return this.characterSelection;
	}
}
