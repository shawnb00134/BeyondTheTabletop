package edu.westga.cs3212.dungeonsAndDragonProject.model;

import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Represents a Dungeon Master who manages campaigns.
 * 
 * @author MoriaEl
 * @version Spring 2025
 */
public class DungeonMaster extends Player {

	/**
	 * Constructs a Dungeon Master from an existing Player.
	 * 
	 * @param accountInfo the account information
	 */
	public DungeonMaster(AccountInfo accountInfo) {
		super(accountInfo);
		this.getAccountInfo().setDungeonMaster(true);
	}

	/**
	 * Sends a campaign invite request to a player by username. If the username does
	 * not exist, shows a popup message for 3 seconds.
	 * 
	 * @param username the username of the player to invite
	 * @param campaign the campaign to join
	 * @param parentStage the stage that contains the campaign manager
	 */
	public void sendCampaignInvite(String username, Campaign campaign, Stage parentStage) {
		if (username == null || username.trim().isEmpty() || campaign == null) {
			throw new IllegalArgumentException("Username and campaign cannot be null or empty");
		}

		Player recipient = ServerCommunicationHandler.findPlayerByUsername(username);

		if (recipient == null) {
			this.showTemporaryPopup("Player not found", "No player with the username '" + username + "' exists.",
					parentStage);
			return;
		}

		Request inviteRequest = new Request(
		        this.getAccountInfo().getAccountId(),
		        recipient.getAccountInfo().getAccountId(),
		        RequestType.CAMPAIGN_INVITE,
		        campaign.getName()
		    );

		recipient.getAccountInfo().addIncomingRequest(inviteRequest);
	    this.getAccountInfo().addOutgoingRequest(inviteRequest);
	    
	    ServerCommunicationHandler.sendOutInvite(recipient.getAccountInfo(), campaign.getDungeonMaster().getAccountInfo());
	    
	}

	/**
	 * Deletes a campaign.
	 * 
	 * @param campaign the campaign to delete
	 */
	public void deleteCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("Campaign cannot be null");
		}
		campaign.removeAllPlayers();
	}

	/**
	 * Transfers ownership of a campaign to another Dungeon Master.
	 * 
	 * @param campaign         the campaign to transfer
	 * @param newDungeonMaster the new Dungeon Master
	 */
	public void transferCampaignOwnership(Campaign campaign, DungeonMaster newDungeonMaster) {
		if (campaign == null || newDungeonMaster == null) {
			throw new IllegalArgumentException("Campaign and new Dungeon Master cannot be null");
		}
		campaign.setDungeonMaster(newDungeonMaster);
	}

	private void showTemporaryPopup(String title, String message, Stage parentStage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(parentStage);

		new Thread(() -> {
			try {
				Thread.sleep(3000);
				Platform.runLater(alert::close);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}).start();

		alert.show();
	}
}