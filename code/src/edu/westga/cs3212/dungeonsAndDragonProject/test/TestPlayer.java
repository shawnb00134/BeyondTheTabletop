package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;

public class TestPlayer {

    private AccountInfo account1;
    private AccountInfo account2;
    private Player player1;
    private Player player2;
    private Campaign campaign;

    @BeforeEach
    void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        this.account2 = new AccountInfo("acc-124", "Jane", "Smith", "jane@example.com", "janesmith", "securepass");

        this.player1 = new Player(this.account1);
        this.player2 = new Player(this.account2);

        this.campaign = new Campaign("Dragon War", "Epic battle with dragons", 5);
    }

    @Test
    void testValidPlayerConstructor() {
        Player player = new Player(this.account1);
        assertEquals(this.account1, player.getAccountInfo());
        assertTrue(player.getCharacters().isEmpty());
    }

    @Test
    void testPlayerConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    @Test
    void testCreateAndDeleteCharacter() {
        Character character = new Character("Warrior", 10, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null);
        this.player1.createCharacter(character);
        
        assertTrue(this.player1.getCharacters().contains(character));

        this.player1.deleteCharacter(character);
        assertFalse(this.player1.getCharacters().contains(character));
    }

    @Test
    void testCreateCharacterNull() {
        assertThrows(IllegalArgumentException.class, () -> this.player1.createCharacter(null));
    }

    @Test
    void testDeleteCharacterNotFound() {
        Character character = new Character("Rogue", 10, 0, 0, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, null, null);
        assertThrows(IllegalArgumentException.class, () -> this.player1.deleteCharacter(character));
    }

    @Test
    void testJoinAndLeaveCampaign() {
        this.player1.joinCampaign(this.campaign);
        assertTrue(this.player1.getJoinedCampaigns().contains(this.campaign));

        this.player1.leaveCampaign(this.campaign);
        assertFalse(this.player1.getJoinedCampaigns().contains(this.campaign));
    }

    @Test
    void testLeaveCampaignNotJoined() {
        assertThrows(IllegalArgumentException.class, () -> this.player1.leaveCampaign(this.campaign));
    }

    @Test
    void testJoinCampaignNull() {
        assertThrows(IllegalArgumentException.class, () -> this.player1.joinCampaign(null));
    }

    @Test
    void testLeaveCampaignNull() {
        assertThrows(IllegalArgumentException.class, () -> this.player1.leaveCampaign(null));
    }

    @Test
    void testCreateCampaign() {
    	this.player1.createCampaign(this.campaign);
        assertEquals(this.player1.getAccountInfo().getUsername(), this.campaign.getDungeonMaster().getAccountInfo().getUsername());
    }

    @Test
    void testCreateCampaignNull() {
        assertThrows(IllegalArgumentException.class, () -> this.player1.createCampaign(null));
    }
    
    @Test
    void dungeonMasterCreatesCampaign() {
        AccountInfo account3 = new AccountInfo("acc-125", "Jane", "Smith", "janeeee@example.com", "janesmith", "securepass");
        DungeonMaster player3 = new DungeonMaster(account3);
        
        player3.createCampaign(this.campaign);
        assertEquals(player3, this.campaign.getDungeonMaster());
    }

    @Test
    void testGetIncomingRequests() {
        Request request = new Request(this.account1.getAccountId(), this.account2.getAccountId(), RequestType.FRIEND, null);
        this.account2.addIncomingRequest(request);

        List<Request> requests = this.player2.getIncomingRequests();
        assertEquals(1, requests.size());
        assertEquals(request, requests.get(0));
    }

    @Test
    void testPlayerToString() {
        assertEquals("johndoe", this.player1.toString());
    }
}