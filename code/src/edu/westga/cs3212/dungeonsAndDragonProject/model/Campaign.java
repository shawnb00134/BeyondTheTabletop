package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Campaign for users
 * 
 * @author Kelis Gates
 * @version Spring 2025
 * 
 */
public class Campaign {

	private String name;
	private String description;
	private ArrayList<Player> players;
	private DungeonMaster dungeonMaster;
	private boolean isOpen;
	private int campaignLimit;
	private String sharedNotesText;
	private String dungeonMasterNotes;
	private HashMap<String, String> campaignPlayers; 
	private ArrayList<String> specialPermissions;

	/**
	 * Creates a new Campaign
	 * 
	 * @precondition !name.isEmpty() && !description.isEmpty() !(dungeonMaster ==
	 *               null)
	 * @postcondition this.name = name this.description = description
	 * 
	 * @param name        the name of the campaign
	 * @param description the description of the campaign
	 * @param maxPlayers  limit of players that can join campaign
	 */
	public Campaign(String name, String description, int maxPlayers) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name can not be empty.");
		}

		if (description.isEmpty()) {
			throw new IllegalArgumentException("description can not be empty.");
		}

		this.name = name;
		this.description = description;
		this.players = new ArrayList<Player>();
		this.isOpen = true;
		this.campaignLimit = maxPlayers;
		this.campaignPlayers = new HashMap<>();
		this.sharedNotesText = "";
		this.dungeonMasterNotes = "";
		this.specialPermissions = new ArrayList<String>();
	}

	/**
	 * Gets the name of the campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return name of the campaign
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the campaign
	 * 
	 * @precondition none
	 * @postcondition this.name = name
	 * @param name name of the campaign
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description of the campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the description of the campaign
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the description of the campaign
	 * 
	 * @precondition none
	 * @postcondition this.description = description
	 * @param description the description of the campaign
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the list of players
	 * 
	 * @precondition none
	 * @postcondtion none
	 * 
	 * @return the list of players associated with campaign
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Sets the list of players
	 * 
	 * @precondition none
	 * @postcondtion this.players == players
	 * 
	 * @param players array list of players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	/**
	 * Gets the dungeon master
	 * 
	 * @precondition none
	 * @postcondtion none
	 * 
	 * @return the dungeon master over campaign
	 */
	public DungeonMaster getDungeonMaster() {
		return this.dungeonMaster;
	}

	/**
	 * Sets the dungeon master of the campaign
	 * 
	 * @precondition none
	 * @postcondtion this.dungeonMaster = dungeonMaster
	 * 
	 * @param dungeonMaster the dungeonMaster over the campaign
	 */
	public void setDungeonMaster(Player dungeonMaster) {
		if (dungeonMaster == null) {
			throw new IllegalArgumentException("Dungeon Master cannot be null");
		}
		if (dungeonMaster instanceof DungeonMaster) {
			this.dungeonMaster = (DungeonMaster) dungeonMaster;
		} else {
			this.dungeonMaster = new DungeonMaster(dungeonMaster.getAccountInfo());
		}
	}

	/**
	 * Gets player limit of campaign
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @return player limit of campaign
	 */
	public int getCampaignLimit() {
		return this.campaignLimit;
	}

	/**
	 * Set campaign player limit
	 * 
	 * @precondition none
	 * @postcondtion this.campaignlimit = campaignlimit;
	 * @param campaignLimit max players allowed in campaign
	 */
	public void setCampaignLimit(int campaignLimit) {
		this.campaignLimit = campaignLimit;
	}

	/**
	 * Adds player to campaign
	 * 
	 * @precondition none
	 * @postcondtion this.players.size() = this.players.size() + 1
	 * @param player the player invited
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}

	/**
	 * Removes a player from the campaign
	 * 
	 * @precondition none
	 * @postcondtion this.players.size() = this.players.size() - 1
	 * @param player the player to be removed from campaign
	 */
	public void removePlayer(Player player) {
		int index = 0;
		for (Player currPlayer : this.players) {
			if (currPlayer == player) {
				this.players.remove(index);
				break;
			}
			
			index++;
		}
	}

	/**
	 * Removes all players from the campaign
	 * 
	 * @precondition none
	 * @postcondtion this.players.size() == 0
	 */
	public void removeAllPlayers() {
		for (int index = this.players.size() - 1; index >= 0; index--) {
			this.players.remove(index);
		}
	}

	/**
	 * checks if campaign is open
	 * 
	 * @precondition none
	 * @postcondtion none
	 * 
	 * @return boolean if campaign is open or not
	 */
	public boolean isOpen() {
		return this.isOpen;
	}

	/**
	 * closes the campaign
	 * 
	 * @precondition none
	 * @postcondtion this.isOpen == false
	 * 
	 */
	public void closeCampaign() {
		this.isOpen = false;
	}

	/**
	 * Checks if campaign is at limit
	 * 
	 * @precondition none
	 * @postcondtion if limit is reached campaign is closed
	 */
	public void checkCampaignLimit() {
		if (this.campaignLimit == this.players.size()) {
			this.closeCampaign();
		} else {
			this.isOpen = true;
		}
	}

	/**
	 * Checks if account is already a player in campaign
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @param info account object
	 * @return true if already in campaign and false otherwise
	 */
	public boolean checkPlayerAlreadyInCampaign(AccountInfo info) {
		for (Player curr : this.players) {
			if (curr != null && curr.getAccountInfo().getUsername().equals(info.getUsername())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if character is added to campaign
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @param player player object
	 * @return true if found in campaign and false otherwise
	 */
	public boolean checkIfCharacterIsAdded(Player player) {
		String playerID = player.getAccountInfo().getAccountId();
		for (String curr : this.getCampaignPlayers().keySet()) {
			if (curr.equals(playerID)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets campaign players with their character
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @return hash map of campaign players
	 */
	public HashMap<String, String> getCampaignPlayers() {
		return this.campaignPlayers;
	}

	/**
	 * Sets the map of players with their character
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @param campaignPlayers hash map of campaign players
	 */
	public void setCampaignPlayers(HashMap<String, String> campaignPlayers) {
		this.campaignPlayers = campaignPlayers;
	}

	/**
	 * Add character and player to campaign map
	 * 
	 * @precondition none
	 * @postcondtion this.campaignPlayers.size() + 1
	 * 
	 * @param characterName character name
	 * @param playerID      player account id in campaign
	 */
	public void addCharacterToPlayer(String characterName, String playerID) {
		this.campaignPlayers.put(playerID, characterName);
	}

	/**
	 * Gets the campaigns shared notes
	 * 
	 * @precondition none
	 * @postcondtion none
	 * @return campaign's shared notes
	 */
	public String getSharedNotesText() {
		return this.sharedNotesText;
	}

	/**
	 * Sets shared notes for the campaign
	 * 
	 * @precondition none
	 * @postcondtion updated campaign's shared notes
	 * @param text the updated shared notes
	 */
	public void setSharedNotesText(String text) {
		this.sharedNotesText = text;
	}

    
    /**gets the Dungeon Master notes
	 * @precondition none
	 * @postcondtion none
	 * @return Dungeon Master notes
	 */
	public String getDungeonMasterNotesText() {
        return this.dungeonMasterNotes;
    }
	
	/**sets Dungeon Master notes for the campaign
	 * @precondition none
	 * @postcondtion updated Dungeon Master shared notes
	 * @param text the updated Dungeon Master notes
	 */
    public void setDungeonMasterNotesText(String text) {
        this.dungeonMasterNotes = text;   
    }

	/**
	 * Gets the list of players that have special permissions within campaign
	 * 
	 * @precondition none
	 * @postcondtion none
	 * 
	 * @return the list of players that have special permissions within campaign
	 */
	public ArrayList<String> getSpecialPlayers() {
		return this.specialPermissions;
	}

	/**
	 * Adds to the list of players that have special permissions within campaign
	 * 
	 * @precondition none
	 * @postcondtion this.specialPermissions.size() + 1
	 * 
	 * @param playerID the account ID of the player to be added
	 */
	public void addPlayerToSpecialPermissions(String playerID) {
		this.specialPermissions.add(playerID);
	}

	/**
	 * Removes from the list of players that have special permissions within
	 * campaign
	 * 
	 * @precondition none
	 * @postcondtion this.specialPermissions.size() - 1
	 * 
	 * @param playerID the account ID of the player to be added
	 */
	public void removeSpecialPermission(String playerID) {
		this.specialPermissions.remove(playerID);
	}

	/**
	 * Checks the list of players that have special permissions within campaign
	 * 
	 * @precondition none
	 * @postcondtion none
	 * 
	 * @param playerID the account ID of the player to be checked
	 * @return true if player has special permissions
	 */
	public boolean checkIfPlayerHasSpecialPermissions(String playerID) {
		for (String currPlayerID : this.specialPermissions) {
			if (currPlayerID.equals(playerID)) {
				return true;
			}
		}

		return false;
	}
}
