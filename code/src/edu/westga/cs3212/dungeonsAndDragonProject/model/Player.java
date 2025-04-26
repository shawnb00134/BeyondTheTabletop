package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player in the Dungeons & Dragons project. A player can create
 * and manage characters, send and receive friend requests, join and leave
 * campaigns, and create campaigns as a Dungeon Master.
 *
 * Future implementations may add more functionality.
 *
 * @author Shawn Bretthauer
 * @version Spring 2025
 */
public class Player {

	private AccountInfo accountInfo;
	private List<Character> characters;
	private List<Campaign> joinedCampaigns;

	/**
	 * Constructs a new Player with the given account information.
	 * 
	 * @param accountInfo the player's account details
	 * @precondition accountInfo != null
	 * @postcondition this.accountInfo == accountInfo && characters and
	 *                joinedCampaigns are initialized
	 */
	public Player(AccountInfo accountInfo) {
		if (accountInfo == null) {
			throw new IllegalArgumentException("Account information cannot be null");
		}
		this.accountInfo = accountInfo;
		this.characters = new ArrayList<>();
		this.joinedCampaigns = new ArrayList<>();
	}

	/**
	 * Gets the player's account information.
	 * 
	 * @return the account information
	 */
	public AccountInfo getAccountInfo() {
		return this.accountInfo;
	}

	/**
	 * Gets the list of characters owned by the player.
	 * 
	 * @return a list of characters
	 */
	public List<Character> getCharacters() {
		return new ArrayList<>(this.characters);
	}

	/**
	 * Creates a new character and adds it to the player's list of characters.
	 * 
	 * @param character the character to be added
	 * @precondition character != null
	 * @postcondition this.characters contains character
	 */
	public void createCharacter(Character character) {
		if (character == null) {
			throw new IllegalArgumentException("Character cannot be null");
		}
		character.setPlayerOwnership(this.accountInfo.getAccountId());
		this.characters.add(character);
	}

	/**
	 * Deletes a character from the player's list of characters.
	 * 
	 * @param character the character to be removed
	 * @precondition character != null && character exists in this.characters
	 * @postcondition this.characters no longer contains character
	 */
	public void deleteCharacter(Character character) {
		if (character == null || !this.characters.contains(character)) {
			throw new IllegalArgumentException("Invalid character");
		}
		this.characters.remove(character);
	}

	/**
	 * Joins a campaign.
	 * 
	 * @param campaign the campaign to join
	 * @precondition campaign != null
	 * @postcondition this.joinedCampaigns contains campaign
	 */
	public void joinCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("Campaign cannot be null");
		}
		this.joinedCampaigns.add(campaign);
	}

	/**
	 * Leaves a campaign.
	 * 
	 * @param campaign the campaign to leave
	 * @precondition campaign != null && campaign is in joinedCampaigns
	 * @postcondition this.joinedCampaigns no longer contains campaign
	 */
	public void leaveCampaign(Campaign campaign) {
		if (campaign == null || !this.joinedCampaigns.contains(campaign)) {
			throw new IllegalArgumentException("Invalid campaign");
		}
		this.joinedCampaigns.remove(campaign);
	}

	/**
	 * Creates a new campaign as a Dungeon Master.
	 * 
	 * @param campaign the campaign to create
	 * @precondition campaign != null
	 * @postcondition this player is set as the campaign's Dungeon Master
	 */
	public void createCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("Campaign cannot be null");
		}
		this.accountInfo.setDungeonMaster(true);
		if (this instanceof DungeonMaster) {
			campaign.setDungeonMaster((DungeonMaster) this);
		} else {
			campaign.setDungeonMaster(new DungeonMaster(this.accountInfo));
		}
	}
 
	/**
	 * gets the joined campaigns list
	 * 
	 * @precondition
	 * @postcondition
	 * @return joined campaigns
	 */
	public List<Campaign> getJoinedCampaigns() {
		return this.joinedCampaigns;
	}

	/**
	 * Returns a list of incoming requests.
	 *
	 * @return incoming requests
	 */
	public List<Request> getIncomingRequests() {
		return this.getAccountInfo().getIncomingRequests();
	}

	@Override
	public String toString() {
		return this.getAccountInfo().getUsername();
	}
}
