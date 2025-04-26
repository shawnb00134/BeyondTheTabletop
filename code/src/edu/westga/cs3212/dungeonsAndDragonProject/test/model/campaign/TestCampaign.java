package edu.westga.cs3212.dungeonsAndDragonProject.test.model.campaign;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Weapon;

class TestCampaign {

	@Test
	void testSetDungeonMaster() {
		DungeonMaster dm = new DungeonMaster(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Campaign result = new Campaign("war", "stuff", 1);
		result.setDungeonMaster(dm);
		
		
		assertEquals("John", result.getDungeonMaster().getAccountInfo().getFirstName(), "checking dungeon master");
	}

	@Test
	void testChangeCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Player player2 = new Player(
				new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword"));
		result.setDungeonMaster(player2);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player2);
		players.add(player1);
		result.setCampaignLimit(3);
		result.setPlayers(players);
		result.setDescription("a");
		result.setName("b");
		result.setSharedNotesText("c");

		assertEquals("b", result.getName(), "checking name");
		assertEquals("a", result.getDescription(), "checking name");
		assertEquals("Sallie", result.getDungeonMaster().getAccountInfo().getFirstName(), "checking dungeon master");
		assertEquals(2, result.getPlayers().size(), "checking how many players.");
		assertEquals("c", result.getSharedNotesText(), "checking notes");
		assertEquals(true, result.isOpen(), "checking campaign is open");
		assertEquals(3, result.getCampaignLimit(),"checking campaing limit");

	}

	@Test
	void testCloseCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		result.closeCampaign();

		assertEquals(false, result.isOpen(), "checking if campaign is open");
	}
	
	

	@Test
	void testAddPlayerToEmptyCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));

		result.addPlayer(player1);

		assertEquals(1, result.getPlayers().size(), "checking how many players.");
	}

	@Test
	void testAddPlayerToExistingMembersInCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Player player2 = new Player(
				new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword"));
		result.addPlayer(player1);
		result.addPlayer(player2);

		assertEquals(2, result.getPlayers().size(), "checking how many players.");
	}
	
	@Test
	void testRemovePlayerInEmptyCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		result.removePlayer(player1);
		assertEquals(0, result.getPlayers().size(), "checking how many players.");
	}

	@Test
	void testRemovePlayerFirstInCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Player player2 = new Player(
				new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword"));
		result.addPlayer(player1);
		result.addPlayer(player2);
		result.removePlayer(player1);
		assertEquals(1, result.getPlayers().size(), "checking how many players.");
		assertEquals("Sallie", result.getPlayers().get(0).getAccountInfo().getFirstName(),
				"checking player who is left name.");
	}

	@Test
	void testRemovePlayerLastInCampaign() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		Player player2 = new Player(
				new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword"));
		result.addPlayer(player1);
		result.addPlayer(player2);

		result.removePlayer(player2);
		assertEquals(1, result.getPlayers().size(), "checking how many players.");
		assertEquals("Frank", result.getPlayers().get(0).getAccountInfo().getFirstName(),
				"checking player who is left name.");
	}

	@Test
	void testRemoveAllPlayers() {
		Campaign result = new Campaign("war", "stuff", 1);

		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		Player player2 = new Player(
				new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword"));
		result.addPlayer(player1);
		result.addPlayer(player2);

		result.removeAllPlayers();
		assertEquals(0, result.getPlayers().size(), "checking how many players.");
	}
	
	@Test
	void checkIfCampaignClosedConditionMet() {
		Campaign result = new Campaign("war", "stuff", 1);
		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		result.addPlayer(player1);
		result.checkCampaignLimit();
		assertEquals(false, result.isOpen());
	}
	
	@Test
	void checkIfCampaignClosedConditionNotMet() {
		Campaign result = new Campaign("war", "stuff", 2);
		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		result.addPlayer(player1);
		result.checkCampaignLimit();
		assertEquals(true, result.isOpen());
	}
	
	@Test
	void checkIfPlayerInCampaignAndPlayerIs() {
		Campaign result = new Campaign("war", "stuff", 2);
		AccountInfo account = new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword");
		Player player1 = new Player(account);
		result.addPlayer(player1);
		boolean verdict = result.checkPlayerAlreadyInCampaign(account);
		assertEquals(true, verdict);
	}
	
	@Test
	void checkIfPlayerInCampaignAndPlayerIsNot() {
		Campaign result = new Campaign("war", "stuff", 2);
		AccountInfo account1 = new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword");
		AccountInfo account = new AccountInfo("12445", "Sallie", "Mae", "john.do@example.com", "salliemae", "securepassword");
		Player player1 = new Player(account);
		result.addPlayer(player1);
		boolean verdict = result.checkPlayerAlreadyInCampaign(account1);
		assertEquals(false, verdict);
	}
	
	@Test
	void checkIfPlayerInCampaignAndPlayerIsNotAndNull() {
		Campaign result = new Campaign("war", "stuff", 2);
		AccountInfo account1 = new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword");
		result.addPlayer(null);
		boolean verdict = result.checkPlayerAlreadyInCampaign(account1);
		assertEquals(false, verdict);
	}
	
	
	@Test
	void addCharacterToPlayer() {
		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory();
		
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		Campaign result = new Campaign("war", "stuff", 2);
		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		result.addPlayer(player1);
		result.addCharacterToPlayer(character.getCharacterName(),player1.getAccountInfo().getAccountId());
		
		assertEquals("John", result.getCampaignPlayers().get("12344"), "checking charcter name associated with player");
		
	}
	
	@Test
	void checkIfCharacterIsAddedAndIsAdded() {
		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory();
		
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		Campaign campaign = new Campaign("war", "stuff", 2);
		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		campaign.addPlayer(player1);
		campaign.addCharacterToPlayer(character.getCharacterName(),player1.getAccountInfo().getAccountId());
		
		boolean result = campaign.checkIfCharacterIsAdded(player1);
		
		assertTrue(result);
		
		
	}
	
	@Test
	void checkIfCharacterIsAddedAndIsNotAdded() {
		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");

		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		Inventory charInventory = new Inventory();
		
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		charInventory.addItemToInventory(weapon);
		
		Character character = new Character("John", 1, 1, 1, 1, charAttributes, charClass, weaponMastery, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes","Height", "Weight", "Age", "Gender", false, "", "playerID");
		
		Campaign campaign = new Campaign("war", "stuff", 2);
		Player player1 = new Player(
				new AccountInfo("12344", "Frank", "Mac", "john.doe@example.com", "johndoe", "securepassword"));
		campaign.addPlayer(player1);
		campaign.addCharacterToPlayer(character.getCharacterName(),player1.getAccountInfo().getAccountId());
		Player player2 = new Player(
				new AccountInfo("12345", "abc", "Mac", "john.doe@example.com", "abc123", "securepassword"));
		boolean result = campaign.checkIfCharacterIsAdded(player2);
		
		assertFalse(result);
	}

}
