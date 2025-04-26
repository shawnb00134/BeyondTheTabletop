package edu.westga.cs3212.dungeonsAndDragonProject.test.model.campaign;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;

public class TestSpecialPermissions {
	
	@Test
	void testAddAndRemovePlayerToSpecialPermissions() {
		Player player = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Campaign result = new Campaign("war", "stuff", 1);
		result.setDungeonMaster(player);
		
		result.addPlayerToSpecialPermissions("12346");
		assertTrue(result.checkIfPlayerHasSpecialPermissions("12346"));
		
		result.removeSpecialPermission("12346");
		assertFalse(result.checkIfPlayerHasSpecialPermissions("12346"));		
	}
}
