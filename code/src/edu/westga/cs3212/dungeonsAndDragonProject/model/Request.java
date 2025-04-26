package edu.westga.cs3212.dungeonsAndDragonProject.model;

import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;

/**
 * Represents a request in the system (e.g. friend request, campaign invite, or
 * campaign join request). Extends original 'FriendRequest' to be more generic.
 * 
 * Additional logic can be added to handle notifications or timestamps.
 * 
 * @author MoriaEl
 * @version Spring 2025git status
 * 
 */
public class Request {

	private String fromAccountId;
	private String toAccountId;
	private RequestType requestType;
	private RequestStatus status;
	private String campaign;

	/**
	 * Creates a request with a default status of PENDING.
	 * 
	 * @param fromAccountId the account sending the request
	 * @param toAccountId   the account receiving the request
	 * @param requestType   type of request (FRIEND, CAMPAIGN_INVITE, CAMPAIGN_JOIN,
	 *                      etc.)
	 * @param campaign      reference of what campaign is the toAccountId being
	 *                      invited to
	 */
	public Request(String fromAccountId, String toAccountId, RequestType requestType, String campaign) {
		this.fromAccountId = fromAccountId;
		this.toAccountId = toAccountId;
		this.requestType = requestType;
		this.status = RequestStatus.PENDING;
		this.campaign = campaign;
	}

	/**
	 * Returns the ID of the account that sent the request.
	 * 
	 * @return the id of the requesting account
	 */
	public String getFromAccountId() {
		return this.fromAccountId;
	}

	/**
	 * Returns the ID of the account that received the request.
	 * 
	 * @return the id of the receiver account
	 */
	public String getToAccountId() {
		return this.toAccountId;
	}

	/**
	 * Returns the current status of this request.
	 * 
	 * @return the request status
	 */
	public RequestStatus getStatus() {
		return this.status;
	}

	/**
	 * Returns the type of this request (FRIEND, CAMPAIGN_INVITE, etc.).
	 * 
	 * @return the request type
	 */
	public RequestType getRequestType() {
		return this.requestType;
	}

	/**
	 * Accepts the request, setting the status to ACCEPTED.
	 * 
	 * @precondition none
	 * @postcondition getStatus = RequestStatus.Accept
	 */
	public void accept() {
		this.status = RequestStatus.ACCEPTED;
	}

	/**
	 * Rejects the request, setting the status to REJECTED.
	 * 
	 * @precondition none
	 * @postcondition getStatus = RequestStatus.Rejected
	 */
	public void reject() {
		this.status = RequestStatus.REJECTED;
	}

	/**
	 * Gets associated campaign
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return the associated campaign
	 */
	public String getCampaign() {
		return this.campaign;
	}

	@Override
	public String toString() {
		String user = "";
		for (AccountInfo curr : ServerCommunicationHandler.loadAccounts()) {
			if (curr.getAccountId().equals(this.fromAccountId)) {
				user = curr.getUsername();
			}
		}

		if (this.requestType == RequestType.CAMPAIGN_INVITE) {
			return "Campaign Invite from: " + user + " for campaign: " + this.campaign;
		}

		return "Request From: " + user;
	}
}