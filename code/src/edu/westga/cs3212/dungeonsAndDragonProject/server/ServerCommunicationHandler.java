package edu.westga.cs3212.dungeonsAndDragonProject.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Modifier;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Resource;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Handles server communication via JSON serialization. Ensures data integrity
 * and SRP compliance.
 *
 * @author YourName
 * @version Spring 2025
 */
public class ServerCommunicationHandler {
	private static final Gson GSON = new GsonBuilder().excludeFieldsWithModifiers(Modifier.FINAL, Modifier.STATIC)
			.create();
	private static final Logger LOGGER = Logger.getLogger(ServerCommunicationHandler.class.getName());
	private static String accountFile = "src/edu/westga/cs3212/dungeonsAndDragonProject/assets/json/accounts.json";
	private static String campaignFile = "src/edu/westga/cs3212/dungeonsAndDragonProject/assets/json/campaigns.json";
	private static String friendRequestFile = "\"src/edu/westga/cs3212/dungeonsAndDragonProject/assets/json/friendRequest.json";
	private static String racesFile = "src/edu/westga/cs3212/dungeonsAndDragonProject/assets/json/races.json";
	private static String classFile = "src/edu/westga/cs3212/dungeonsAndDragonProject/assets/json/roles.json";

	public static final String UPLOAD_RESOURCE = "upload resource";
	public static final String LIST_RESOURCES = "list resources";
	public static final String DOWNLOAD_RESOURCE = "download resource";
	public static final String DELETE_RESOURCE = "delete resource";
	public static final String TOGGLE_VISIBILITY = "toggle visibility";

	/**
	 * Authenticates a user login by checking stored credentials.
	 *
	 * @param username    The username entered by the user
	 * @param rawPassword The password entered by the user
	 * @return The AccountInfo object if authentication is successful, null
	 *         otherwise
	 */
	public static AccountInfo authenticateUser(String username, String rawPassword) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "login");
		requestObject.addProperty("username", username);
		requestObject.addProperty("password", rawPassword);

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);

		JsonObject jsonResponseObject = JsonParser.parseString(response).getAsJsonObject();
		String successCode = jsonResponseObject.get("success code").getAsString();

		if ("success".equals(successCode)) {
			AccountInfo accountInfo = GSON.fromJson(jsonResponseObject.get("accountInfo"), AccountInfo.class);
			return accountInfo;
		} else {
			return null;
		}
	}

	/**
	 * loads current account's associated campaigns by checking stored credentials.
	 * 
	 * @precondition account != null
	 * @postcondition none
	 * @param account the current account
	 * 
	 * @return The list of campaigns that the account is in, can be empty
	 * 
	 */
	public static List<Campaign> loadAccountCampaigns(AccountInfo account) {
		if (account == null) {
			throw new IllegalArgumentException("account can not be null.");
		}
		List<Campaign> campaigns = new ArrayList<Campaign>();

		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "list campaigns");
		requestObject.addProperty("username", account.getUsername());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);

		JsonObject jsonResponseObject = JsonParser.parseString(response).getAsJsonObject();
		String successCode = jsonResponseObject.get("success code").getAsString();

		if ("success".equals(successCode)) {
			JsonArray joinedCampaignsArray = jsonResponseObject.getAsJsonArray("joinedCampaigns");
			Type listType = new TypeToken<List<Campaign>>() {
			}.getType();
			List<Campaign> joinedCampaigns = GSON.fromJson(joinedCampaignsArray, listType);
			return joinedCampaigns;
		}

		return campaigns;
	}

	/**
	 * Adds a newly created campaign to the json files
	 * 
	 * @precondition campaign != null
	 * @postcondition campaign file overwritten
	 * 
	 * @param campaign the new campaign being added to json file
	 */
	public static void addCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("campaign can not be null.");
		}
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "create campaign");
		requestObject.add("campaign", GSON.toJsonTree(campaign));
		requestObject.addProperty("name", campaign.getName());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * removes a campaign
	 * 
	 * @precondition none
	 * @postcondition campaign file overwritten
	 * @param campaign to be removed
	 */
	public static void removeCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("campaign must not be null.");
		}
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "remove campaign");
		requestObject.addProperty("name", campaign.getName());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Gets specified campaign using server
	 * 
	 * @precondition none
	 * @postcondition none
	 * @param campaign the specified campaign
	 * @return the specified campaign
	 */
	public static Campaign getCampaignFromServer(Campaign campaign) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "get campaign");
		requestObject.addProperty("name", campaign.getName());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);

		JsonObject jsonResponseObject = JsonParser.parseString(response).getAsJsonObject();
		String successCode = jsonResponseObject.get("success code").getAsString();

		if ("success".equals(successCode)) {
			Campaign result = GSON.fromJson(jsonResponseObject.get("campaign result"), Campaign.class);
			return result;
		} else {
			return null;
		}

	}

	/**
	 * Sends campaign invite to server to overwrite account files
	 * Verifies that the dm is a real account to prevent testing from triggering this method.
	 * 
	 * @precondition none
	 * @postcondition account file overwritten
	 * @param recipient the account that is receiving the invite
	 * @param dm        the dungeon master that is sending the invite
	 */
	public static void sendOutInvite(AccountInfo recipient, AccountInfo dm) {
		if (loadAccounts().contains(dm)) {
			JsonObject requestObject = new JsonObject();
			requestObject.addProperty("type", "invite to campaign");
			requestObject.addProperty("recipient username", recipient.getUsername());
			requestObject.add("recipient", GSON.toJsonTree(recipient));
			requestObject.addProperty("dungeon master username", dm.getUsername());
			requestObject.add("dungeon master", GSON.toJsonTree(dm));

			String request = GSON.toJson(requestObject);
			String response = Client.sendRequest(request);
			//System.out.println("Response from server: " + response);
		}

		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "invite to campaign");
		requestObject.addProperty("recipient username", recipient.getUsername());
		requestObject.add("recipient", GSON.toJsonTree(recipient));
		requestObject.addProperty("dungeon master username", dm.getUsername());
		requestObject.add("dungeon master", GSON.toJsonTree(dm));

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Tells the server that the recipient accepts the campaign invite from the
	 * sender
	 * 
	 * @precondition none
	 * @postcondition account file overwritten and campaign file overwritten
	 * @param recipient the account that received the invite
	 * @param sender    the account that is sending the invite
	 * @param campaign the campaign associated with the invite
	 */
	public static void acceptInvite(AccountInfo recipient, AccountInfo sender, Campaign campaign) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "accept request");
		requestObject.addProperty("recipient username", recipient.getUsername());
		requestObject.add("recipient", GSON.toJsonTree(recipient));
		requestObject.addProperty("sender username", sender.getUsername());
		requestObject.add("sender", GSON.toJsonTree(sender));
		requestObject.addProperty("campaign name", campaign.getName());
		requestObject.add("campaign", GSON.toJsonTree(campaign));

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Tells the server that the recipient denies the campaign invite from the
	 * sender
	 * 
	 * @precondition none
	 * @postcondition account file overwritten and campaign file overwritten
	 * @param recipient the account that received the invite
	 * @param sender    the account that is sending the invite
	 */
	public static void denyInvite(AccountInfo recipient, AccountInfo sender) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "accept request");
		requestObject.addProperty("recipient username", recipient.getUsername());
		requestObject.add("recipient", GSON.toJsonTree(recipient));
		requestObject.addProperty("sender username", sender.getUsername());
		requestObject.add("sender", GSON.toJsonTree(sender));

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Edits the oldCampaign
	 * 
	 * @precondition none
	 * @postcondition campaign file overwritten
	 * @param oldCampaign the existing campaign
	 * @param newCampaign the campaign with updated changes
	 */
	public static void editCampaign(Campaign oldCampaign, Campaign newCampaign) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "update campaign");
		requestObject.addProperty("campaign name", oldCampaign.getName());
		requestObject.add("new campaign", GSON.toJsonTree(newCampaign));
		requestObject.addProperty("new name", newCampaign.getName());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Overwrite the campaign.
	 * 
	 * @precondition none
	 * @postcondition campaign file overwritten
	 * 
	 * @param campaign the account info to override
	 */
	public static void overwriteCampaign(Campaign campaign) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "overwrite campaign");
		requestObject.addProperty("name", campaign.getName());
		requestObject.add("campaign", GSON.toJsonTree(campaign));

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Gets every outgoing request from the account.
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param info the account info
	 * @return outgoing requests of the account.
	 */
	public static List<Request> getOutgoingRequest(AccountInfo info) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "list requests");
		requestObject.addProperty("username", info.getUsername());
		List<Request> requests = new ArrayList<Request>();

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);

		JsonObject jsonResponseObject = JsonParser.parseString(response).getAsJsonObject();
		String successCode = jsonResponseObject.get("success code").getAsString();

		if ("success".equals(successCode)) {
			JsonArray requestArray = jsonResponseObject.getAsJsonArray("outgoingRequests");
			Type listType = new TypeToken<List<Request>>() {
			}.getType();
			List<Request> outgoing = GSON.fromJson(requestArray, listType);
			return outgoing;
		}

		return requests;
	}

	/**
	 * Saves account information to a file.
	 *
	 * @param accounts List of AccountInfo objects
	 */
	public static void saveAccounts(List<AccountInfo> accounts) {
		writeToFile(accountFile, accounts);
	}

	/**
	 * Loads account information from a file.
	 *
	 * @return List of AccountInfo objects
	 */
	public static List<AccountInfo> loadAccounts() {
		return readNDJSONFromFile(accountFile, AccountInfo.class);
	}

	/**
	 * Saves characters to a file.
	 *
	 * @param characters List of Character objects
	 */
	public static void saveCharacters(List<Character> characters) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "save character");
		requestObject.addProperty("characters", GSON.toJson(characters));

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Saves a single character to a file
	 * 
	 * @precondition none
	 * @postcondition character and account file overwritten
	 * 
	 * @param character  the character to save
	 * @param preExiting  whether or not the character has been saved before
	 */
	public static void saveCharacter(Character character, boolean preExiting) {
		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "create character");
		requestObject.add("character", GSON.toJsonTree(character));
		requestObject.addProperty("characterName", character.getCharacterName());
		requestObject.addProperty("playerOwnerID",
				SystemContextViewModel.getInstance().getCurrentAccount().getAccountId());
		requestObject.addProperty("preExisting", preExiting);

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Removes a character. 
	 * 
	 * @precondition character != null
	 * @postcondition character and account file overwritten
	 * 
	 * @param character the character to remove.
	 */
	public static void removeCharacter(Character character) {
		if (character == null) {
			throw new IllegalArgumentException("Character cannot be null");
		}

		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "delete character");
		requestObject.addProperty("characterName", character.getCharacterName());
		requestObject.addProperty("playerOwnerID",
				SystemContextViewModel.getInstance().getCurrentAccount().getAccountId());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);
	}

	/**
	 * Loads characters from a file.
	 * 
	 * @param account The account to load characters from.
	 *
	 * @param account the account to load its character(s)
	 * @return List of Character objects
	 */
	public static List<Character> loadCharacters(AccountInfo account) {
		if (account == null) {
			throw new IllegalArgumentException("Account cannot be null.");
		}

		List<Character> characters = new ArrayList<Character>();

		JsonObject requestObject = new JsonObject();
		requestObject.addProperty("type", "list characters");
		requestObject.addProperty("playerOwnerID", account.getAccountId());

		String request = GSON.toJson(requestObject);
		String response = Client.sendRequest(request);
		//System.out.println("Response from server: " + response);

		JsonObject jsonResponseObject = JsonParser.parseString(response).getAsJsonObject();
		String successCode = jsonResponseObject.get("success code").getAsString();
		//System.out.println(successCode);

		if ("success".equals(successCode)) {
			JsonArray charactersArray = jsonResponseObject.getAsJsonArray("createdCharacters");
			Type listType = new TypeToken<List<Character>>() {
			}.getType();
			List<Character> createdCharacters = GSON.fromJson(charactersArray, listType);
			return createdCharacters;
		}
		return characters;
	}

	/**
	 * Creates a new character in the system, saves it
	 * 
	 * @param character The character to create
	 */
	public static void createCharacter(Character character) {
		if (character == null) {
			throw new IllegalArgumentException("Character cannot be null");
		}

		saveCharacter(character, false);
	}

	/**
	 * Saves campaigns to a file.
	 *
	 * @param campaigns List of Campaign objects
	 */
	public static void saveCampaigns(List<Campaign> campaigns) {
		writeToFile(campaignFile, campaigns);
	}

	/**
	 * Loads campaigns from a file.
	 *
	 * @return List of Campaign objects
	 */
	public static List<Campaign> loadCampaigns() {
		return readNDJSONFromFile(campaignFile, Campaign.class);
	}

	/**
	 * Loads the races/species from a file.
	 * 
	 * @return List of race/species objects
	 */
	public static List<Race> loadRaces() {
		return readNDJSONFromFile(racesFile, Race.class);
	}

	/**
	 * Loads the classes/roles from a file.
	 * 
	 * @return List of classes/roles objects
	 */
	public static List<Role> loadClasses() {
		return readNDJSONFromFile(classFile, Role.class);
	}

	/**
	 * Saves friend requests to a file.
	 *
	 * @param friendRequests List of FriendRequest objects
	 */
	public static void saveFriendRequests(List<Request> friendRequests) {
		writeToFile(friendRequestFile, friendRequests);
	}

	/**
	 * Loads friend requests from a file.
	 *
	 * @return List of FriendRequest objects
	 */
	public static List<Request> loadFriendRequests() {
		return readNDJSONFromFile(friendRequestFile, Request.class);
	}

	/**
	 * Creates a new campaign in the system, saves it
	 *
	 * @param campaign The campaign to create
	 */
	public static void createCampaign(Campaign campaign) {
		if (campaign == null) {
			throw new IllegalArgumentException("Campaign cannot be null");
		}
		List<Campaign> campaigns = loadCampaigns();

		campaigns.add(campaign);
		saveCampaigns(campaigns);

	}

	/**
	 * Returns a list of open campaigns (not full) so players can see them.
	 * 
	 * @return campaigns that are open
	 */
	public static List<Campaign> getOpenCampaigns() {
		List<Campaign> all = loadCampaigns();
		List<Campaign> open = new ArrayList<>();
		for (Campaign camp : all) {
			if (camp.isOpen()) {
				open.add(camp);
			}
		}
		return open;
	}

	/**
	 * A general request handler to process campaign-related requests (join or
	 * invite acceptance). If the DM (or the receiving side) accepts, the player is
	 * added to the campaign, etc.
	 *
	 * @param request some identifier or the Request object itself
	 * @param accept  true if accepted, false if rejected
	 */
	public static void handleCampaignRequest(Request request, boolean accept) {
		if (request == null) {
			return;
		}

		List<AccountInfo> allAccounts = loadAccounts();
		AccountInfo from = null;
		AccountInfo to = null;
		for (AccountInfo acc : allAccounts) {
			if (acc.getAccountId().equals(request.getFromAccountId())) {
				from = acc;
			} else if (acc.getAccountId().equals(request.getToAccountId())) {
				to = acc;
			}
		}
		if (from == null || to == null) {
			return;
		}

		Campaign targetCampaign = findCampaignByDungeonMaster(to, from);
		if (request.getRequestType() == RequestType.CAMPAIGN_INVITE) {
			targetCampaign = findCampaignByDungeonMaster(from, to);
		}

		if (targetCampaign == null) {
			return;
		}

		if (accept) {
			attemptJoiningCampaign(request, from, to, targetCampaign);
		} else {
			request.reject();
		}
		saveAccounts(allAccounts);
	}

	private static void attemptJoiningCampaign(Request request, AccountInfo from, AccountInfo to,
			Campaign targetCampaign) {
		request.accept();
		//System.out.print(targetCampaign.getName() + " " + targetCampaign.getDescription());
		Player player;
		if (request.getRequestType() == RequestType.CAMPAIGN_JOIN) {
			player = new Player(from);
		} else {
			player = new Player(to);
		}

		if (targetCampaign.isOpen()) {
			targetCampaign.getPlayers().add(player);
			List<Campaign> allCampaigns = loadCampaigns();
			saveCampaigns(allCampaigns);
		} else {
			request.reject();
		}
	}

	private static Campaign findCampaignByDungeonMaster(AccountInfo dmAccount, AccountInfo playerAccount) {
		List<Campaign> allCamps = loadCampaigns();
		for (Campaign camp : allCamps) {
			if (camp.getDungeonMaster().getAccountInfo().getAccountId().equals(dmAccount.getAccountId())) {
				return camp;
			}
		}
		return null;
	}

	/**
	 * Transfers campaign ownership to a new Dungeon Master.
	 *
	 * @param campaign         The campaign to transfer
	 * @param newDungeonMaster The new Dungeon Master
	 */
	public static void transferCampaignOwnership(Campaign campaign, DungeonMaster newDungeonMaster) {
		if (campaign == null || newDungeonMaster == null) {
			throw new IllegalArgumentException("Campaign and new Dungeon Master cannot be null");
		}
		campaign.setDungeonMaster(newDungeonMaster);
		saveCampaigns(loadCampaigns());
	}

	private static <T> void writeToFile(String filename, T data) {
		try {
			File file = new File(filename);
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				if (data instanceof List<?>) {
					for (Object obj : (List<?>) data) {
						writer.write(GSON.toJson(obj));
						writer.newLine();
					}
				} else {
					writer.write(GSON.toJson(data));
				}
			}
		} catch (IOException exception) {
			LOGGER.log(Level.SEVERE, "Error writing to " + filename);
		}
	}

	private static <T> List<T> readNDJSONFromFile(String filename, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		File file = new File(filename);
		if (!file.exists()) {
			LOGGER.log(Level.WARNING, "File not found: " + filename + "; returning empty list.");
			return list;
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.trim().isEmpty()) {
					T obj = GSON.fromJson(line, clazz);
					list.add(obj);
				}
			}
		} catch (IOException exception) {
			LOGGER.log(Level.WARNING, "Error reading from " + filename + " (returning empty list)");
		}
		return list;
	}

	/**
	 * Sets the path for the accounts JSON file. Useful for testing or changing the
	 * default location of stored account data.
	 *
	 * @param path the file path to use for saving/loading accounts; must be
	 *             non-null and non-empty
	 * @throws IllegalArgumentException if path is null or blank
	 */
	public static void setAccountFilePath(String path) {
		accountFile = path;
	}

	/**
	 * Sets the path for the characters JSON file. Useful for testing or changing
	 * the default location of stored character data.
	 *
	 * @param path the file path to use for saving/loading characters; must be
	 *             non-null and non-empty
	 * @throws IllegalArgumentException if path is null or blank
	 */
	public static void setCharacterFilePath(String path) {
	}

	/**
	 * Sets the path for the campaigns JSON file. Useful for testing or changing the
	 * default location of stored campaign data.
	 *
	 * @param path the file path to use for saving/loading campaigns; must be
	 *             non-null and non-empty
	 * @throws IllegalArgumentException if path is null or blank
	 */
	public static void setCampaignFilePath(String path) {
		campaignFile = path;
	}

	/**
	 * Sets the path for the friend requests JSON file. Useful for testing or
	 * changing the default location of stored request data.
	 *
	 * @param path the file path to use for saving/loading friend requests; must be
	 *             non-null and non-empty
	 * @throws IllegalArgumentException if path is null or blank
	 */
	public static void setFriendRequestFilePath(String path) {
		friendRequestFile = path;
	}

	/**
	 * Finds a player by their username.
	 *
	 * @param username the username to search for
	 * @return the Player if found, null otherwise
	 */
	public static Player findPlayerByUsername(String username) {
		List<AccountInfo> accounts = loadAccounts();

		for (AccountInfo account : accounts) {
			if (account.getUsername().equalsIgnoreCase(username)) {
				return new Player(account);
			}
		}
		return null;
	}

	/**
	 * Uploads a new resource image to the server.
	 * 
	 * @param campaign  campaign name or ID
	 * @param name      user‑provided resource name
	 * @param imagePath local Path to a .png/.jpg file
	 * @throws IOException on file read or communication errors
	 */
	public static void uploadResource(String campaign, String name, Path imagePath) throws IOException {
		byte[] bytes = Files.readAllBytes(imagePath);
		String b64 = Base64.getEncoder().encodeToString(bytes);
		JsonObject req = new JsonObject();
		req.addProperty("type", UPLOAD_RESOURCE);
		req.addProperty("campaign", campaign);
		JsonObject data = new JsonObject();
		data.addProperty("name", name);
		data.addProperty("content", b64);
		req.add("data", data);
		Client.sendRequest(req.toString());
	}

	/**
	 * Lists all resources for a campaign.
	 * 
	 * @param campaign campaign ID
	 * @return list of Resource metadata
	 */
	public static List<Resource> listResources(String campaign) {
		JsonObject req = new JsonObject();
		req.addProperty("type", LIST_RESOURCES);
		req.addProperty("campaign", campaign);
		String resp = Client.sendRequest(req.toString());
		JsonObject obj = JsonParser.parseString(resp).getAsJsonObject();
		Type listType = new TypeToken<List<Resource>>() {
		}.getType();
		return GSON.fromJson(obj.get("resources"), listType);
	}

	/**
	 * Downloads an existing resource’s content.
	 * 
	 * @param campaign campaign ID
	 * @param name     resource name
	 * @return raw bytes of the image
	 */
	public static byte[] downloadResource(String campaign, String name) {
		JsonObject req = new JsonObject();
		req.addProperty("type", DOWNLOAD_RESOURCE);
		req.addProperty("campaign", campaign);
		req.addProperty("name", name);
		String resp = Client.sendRequest(req.toString());
		JsonObject obj = JsonParser.parseString(resp).getAsJsonObject();
		String b64 = obj.get("content").getAsString();
		return Base64.getDecoder().decode(b64);
	}

	/**
	 * Deletes a resource on the server (DM only).
	 * 
	 * @param campaign the campaign in question
	 * @param filename name of the file to delete
	 */
	public static void deleteResource(String campaign, String filename) {
		JsonObject req = new JsonObject();
		req.addProperty("type", DELETE_RESOURCE);
		req.addProperty("campaign", campaign);
		req.addProperty("name", filename);
		Client.sendRequest(req.toString());
	}

	/**
	 * Toggles a resource’s visibility on the server.
	 *
	 * @param campaign the campaign ID
	 * @param filename the resource’s filename
	 * @return the new visibility state, or null on error
	 */
	public static Boolean toggleVisibility(String campaign, String filename) {
	    JsonObject req = new JsonObject();
	    req.addProperty("type", TOGGLE_VISIBILITY);
	    req.addProperty("campaign", campaign);
	    req.addProperty("name", filename);
	    String resp = Client.sendRequest(req.toString());
	    JsonObject obj = JsonParser.parseString(resp).getAsJsonObject();
	    if (!obj.has("visible")) {
	        return null;
	    }
	    return obj.get("visible").getAsBoolean();
	}

}