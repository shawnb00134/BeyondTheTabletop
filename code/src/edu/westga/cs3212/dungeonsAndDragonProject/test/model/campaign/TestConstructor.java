package edu.westga.cs3212.dungeonsAndDragonProject.test.model.campaign;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;

public class TestConstructor {

	@Test
	void testEmptyName() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Campaign("", "a", 1);
		});
	}
	
	@Test
	void testSetNullDungeonMaster() {
		Campaign result = new Campaign("war", "stuff", 1);
		assertThrows(IllegalArgumentException.class, () -> {
			result.setDungeonMaster(null);
		});
	}

	@Test
	void testEmptyDescription() {
		assertThrows(IllegalArgumentException.class, () -> {
			new Campaign("a", "", 1);
		});
	}

	@Test
	void testValidCampaign() {
		Player player = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Campaign result = new Campaign("war", "stuff", 1);
		result.setDungeonMaster(player);
		assertEquals("war", result.getName(), "checking name");
		assertEquals("stuff", result.getDescription(), "checking name");
		assertEquals("John", result.getDungeonMaster().getAccountInfo().getFirstName(), "checking dungeon master");
		assertEquals(0, result.getPlayers().size(), "checking how many players.");
		assertEquals("", result.getSharedNotesText(), "checking notes");
		assertEquals(true, result.isOpen(), "checking campaign is open");
		assertEquals(1, result.getCampaignLimit(), "checking campaing limit");
		assertTrue(result.getSpecialPlayers().isEmpty());
	}
}
