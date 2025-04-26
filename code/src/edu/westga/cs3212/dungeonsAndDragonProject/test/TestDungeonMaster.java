package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;

public class TestDungeonMaster {

    @BeforeAll
    public static void initJavaFX() {
        new JFXPanel();
        Platform.setImplicitExit(false);
    }
    
    private Stage createStage() throws InterruptedException {
        final Stage[] stageHolder = new Stage[1];
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            stageHolder[0] = new Stage();
            latch.countDown();
        });
        latch.await();
        return stageHolder[0];
    }
    
    @Test
    void testConstructorSetsDMFlag() {
        AccountInfo acc = new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret");
        DungeonMaster dm = new DungeonMaster(acc);
        assertTrue(dm.getAccountInfo().isDungeonMaster());
    }
    
    @Test
    void testSendCampaignInviteThrowsForNullUsername() throws InterruptedException {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Campaign camp = new Campaign("Camp1", "desc", 4);
        Stage stage = createStage();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> dm.sendCampaignInvite(null, camp, stage));
        assertEquals("Username and campaign cannot be null or empty", ex.getMessage());
    }
    
    @Test
    void testSendCampaignInvite() throws InterruptedException {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Campaign camp = new Campaign("Camp1", "desc", 4);
        dm.createCampaign(camp);
        Stage stage = createStage();
        
        dm.sendCampaignInvite("one", camp, stage);
        
    }
    
    @Test
    void testSendCampaignInviteThrowsForBlankUsername() throws InterruptedException {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Campaign camp = new Campaign("Camp1", "desc", 4);
        Stage stage = createStage();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> dm.sendCampaignInvite("   ", camp, stage));
        assertEquals("Username and campaign cannot be null or empty", ex.getMessage());
    }
    
    @Test
    void testSendCampaignInviteThrowsForNullCampaign() throws InterruptedException {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Stage stage = createStage();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> dm.sendCampaignInvite("recipient", null, stage));
        assertEquals("Username and campaign cannot be null or empty", ex.getMessage());
    }

    @Test
    void testDeleteCampaignNullThrowsException() {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Exception ex = assertThrows(IllegalArgumentException.class, () -> dm.deleteCampaign(null));
        assertEquals("Campaign cannot be null", ex.getMessage());
    }
    
    @Test
    void testDeleteCampaignRemovesAllPlayers() {
        Campaign camp = new Campaign("Camp2", "desc", 4);
        camp.addPlayer(new Player(new AccountInfo("p1", "Player", "One", "p1@example.com", "user1", "pass")));
        camp.addPlayer(new Player(new AccountInfo("p2", "Player", "Two", "p2@example.com", "user2", "pass")));
        assertEquals(2, camp.getPlayers().size());
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        dm.deleteCampaign(camp);
        assertEquals(0, camp.getPlayers().size());
    }
    
    @Test
    void testTransferCampaignOwnershipNullThrowsException() {
        DungeonMaster dm = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm@example.com", "dmUser", "secret"));
        Campaign camp = new Campaign("CampTransfer", "desc", 4);
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> dm.transferCampaignOwnership(null, dm));
        assertEquals("Campaign and new Dungeon Master cannot be null", ex1.getMessage());
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> dm.transferCampaignOwnership(camp, null));
        assertEquals("Campaign and new Dungeon Master cannot be null", ex2.getMessage());
    }
    
    @Test
    void testTransferCampaignOwnershipUpdatesDM() {
        DungeonMaster dm1 = new DungeonMaster(new AccountInfo("dm1", "Dungeon", "Master", "dm1@example.com", "dmUser", "secret"));
        Campaign camp = new Campaign("CampTransfer", "desc", 4);
        camp.setDungeonMaster(dm1);
        DungeonMaster dm2 = new DungeonMaster(new AccountInfo("dm2", "Dungeon", "Master", "dm2@example.com", "dmUser2", "pass"));
        dm1.transferCampaignOwnership(camp, dm2);
        assertEquals("dm2", camp.getDungeonMaster().getAccountInfo().getAccountId());
    }
}
