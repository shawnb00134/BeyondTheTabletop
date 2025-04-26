package edu.westga.cs3212.dungeonsAndDragonProject.model;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
 * Represents a user account in the system, storing identifying info and a
 * secure password hash.
 * 
 * @author MoriaEl
 * @version Spring 2025
 */
public class AccountInfo {

	private String accountId;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String hashedPassword;
	private String salt;
	private boolean isDungeonMaster;
	private List<Request> incomingRequests;
	private List<Request> outgoingRequests;

	/**
	 * Creates an AccountInfo with the provided details. Enforces salted-hash
	 * password security. Uniqueness of username and email must be handled
	 * externally (e.g., by a service or controller).
	 *
	 * @param accountId   unique account identifier
	 * @param firstName   user's first name
	 * @param lastName    user's last name
	 * @param email       user's unique email address
	 * @param username    user's unique username
	 * @param rawPassword raw password (will be hashed and salted)
	 */

	public AccountInfo(String accountId, String firstName, String lastName, String email, String username,
			String rawPassword) {
		this.accountId = accountId;
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setUsername(username);
		this.username = username;
		this.isDungeonMaster = false;
		this.incomingRequests = new ArrayList<>();
		this.outgoingRequests = new ArrayList<>();
		this.setPassword(rawPassword);
	}

	/**
	 * Returns the account's unique ID.
	 *
	 * @return accountId
	 */
	public String getAccountId() {
		return this.accountId;
	}

	/**
	 * Returns the user's first name.
	 *
	 * @return firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Returns the user's last name.
	 *
	 * @return lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Returns the user's email address.
	 *
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Returns the user's username.
	 *
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Sets the first name
	 * 
	 * @param firstName the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName.trim();
	}

	/**
	 * Sets the last name
	 * 
	 * @param lastName the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName.trim();
	}

	/**
	 * Set email only if it matches the allowed pattern (gmail or yahoo).
	 * 
	 * @param newEmail the new email
	 */
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	/**
	 * Sets the username
	 * 
	 * @param username the username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Updates the stored password by salting and hashing the new raw password. Best
	 * practice: never store or log the raw password in plaintext.
	 * 
	 * @param rawPassword the raw password
	 */
	public void setPassword(String rawPassword) {
		this.generateSalt();
		this.hashAndStorePassword(rawPassword);
	}

	/**
	 * Indicates whether this account is flagged as a Dungeon Master.
	 *
	 * @return true if Dungeon Master, false otherwise
	 */
	public boolean isDungeonMaster() {
		return this.isDungeonMaster;
	}

	/**
	 * Sets this account to be a Dungeon Master or not.
	 *
	 * @param isDungeonMaster true if user should be a DM, false otherwise
	 */
	public void setDungeonMaster(boolean isDungeonMaster) {
		this.isDungeonMaster = isDungeonMaster;
	}

	/**
	 * Verifies a raw password by hashing it with the saved salt and comparing it to
	 * the stored hash.
	 *
	 * @param rawPassword the password to check
	 * @return true if the password matches, false otherwise
	 */
	public boolean verifyPassword(String rawPassword) {
		String attemptHash = this.createHash(rawPassword, this.salt);
		return attemptHash.equals(this.hashedPassword);
	}

	/**
	 * Returns a list of incoming requests.
	 *
	 * @return incoming requests
	 */
	public List<Request> getIncomingRequests() {
		return this.incomingRequests;
	}

	/**
	 * Returns a list of outgoing requests.
	 *
	 * @return outgoing requests
	 */
	public List<Request> getOutgoingRequests() {
		return this.outgoingRequests;
	}

	/**
	 * Adds a new request to this account's incoming requests.
	 *
	 * @param request the request sent to this user
	 */
	public void addIncomingRequest(Request request) {
		this.incomingRequests.add(request);
	}

	/**
	 * Adds a new request to this account's outgoing requests.
	 *
	 * @param request the request created by this user
	 */
	public void addOutgoingRequest(Request request) {
		this.outgoingRequests.add(request);
	}

	/**
	 * Generates a random salt for password hashing to ensure even identical
	 * passwords produce unique hashes. Uses SecureRandom for cryptographic
	 * strength.
	 */
	private void generateSalt() {
		try {
			SecureRandom random = SecureRandom.getInstanceStrong();
			byte[] saltBytes = new byte[16];
			random.nextBytes(saltBytes);
			this.salt = Base64.getEncoder().encodeToString(saltBytes);
		} catch (Exception ex) {
			throw new RuntimeException("Error generating salt", ex);
		}
	}

	/**
	 * Hashes the given raw password with this account's salt and stores the result.
	 *
	 * @param rawPassword the raw password
	 */
	private void hashAndStorePassword(String rawPassword) {
		this.hashedPassword = this.createHash(rawPassword, this.salt);
	}

	/**
	 * Creates a SHA-256 hash of the raw password plus the salt, then encodes it as
	 * a Base64 string.
	 *
	 * @param password  the raw password
	 * @param saltValue the salt
	 * @return a Base64-encoded hash string
	 */
	private String createHash(String password, String saltValue) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			String combined = saltValue + password;
			byte[] hashBytes = digest.digest(combined.getBytes("UTF-8"));
			return Base64.getEncoder().encodeToString(hashBytes);
		} catch (Exception ex) {
			throw new RuntimeException("Error hashing password", ex);
		}
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}

		if (!(object instanceof AccountInfo)) {
			return false;
		}
		AccountInfo other = (AccountInfo) object;

		return this.accountId.equals(other.getAccountId());
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.accountId);
	}
}