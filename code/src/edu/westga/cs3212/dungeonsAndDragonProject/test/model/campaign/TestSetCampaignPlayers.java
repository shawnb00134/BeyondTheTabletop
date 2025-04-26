package edu.westga.cs3212.dungeonsAndDragonProject.test.model.campaign;
import org.junit.jupiter.api.Test;
import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;

public class TestSetCampaignPlayers {

	@Test
	void testSetNullPlayers() {
		Player player = new Player(
				new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
		Campaign result = new Campaign("war", "stuff", 1);
		result.setDungeonMaster(player);
		
		result.setCampaignPlayers(null);
	}
}
