package edu.westga.cs3212.dungeonsAndDragonProject.viewmodel;

import java.util.ArrayList;
import java.util.List;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

/**
 * Campaign Creation ViewModel
 * 
 * @author Kelis Gates
 * 
 * @version Spring 2025
 */
public class CampaignCreationViewModel {

	private StringProperty name;
	private StringProperty description;
	private StringProperty campaignLimit;

	private ListProperty<Player> players;
	private ObjectProperty<Campaign> campaign;

	private Player playerOfCurrentAccount;
	private ListProperty<Campaign> campaigns;
	private ListProperty<Request> incomingReq;
	private ListProperty<Character> characters;
	private boolean requestCompleted;

	private boolean isInTestMode;

	/**
	 * Initiates viewmodel
	 * 
	 * @precondition none
	 * @postcondition fields initialized
	 * @param isInTestMode if running junit test the code is true otherwise false
	 */
	public CampaignCreationViewModel(boolean isInTestMode) {
		this.isInTestMode = isInTestMode;

		// campaign creation view
		this.name = new SimpleStringProperty("");
		this.description = new SimpleStringProperty("");
		this.campaignLimit = new SimpleStringProperty("");

		Player player = new Player(SystemContextViewModel.getInstance().getCurrentAccount());
		this.playerOfCurrentAccount = player;

		// campaign lobby view
		this.players = new SimpleListProperty<Player>(FXCollections.observableArrayList(new ArrayList<Player>()));
		this.campaign = new SimpleObjectProperty<>();

		// campaign manager view
		this.campaigns = new SimpleListProperty<Campaign>(FXCollections.observableArrayList(new ArrayList<Campaign>()));
		this.loadJoinedCampaigns();

		this.incomingReq = new SimpleListProperty<Request>(FXCollections.observableArrayList(new ArrayList<Request>()));
		for (Request curr : SystemContextViewModel.getInstance().getCurrentAccount().getIncomingRequests()) {
			this.incomingReq.add(curr);
		}

		this.characters = new SimpleListProperty<Character>(
				FXCollections.observableArrayList(new ArrayList<Character>()));
		this.loadCharacters();

		this.setRequestCompleted(false);
	}

	/**
	 * gets name of campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return name of campaign
	 */
	public StringProperty getName() {
		return this.name;
	}

	/**
	 * gets description of campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return name of campaign
	 */
	public StringProperty getDescription() {
		return this.description;
	}

	/**
	 * gets limit number of campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return limit of campaign
	 */
	public StringProperty getCampaignLimit() {
		return this.campaignLimit;
	}

	/**
	 * gets list of Characters
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return list of Characters
	 */
	public ListProperty<Character> getCharacters() {
		return this.characters;
	}

	/**
	 * gets list of campaigns
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return list of campaign
	 */
	public ListProperty<Campaign> getCampaigns() {
		return this.campaigns;
	}

	/**
	 * gets player
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return player
	 */
	public Player getPlayerOfCurrentAccount() {
		return this.playerOfCurrentAccount;
	}

	/**
	 * gets list of players in campaigns
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return list of players in campaign
	 * @param campaign the associated campaign
	 */
	public ListProperty<Player> getPlayersInCampaign(Campaign campaign) {
		for (Player curr : campaign.getPlayers()) {
			this.players.add(curr);
		}
		return this.players;
	}

	/**
	 * gets list of players in campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return list of players in campaign
	 */
	public ListProperty<Player> getPlayers() {
		return this.players;
	}

	/**
	 * Get campaign for campaign lobby
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return campaign for campaign lobby
	 */
	public ObjectProperty<Campaign> getCampaign() {
		return this.campaign;
	}

	/**
	 * Sets campaign for campaign lobby
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param campaign for campaign lobby
	 */
	public void setCampaign(Campaign campaign) {
		this.campaign.set(campaign);
	}

	/**
	 * Gets the incoming request
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return a list of incoming request
	 */
	public ListProperty<Request> getIncomingReq() {
		return this.incomingReq;
	}

	/**
	 * Sets the list of incoming request
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param requests a list of requests
	 */
	public void setIncomingReq(ListProperty<Request> requests) {
		this.incomingReq = requests;
	}

	/**
	 * get if the request was completed
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return true if request has been completed otherwise false if not
	 */
	public boolean isRequestCompleted() {
		return this.requestCompleted;
	}

	/**
	 * sets if request was completed
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param requestCompleted request was completed
	 */
	public void setRequestCompleted(boolean requestCompleted) {
		this.requestCompleted = requestCompleted;
	}

	/**
	 * Loads campaign data for editing campaign
	 * 
	 * @precondition campaign != null
	 * @postcondition
	 * 
	 * @param campaign the campaign that is being edited
	 */
	public void loadCampaignData(Campaign campaign) {
		if (campaign != null) {
			this.name.set(campaign.getName());
			this.description.set(campaign.getDescription());
			this.campaignLimit.set(String.valueOf(campaign.getCampaignLimit()));
		}
	}

	/**
	 * Creates a new campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 */
	public void createCampaign() {
		int limit = Integer.parseInt(this.getCampaignLimit().get());
		Campaign campaign = new Campaign(this.name.get(), this.description.get(), limit);

		this.playerOfCurrentAccount.createCampaign(campaign);
		if (!this.isInTestMode) {
			ServerCommunicationHandler.addCampaign(campaign);
		}

		this.campaigns.add(campaign);
		this.playerOfCurrentAccount.joinCampaign(campaign);
	}

	/**
	 * send invite to account
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param username account that is being invited
	 * @param campaign current campaign
	 * @param stage    the stage
	 * @return account info (for testing)
	 * 
	 */
	public AccountInfo sendInviteToPlayer(String username, Campaign campaign, Stage stage) {
		if (!this.isInTestMode) {
			List<AccountInfo> allAccounts = ServerCommunicationHandler.loadAccounts();
			for (AccountInfo account : allAccounts) {
				if (account.getUsername().equals(username)) {
					this.campaign.get().getDungeonMaster().sendCampaignInvite(username, campaign, stage);
					return account;
				}
			}
			return null;
		} else {
			return null;
		}
	}

	private void loadJoinedCampaigns() {
		AccountInfo currentAccount = SystemContextViewModel.getInstance().getCurrentAccount();
		if (!this.isInTestMode) {
			List<Campaign> loadedCampaigns = ServerCommunicationHandler.loadAccountCampaigns(currentAccount);
			if (loadedCampaigns != null && !loadedCampaigns.isEmpty()) {
				for (Campaign curr : loadedCampaigns) {
					this.campaigns.add(curr);
				}
			}
		}
	}

	private void loadCharacters() {
		if (!this.isInTestMode) {
			String playerId = this.playerOfCurrentAccount.getAccountInfo().getAccountId();
			List<Character> loadedCharacters = ServerCommunicationHandler
					.loadCharacters(this.playerOfCurrentAccount.getAccountInfo());
			for (Character curr : loadedCharacters) {
				if (curr.getPlayerOwnerID().equals(playerId)) {
					boolean result = this.checkIfCharacterIsInAnotherCampaign(curr, playerId);
					if (!result) {
						this.characters.add(curr);
					}
				}
			}
		}
	}

	private boolean checkIfCharacterIsInAnotherCampaign(Character curr, String id) {
		if (!this.isInTestMode) {
			for (Campaign cam : ServerCommunicationHandler.loadCampaigns()) {
				for (String currChar : cam.getCampaignPlayers().values()) {
					if (currChar.equals(curr.getCharacterName())) {
						if (curr.getPlayerOwnerID().equals(id)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * overwrites accounts in request and campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param request    request sent to account
	 * @param isAccepted request status accepted or not
	 */
	public void overWriteCampaignAndRequests(Request request, boolean isAccepted) {
		AccountInfo recipient = this.findAccountById(this.playerOfCurrentAccount.getAccountInfo().getAccountId());
		int index = this.findIndexOfRemovedRequest(recipient.getIncomingRequests(), request);
		if (index >= 0 && index < recipient.getIncomingRequests().size()) {
			recipient.getIncomingRequests().remove(index);
			this.playerOfCurrentAccount.getAccountInfo().getIncomingRequests().remove(request);
		}

		AccountInfo sender = this.findAccountById(request.getFromAccountId());
		int index2 = this.findIndexOfRemovedRequest(sender.getOutgoingRequests(), request);
		if (index2 >= 0 && index2 < sender.getOutgoingRequests().size()) {
			sender.getOutgoingRequests().remove(index2);
		}

		if (!this.isInTestMode) {
			if (isAccepted && request.getRequestType() == RequestType.CAMPAIGN_INVITE) {
				Campaign campaign = this.findCampaignByName(request.getCampaign());
				if (campaign != null) {
					ServerCommunicationHandler.acceptInvite(recipient, sender, campaign);
				}
			} else if (request.getRequestType() == RequestType.CAMPAIGN_INVITE) {
				ServerCommunicationHandler.denyInvite(recipient, sender);
			}
		}
	}

	/**
	 * Finds index of request that is to be removed
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param requests a list of requests
	 * @param request  the request to be removed
	 * @return index in the list of requests
	 */
	public int findIndexOfRemovedRequest(List<Request> requests, Request request) {
		int index = 0;
		for (int loopIndex = 0; loopIndex < requests.size(); loopIndex++) {
			if (requests.get(loopIndex).equals(request)) {
				index = loopIndex;
			}
		}
		return index;
	}

	/**
	 * Finds account by their id
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param id the id of the account
	 * @return account
	 */
	public AccountInfo findAccountById(String id) {
		AccountInfo result = null;
		List<AccountInfo> loadedAccounts = ServerCommunicationHandler.loadAccounts();
		for (AccountInfo curr : loadedAccounts) {
			if (curr.getAccountId().equals(id)) {
				result = curr;
			}
		}
		return result;
	}

	/**
	 * Finds campaign by their name
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param campaignName the campaign name
	 * @return campaign object
	 */
	public Campaign findCampaignByName(String campaignName) {
		Campaign result = null;
		List<Campaign> loadedCampaigns = ServerCommunicationHandler.loadCampaigns();
		for (Campaign curr : loadedCampaigns) {
			if (curr.getName().equals(campaignName)) {
				result = curr;
			}
		}
		return result;
	}

	/**
	 * Edits an existing campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param selectedCampaign the campaign to be edited
	 */
	public void editCampaign(Campaign selectedCampaign) {
		List<Campaign> campaignList = this.campaigns.get();
		Campaign toUpdate;
		if (!this.isInTestMode) {
			toUpdate = ServerCommunicationHandler.getCampaignFromServer(selectedCampaign);
		} else {
			toUpdate = selectedCampaign;
		}

		if (toUpdate != null) {
			campaignList.remove(toUpdate);

			toUpdate.setName(this.getName().get());
			toUpdate.setDescription(this.getDescription().get());
			toUpdate.setCampaignLimit(Integer.parseInt(this.getCampaignLimit().get()));
			toUpdate.checkCampaignLimit();

			campaignList.add(toUpdate);
			if (!this.isInTestMode) {
				ServerCommunicationHandler.editCampaign(selectedCampaign, toUpdate);
			}
			campaignList.remove(selectedCampaign);
		}
	}

	/**
	 * Checks if the specified account is the dungeon master of any campaign.
	 * 
	 * @param account the account to check.
	 * @return true if the account is a dungeon master; false otherwise.
	 */
	public boolean checkIfDungeonMaster(AccountInfo account) {
		if (!this.getCampaigns().isEmpty()) {
			for (Campaign curr : this.getCampaigns()) {
				if (curr.getDungeonMaster().getAccountInfo().getUsername().equals(account.getUsername())) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * Finds the character
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param charName the name of the character
	 * @return the character or null if character not found
	 */
	public Character findCharacter(String charName) {
		for (Character curr : ServerCommunicationHandler
				.loadCharacters(SystemContextViewModel.getInstance().getCurrentAccount())) {
			if (curr.getCharacterName().equals(charName)) {
				return curr;
			}
		}
		return null;
	}
}
