package edu.westga.cs3212.dungeonsAndDragonProject.test.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestStatus;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Resource;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;

public class TestServerCommunicationHandler {

	private File testAccountsFile;
	private File testCharactersFile;
	private File testCampaignsFile;
	private File testRequestsFile;
	private static final Gson GSON = new Gson();

	@BeforeEach
	void setUp() throws Exception {
		testAccountsFile = new File("temp-test-accounts.json");
		testCharactersFile = new File("temp-test-characters.json");
		testCampaignsFile = new File("temp-test-campaigns.json");
		testRequestsFile = new File("temp-test-requests.json");
		ServerCommunicationHandler.setAccountFilePath(testAccountsFile.getPath());
		ServerCommunicationHandler.setCharacterFilePath(testCharactersFile.getPath());
		ServerCommunicationHandler.setCampaignFilePath(testCampaignsFile.getPath());
		ServerCommunicationHandler.setFriendRequestFilePath(testRequestsFile.getPath());
		if (testAccountsFile.exists()) {
			testAccountsFile.delete();
		}
		if (testCharactersFile.exists()) {
			testCharactersFile.delete();
		}
		if (testCampaignsFile.exists()) {
			testCampaignsFile.delete();
		}
		if (testRequestsFile.exists()) {
			testRequestsFile.delete();
		}
		overrideClientSendRequest();
	}

	@AfterEach
	void tearDown() {
		if (testAccountsFile.exists()) {
			testAccountsFile.delete();
		}
		if (testCharactersFile.exists()) {
			testCharactersFile.delete();
		}
		if (testCampaignsFile.exists()) {
			testCampaignsFile.delete();
		}
		if (testRequestsFile.exists()) {
			testRequestsFile.delete();
		}
	}

	private void overrideClientSendRequest() throws Exception {
		Field field = Client.class.getDeclaredField("sendRequestDelegate");
		field.setAccessible(true);
		field.set(null, (Client.SendRequestDelegate) (req) -> TestResponseUtil.getFakeResponse(req));
	}

	@Test
	void testAuthenticateUserSuccessFake() {
		JsonObject accountObj = new JsonObject();
		accountObj.addProperty("accountId", "abc123");
		accountObj.addProperty("username", "testuser");
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "success");
		responseObj.add("accountInfo", accountObj);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo result = ServerCommunicationHandler.authenticateUser("testuser", "password123");
		assertNotNull(result);
		assertEquals("abc123", result.getAccountId());
	}

	@Test
	void testAuthenticateUserFailureFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "failure");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo result = ServerCommunicationHandler.authenticateUser("testuser", "wrongpass");
		assertNull(result);
	}

	@Test
	void testLoadAccountCampaignsSuccessFake() {
		JsonArray campaignsArray = new JsonArray();
		Campaign camp = new Campaign("Camp1", "desc", 5);
		campaignsArray.add(GSON.toJsonTree(camp));
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "success");
		responseObj.add("joinedCampaigns", campaignsArray);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("1", "A", "B", "a@b.com", "user1", "pass");
		List<Campaign> result = ServerCommunicationHandler.loadAccountCampaigns(acc);
		assertEquals(1, result.size());
		assertEquals("Camp1", result.get(0).getName());
	}

	@Test
	void testLoadAccountCampaignsFailureFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "failure");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("1", "A", "B", "a@b.com", "user1", "pass");
		List<Campaign> result = ServerCommunicationHandler.loadAccountCampaigns(acc);
		assertTrue(result.isEmpty());
	}

	@Test
	void testAddCampaignFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign camp = new Campaign("NewCamp", "desc", 3);
		ServerCommunicationHandler.addCampaign(camp);
	}

	@Test
	void testRemoveCampaignFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign camp = new Campaign("RemoveCamp", "desc", 3);
		ServerCommunicationHandler.removeCampaign(camp);
	}

	@Test
	void testGetCampaignFromServerSuccessFake() {
		Campaign camp = new Campaign("CampX", "desc", 5);
		String campJson = GSON.toJson(camp);
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "success");
		JsonObject inner = JsonParser.parseString(campJson).getAsJsonObject();
		responseObj.add("campaign result", inner);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign result = ServerCommunicationHandler.getCampaignFromServer(new Campaign("CampX", "desc", 5));
		assertNotNull(result);
		assertEquals("CampX", result.getName());
	}

	@Test
	void testGetCampaignFromServerFailureFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "failure");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign result = ServerCommunicationHandler.getCampaignFromServer(new Campaign("NonExist", "desc", 5));
		assertNull(result);
	}

	@Test
    void testSendOutInviteFake() {
		List<AccountInfo> accounts = new ArrayList<>();
		AccountInfo from = new AccountInfo("fromID", "A", "B", "f@a.com", "fromUser", "pass");
		AccountInfo to = new AccountInfo("toID", "C", "D", "t@a.com", "toUser", "pass");
		accounts.add(from);
		ServerCommunicationHandler.saveAccounts(accounts);
		
    	JsonObject inviteObj = new JsonObject();
    	inviteObj.addProperty("dummy", "ok");
    	
    	ServerCommunicationHandler.sendOutInvite(to, from);
    }

	@Test
	void testAcceptInviteFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo recipient = new AccountInfo("r1", "R", "One", "r1@a.com", "recip", "pass");
		AccountInfo sender = new AccountInfo("s1", "S", "One", "s1@a.com", "sender", "pass");
		Campaign camp = new Campaign("InviteCamp", "desc", 3);
		ServerCommunicationHandler.acceptInvite(recipient, sender, camp);
	}

	@Test
	void testDenyInviteFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo recipient = new AccountInfo("r2", "R", "Two", "r2@a.com", "recip2", "pass");
		AccountInfo sender = new AccountInfo("s2", "S", "Two", "s2@a.com", "sender2", "pass");
		ServerCommunicationHandler.denyInvite(recipient, sender);
	}

	@Test
	void testEditCampaignFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign oldCamp = new Campaign("OldCamp", "old", 4);
		Campaign newCamp = new Campaign("NewCamp", "new", 4);
		ServerCommunicationHandler.editCampaign(oldCamp, newCamp);
	}

	@Test
	void testOverwriteCampaignFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		Campaign camp = new Campaign("OverwriteCamp", "desc", 3);
		ServerCommunicationHandler.overwriteCampaign(camp);
	}

	@Test
	void testGetOutgoingRequestFake() {
		Request req = new Request("fromOut", "toOut", RequestType.CAMPAIGN_JOIN, "CampY");
		JsonArray reqArray = new JsonArray();
		reqArray.add(GSON.toJsonTree(req));
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "success");
		responseObj.add("outgoingRequests", reqArray);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("acc1", "A", "B", "a@b.com", "user", "pass");
		List<Request> outgoing = ServerCommunicationHandler.getOutgoingRequest(acc);
		assertEquals(1, outgoing.size());
		assertEquals("fromOut", outgoing.get(0).getFromAccountId());
	}

	@Test
	void testGetOutgoingRequestFakeFailure() {
		Request req = new Request("fromOut", "toOut", RequestType.CAMPAIGN_JOIN, "CampY");
		JsonArray reqArray = new JsonArray();
		reqArray.add(GSON.toJsonTree(req));
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "failure");
		responseObj.add("outgoingRequests", reqArray);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("acc1", "A", "B", "a@b.com", "user", "pass");
		List<Request> outgoing = ServerCommunicationHandler.getOutgoingRequest(acc);
		assertEquals(0, outgoing.size());
	}

	@Test
	void testLoadCharactersFake() {
		Attributes sampleAttributes = new Attributes(10, 12, 14, 8, 11, 13);
		Race sampleRace = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "Versatile race",
				new String[] { "Resourceful", "Skillful" });
		Role sampleRole = new Role("Warrior", "Martial class", Set.of("Second Wind"),
				Set.of("Light Armor", "Simple Weapon"));
		Inventory sampleInventory = new Inventory();
		Character c1 = new Character("Hero", 2, 2, 10, 10, sampleAttributes, sampleRole, new ArrayList<>(),
				sampleInventory, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), sampleRace, "Neutral", "None",
				"Black", "Tan", "Brown", "6ft", "180lbs", "25", "Male", false, "", "1");
		JsonArray charArray = new JsonArray();
		charArray.add(GSON.toJsonTree(c1));
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "success");
		responseObj.add("createdCharacters", charArray);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("1", "A", "B", "a@b.com", "user1", "pass");
		List<Character> loaded = ServerCommunicationHandler.loadCharacters(acc);
		assertEquals(1, loaded.size());
		Character loadedChar = loaded.get(0);
		assertEquals("Hero", loadedChar.getCharacterName());
		assertEquals(2, loadedChar.getCharacterLevel());
	}

	@Test
	void testLoadCharactersFailure() {
		Attributes sampleAttributes = new Attributes(10, 12, 14, 8, 11, 13);
		Race sampleRace = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "Versatile race",
				new String[] { "Resourceful", "Skillful" });
		Role sampleRole = new Role("Warrior", "Martial class", Set.of("Second Wind"),
				Set.of("Light Armor", "Simple Weapon"));
		Inventory sampleInventory = new Inventory();
		Character c1 = new Character("Hero", 2, 2, 10, 10, sampleAttributes, sampleRole, new ArrayList<>(),
				sampleInventory, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), sampleRace, "Neutral", "None",
				"Black", "Tan", "Brown", "6ft", "180lbs", "25", "Male", false, "", "1");
		JsonArray charArray = new JsonArray();
		charArray.add(GSON.toJsonTree(c1));
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("success code", "failure");
		responseObj.add("createdCharacters", charArray);
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo acc = new AccountInfo("1", "A", "B", "a@b.com", "user1", "pass");
		List<Character> loaded = ServerCommunicationHandler.loadCharacters(acc);
		assertEquals(0, loaded.size());
	}

	@Test
	void testCreateCharacterFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo current = new AccountInfo("current", "First", "Last", "c@a.com", "currUser", "pass");
		edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel.getInstance()
				.setCurrentAccount(current);
		Character c = new Character("Mage", 3, 10, 20, 20, new Attributes(8, 14, 10, 10, 12, 12),
				new Role("Wizard", "Spellcaster", Set.of("Arcane Recovery"), Set.of("Daggers")), new ArrayList<>(),
				new Inventory(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new Race("Elf", Creature.HUMANOID, Size.MEDIUM, 35, "Graceful", new String[] { "Keen Senses" }),
				"Chaotic Good", "None", "Blonde", "Fair", "Blue", "5ft 8in", "130lbs", "120", "Female", false, "", "x");
		ServerCommunicationHandler.createCharacter(c);
	}

	@Test
	void testRemoveCharacterFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		AccountInfo current = new AccountInfo("current1", "First", "Last", "c@a.com", "currUser", "pass");
		edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel.getInstance()
				.setCurrentAccount(current);
		Character c = new Character("Rogue", 2, 8, 15, 15, new Attributes(10, 10, 10, 10, 10, 10),
				new Role("Rogue", "Stealthy", Set.of("Sneak Attack"), Set.of("Light Armor")), new ArrayList<>(),
				new Inventory(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new Race("Halfling", Creature.HUMANOID, Size.SMALL, 25, "Lucky", new String[] { "Brave" }), "Neutral",
				"None", "Brown", "Tan", "Green", "3ft", "40lbs", "30", "Male", false, "", "current1");
		ServerCommunicationHandler.removeCharacter(c);
	}

	@Test
	void testHandleCampaignRequestJoinFake() {
		List<AccountInfo> accounts = new ArrayList<>();
		AccountInfo from = new AccountInfo("fromID", "A", "B", "f@a.com", "fromUser", "pass");
		AccountInfo to = new AccountInfo("toID", "C", "D", "t@a.com", "toUser", "pass");
		accounts.add(from);
		accounts.add(to);
		ServerCommunicationHandler.saveAccounts(accounts);
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		Player dm = new Player(to);
		camp.setDungeonMaster(dm);
		List<Campaign> camps = new ArrayList<>();
		camps.add(camp);
		ServerCommunicationHandler.saveCampaigns(camps);
		Request req = new Request("fromID", "toID", RequestType.CAMPAIGN_JOIN, "JoinCamp");
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");
		ServerCommunicationHandler.handleCampaignRequest(req, true);
		assertEquals(RequestStatus.ACCEPTED, req.getStatus());
	}

	@Test
	void testHandleCampaignRequestJoinFakeDeny() {
		List<AccountInfo> accounts = new ArrayList<>();
		AccountInfo from = new AccountInfo("fromID", "A", "B", "f@a.com", "fromUser", "pass");
		AccountInfo to = new AccountInfo("toID", "C", "D", "t@a.com", "toUser", "pass");
		accounts.add(from);
		accounts.add(to);
		ServerCommunicationHandler.saveAccounts(accounts);
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		Player dm = new Player(to);
		camp.setDungeonMaster(dm);
		List<Campaign> camps = new ArrayList<>();
		camps.add(camp);
		ServerCommunicationHandler.saveCampaigns(camps);
		Request req = new Request("fromID", "toID", RequestType.CAMPAIGN_JOIN, "JoinCamp");
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");
		ServerCommunicationHandler.handleCampaignRequest(req, false);
		assertEquals(RequestStatus.REJECTED, req.getStatus());
	}

	@Test
	void testFindPlayerByUsernameReturnsNullIfNotFound() {
		List<AccountInfo> accounts = new ArrayList<>();
		accounts.add(new AccountInfo("p1", "A", "B", "a@b.com", "player1", "pass"));
		ServerCommunicationHandler.saveAccounts(accounts);
		Player found = ServerCommunicationHandler.findPlayerByUsername("nonexistent");
		assertNull(found);
	}

	@Test
	void testFindPlayerByUsernameReturnsPlayer() {
		List<AccountInfo> accounts = new ArrayList<>();
		accounts.add(new AccountInfo("p1", "A", "B", "a@b.com", "player1", "pass"));
		accounts.add(new AccountInfo("p2", "C", "D", "c@d.com", "player2", "pass"));
		ServerCommunicationHandler.saveAccounts(accounts);
		Player found = ServerCommunicationHandler.findPlayerByUsername("player2");
		assertNotNull(found);
		assertEquals("p2", found.getAccountInfo().getAccountId());
	}

	@Test
	void testTransferCampaignOwnershipFake() {
		List<Campaign> camps = new ArrayList<>();
		Campaign camp = new Campaign("OwnerCamp", "desc", 5);
		Player oldDM = new Player(new AccountInfo("oldDM", "F", "L", "old@d.com", "olduser", "pass"));
		camp.setDungeonMaster(oldDM);
		camps.add(camp);
		ServerCommunicationHandler.saveCampaigns(camps);
		DungeonMaster newDM = new DungeonMaster(new AccountInfo("newDM", "N", "M", "new@d.com", "newuser", "pass"));
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");
		ServerCommunicationHandler.transferCampaignOwnership(camp, newDM);
		assertEquals("newDM", camp.getDungeonMaster().getAccountInfo().getAccountId());
	}

	@Test
	void testSaveCharactersFake() {
		JsonObject responseObj = new JsonObject();
		responseObj.addProperty("dummy", "ok");
		TestResponseUtil.setFakeResponse(responseObj.toString());
		List<Character> characters = new ArrayList<>();
		Attributes sampleAttributes = new Attributes(10, 10, 10, 10, 10, 10);
		Race sampleRace = new Race("SampleRace", Creature.HUMANOID, Size.MEDIUM, 30, "sample",
				new String[] { "Trait" });
		Role sampleRole = new Role("SampleRole", "sample role", Set.of("Feat"), Set.of("Armor"));
		Inventory sampleInventory = new Inventory();
		Character sampleChar = new Character("TestChar", 1, 10, 10, 10, sampleAttributes, sampleRole, new ArrayList<>(),
				sampleInventory, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), sampleRace, "Good", "None",
				"Black", "Brown", "Blue", "5ft", "150lbs", "20", "Male", false, "", "acc1");
		characters.add(sampleChar);
		ServerCommunicationHandler.saveCharacters(characters);
	}

	@Test
	void testLoadRaces() throws Exception {
		setStaticFinalField("racesFile", "temp-test-races.json");
		Race race = new Race("TestRace", Creature.HUMANOID, Size.MEDIUM, 30, "desc", new String[] { "Trait1" });
		java.nio.file.Files.write(java.nio.file.Paths.get("temp-test-races.json"),
				(GSON.toJson(race) + "\n").getBytes());
		List<Race> races = ServerCommunicationHandler.loadRaces();
		assertEquals(1, races.size());
		assertEquals("TestRace", races.get(0).getName());
		new File("temp-test-races.json").delete();
	}

	@Test
	void testLoadClasses() throws Exception {
		setStaticFinalField("classFile", "temp-test-roles.json");
		Role role = new Role("TestRole", "desc", Set.of("Feat1"), Set.of("Prof1"));
		java.nio.file.Files.write(java.nio.file.Paths.get("temp-test-roles.json"),
				(GSON.toJson(role) + "\n").getBytes());
		List<Role> roles = ServerCommunicationHandler.loadClasses();
		assertEquals(1, roles.size());
		assertEquals("TestRole", roles.get(0).getName());
		new File("temp-test-roles.json").delete();
	}

	@Test
	void testSaveAndLoadFriendRequests() {
		List<Request> requests = new ArrayList<>();
		Request req = new Request("id1", "id2", RequestType.FRIEND, "CampaignX");
		requests.add(req);
		ServerCommunicationHandler.saveFriendRequests(requests);
		List<Request> loaded = ServerCommunicationHandler.loadFriendRequests();
		assertEquals(1, loaded.size());
		assertEquals(RequestType.FRIEND, loaded.get(0).getRequestType());
		assertEquals("id1", loaded.get(0).getFromAccountId());
	}

	@Test
	void testCreateCampaignAndGetOpenCampaigns() {
		Campaign camp = new Campaign("OpenCamp", "desc", 5);
		ServerCommunicationHandler.createCampaign(camp);
		List<Campaign> all = ServerCommunicationHandler.loadCampaigns();
		assertEquals(1, all.size());
		List<Campaign> open = ServerCommunicationHandler.getOpenCampaigns();
		assertEquals(1, open.size());
	}

	@Test
	void testAddCampaignWhenPassingInNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.addCampaign(null));
	}

	@Test
	void testCreateCampaignWhenPassingInNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.createCampaign(null));
	}

	@Test
	void testRemoveCampaignWhenPassingInNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.removeCampaign(null));
	}

	@Test
	void testLoadAccountCampaignsWhenPassingNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.loadAccountCampaigns(null));
	}

	@Test
	void testLoadCharactersWhenPassingNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.loadCharacters(null));
	}

	@Test
	void testCreateCharactersWhenPassingNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.createCharacter(null));
	}

	@Test
	void testRemoveCharactersWhenPassingNull() {
		assertThrows(IllegalArgumentException.class, () -> ServerCommunicationHandler.removeCharacter(null));
	}

	@Test
	void testTransferCampaignOwnershipWhenPassingNullCampaign() {
		AccountInfo acc = new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret");
		DungeonMaster dm = new DungeonMaster(acc);
		assertThrows(IllegalArgumentException.class,
				() -> ServerCommunicationHandler.transferCampaignOwnership(null, dm));
	}

	@Test
	void testTransferCampaignOwnershipWhenPassingNullDungeonMaster() {
		Campaign result = new Campaign("war", "stuff", 1);
		assertThrows(IllegalArgumentException.class,
				() -> ServerCommunicationHandler.transferCampaignOwnership(result, null));
	}

	@Test
	void testHandleNullCampaignRequest() {
		ServerCommunicationHandler.handleCampaignRequest(null, true);
	}

	@Test
	void testHandleNullCampaignRequestNullRecipientNullSender() {
		Request request = new Request(null, null, null, null);
		ServerCommunicationHandler.handleCampaignRequest(request, true);
	}

	@Test
	void testHandleNullCampaignRequestNullRecipient() {
		Request request = new Request("qqwq", null, null, null);
		ServerCommunicationHandler.handleCampaignRequest(request, true);
	}

	@Test
	void testHandleNullCampaignRequestNullSender() {
		Request request = new Request(null, "qweqw", null, null);
		ServerCommunicationHandler.handleCampaignRequest(request, true);
	}

	@Test
	void testHandleFakeCampaignInviteRequest() {
		List<AccountInfo> accounts = new ArrayList<>();
		AccountInfo from = new AccountInfo("fromID", "A", "B", "f@a.com", "fromUser", "pass");
		AccountInfo to = new AccountInfo("toID", "C", "D", "t@a.com", "toUser", "pass");
		accounts.add(from);
		accounts.add(to);
		ServerCommunicationHandler.saveAccounts(accounts);
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		Player dm = new Player(to);
		camp.setDungeonMaster(dm);
		List<Campaign> camps = new ArrayList<>();
		camps.add(camp);
		ServerCommunicationHandler.saveCampaigns(camps);
		Request request = new Request("fromID", "toID", RequestType.CAMPAIGN_INVITE, "JoinCamp");
		ServerCommunicationHandler.handleCampaignRequest(request, true);
	}
	
	@Test
	void testHandleFakeClosedCampaignInviteRequest() {
		List<AccountInfo> accounts = new ArrayList<>();
		AccountInfo from = new AccountInfo("fromID", "A", "B", "f@a.com", "fromUser", "pass");
		AccountInfo to = new AccountInfo("toID", "C", "D", "t@a.com", "toUser", "pass");
		accounts.add(from);
		accounts.add(to);
		ServerCommunicationHandler.saveAccounts(accounts);
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		Player dm = new Player(to);
		camp.setDungeonMaster(dm);
		List<Campaign> camps = new ArrayList<>();
		camp.closeCampaign();
		camps.add(camp);
		ServerCommunicationHandler.saveCampaigns(camps);
		Request req = new Request("fromID", "toID", RequestType.CAMPAIGN_JOIN, "JoinCamp");
		TestResponseUtil.setFakeResponse("{\"dummy\":\"ok\"}");
		ServerCommunicationHandler.handleCampaignRequest(req, true);
		assertEquals(RequestStatus.REJECTED, req.getStatus());
	}
	
	@Test
	void testUploadResource() {
		Campaign camp = new Campaign("JoinCamp", "desc", 5);		

		try {
			File tempFile = File.createTempFile("temp-", ".txt");			
			ServerCommunicationHandler.uploadResource(camp.getName(), "fakeresource", tempFile.toPath());
			List<Resource> resources = ServerCommunicationHandler.listResources(camp.getName());
			assertNull(resources);
			tempFile.deleteOnExit();
			ServerCommunicationHandler.deleteResource(camp.getName(), tempFile.getName());
		} catch (IOException e) {
			System.err.println("IOException has occured in test TestServerCommunicationHandler.testUploadResource");
		}
	}
	
	@Test
	void testDownloadResource() {
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		
		try {
			File tempFile = File.createTempFile("temp-", ".txt");			
			ServerCommunicationHandler.uploadResource(camp.getName(), "fakeresource", tempFile.toPath());
			assertTrue(ServerCommunicationHandler.downloadResource(camp.getName(), "temp-.txt").length == 0);
			tempFile.deleteOnExit();
			ServerCommunicationHandler.deleteResource(camp.getName(), tempFile.getName());
		} catch (Exception e) {
			System.err.println("Exception has occured in test TestServerCommunicationHandler.testDownloadResource");
		}
	}
	
	@Test
	void testToggleVisibility() {
		Campaign camp = new Campaign("JoinCamp", "desc", 5);
		
		try {
			File tempFile = File.createTempFile("temp-", ".txt");			
			ServerCommunicationHandler.uploadResource(camp.getName(), "fakeresource", tempFile.toPath());
			assertNull(ServerCommunicationHandler.toggleVisibility(camp.getName(), tempFile.getName()));
			tempFile.deleteOnExit();
			ServerCommunicationHandler.deleteResource(camp.getName(), tempFile.getName());
		} catch (Exception e) {
			System.err.println("Exception has occured in test TestServerCommunicationHandler.testToggleVisibility");
		}
	}

	private static void setStaticFinalField(String fieldName, String newValue) throws Exception {
		Field field = ServerCommunicationHandler.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		} catch (NoSuchFieldException e) {
		}
		field.set(null, newValue);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Client.sendRequest("exit");
	}

	public static class TestResponseUtil {
		private static String fakeResponse = "";

		public static void setFakeResponse(String response) {
			fakeResponse = response;
		}

		public static String getFakeResponse(String request) {
			return fakeResponse;
		}
	}
}
