package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.campaignCreationViewModel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.DungeonMaster;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Player;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CampaignCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleListProperty;
import javafx.stage.Stage;

public class TestCampaignViewModel {
    
    private CampaignCreationViewModel viewModel;
    private AccountInfo account1;
    @Before
    public void setUp() {
    	this.account1 = new AccountInfo("539c5a55-f3d0-47c6-a552-f7441c04cb95", "John", "Doe", "john@example.com", "easy", "password123");
    	this.account1.addIncomingRequest(null);
    	SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
        this.viewModel = new CampaignCreationViewModel(true);
    }
    
    @Test
    public void testInitialProperties() {
        assertNotNull(viewModel.getName());
        assertNotNull(viewModel.getDescription());
        assertNotNull(viewModel.getCampaignLimit());
        assertEquals("", viewModel.getName().get());
        assertEquals("", viewModel.getDescription().get());
        assertEquals("", viewModel.getCampaignLimit().get());
        assertNotNull(viewModel.getCharacters());
        assertNotNull(viewModel.getCampaigns());
        assertNotNull(viewModel.getIncomingReq());
        assertFalse(viewModel.isRequestCompleted());
        assertNotNull(viewModel.getPlayerOfCurrentAccount());
    }
    
    @Test
    public void testLoadCampaignData() {
        Campaign camp = new Campaign("TestCamp", "TestDesc", 5);
        viewModel.loadCampaignData(camp);
        assertEquals("TestCamp", viewModel.getName().get());
        assertEquals("TestDesc", viewModel.getDescription().get());
        assertEquals("5", viewModel.getCampaignLimit().get());
        
        viewModel.loadCampaignData(null);
        assertEquals("TestCamp", viewModel.getName().get());
    }
    
    @Test
    public void testGetPlayersInCampaign() {
        Campaign camp = new Campaign("Camp", "Desc", 10);
        Player p1 = new Player(new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword"));
        Player p2 = new Player(new AccountInfo("123456", "John", "Doe", "john.doe@example.com", "johndoe6", "securepassword"));
        camp.getPlayers().add(p1);
        camp.getPlayers().add(p2);
        
        ListProperty<Player> playersProp = viewModel.getPlayersInCampaign(camp);
        assertEquals(2, playersProp.size());
        assertEquals(p1, playersProp.get(0));
        assertEquals(p2, playersProp.get(1));
        
        viewModel.getPlayers().clear();
        viewModel.getPlayers().add(p1);
        assertEquals(1, viewModel.getPlayers().size());
    }
    
    @Test
    public void testSetAndGetCampaign() {
        Campaign camp = new Campaign("Camp", "Desc", 7);
        viewModel.setCampaign(camp);
        ObjectProperty<Campaign> campProp = viewModel.getCampaign();
        assertEquals(camp, campProp.get());
    }
    
    @Test
    public void testSetAndGetIncomingReq() {
    	Campaign campaign = new Campaign("war", "stuff", 1);
        Request req = new Request("from1","to1", RequestType.CAMPAIGN_INVITE, campaign.getName());
        List<Request> reqList = new ArrayList<>();
        reqList.add(req);
        ListProperty<Request> lp = new SimpleListProperty<>(FXCollections.observableArrayList(reqList));
        viewModel.setIncomingReq(lp);
        assertEquals(lp, viewModel.getIncomingReq());
    }
    
    @Test
    public void testRequestCompletedProperty() {
        viewModel.setRequestCompleted(true);
        assertTrue(viewModel.isRequestCompleted());
        viewModel.setRequestCompleted(false);
        assertFalse(viewModel.isRequestCompleted());
    }
    
    @Test
    public void testCreateCampaign() {
        viewModel.getName().set("NewCamp");
        viewModel.getDescription().set("NewDesc");
        viewModel.getCampaignLimit().set("15");
        
        int initialSize = viewModel.getCampaigns().size();
        viewModel.createCampaign();
        assertEquals(initialSize + 1, viewModel.getCampaigns().size());
        
        Campaign created = viewModel.getCampaigns().get(viewModel.getCampaigns().size() - 1);
        assertEquals("NewCamp", created.getName());
        assertEquals("NewDesc", created.getDescription());
        assertEquals(15, created.getCampaignLimit());
    }
    
    @Test
    public void testSendInviteToPlayer() {
        Campaign dummyCamp = new Campaign("InviteCamp", "Desc", 5);
        viewModel.setCampaign(dummyCamp);
        AccountInfo result = viewModel.sendInviteToPlayer("someone", dummyCamp, (Stage) null);
        assertNull(result);
    }
    
    @Test
    public void testLoadJoinedCampaignsAndLoadCharactersInTestMode() {
        assertTrue(viewModel.getCampaigns().isEmpty());
        assertTrue(viewModel.getCharacters().isEmpty());
    }
    
    
    @Test
    public void testFindIndexOfRemovedRequest() {
    	Campaign campaign = new Campaign("war", "stuff", 1);
        Request req1 = new Request("from1","to1", RequestType.CAMPAIGN_INVITE, campaign.getName());
        Request req2 = new Request("from1","to2", RequestType.CAMPAIGN_INVITE, campaign.getName());
        List<Request> list = new ArrayList<>();
        list.add(req1);
        list.add(req2);
        
        int index1 = viewModel.findIndexOfRemovedRequest(list, req1);
        int index2 = viewModel.findIndexOfRemovedRequest(list, req2);
        assertEquals(0, index1);
        assertEquals(1, index2);
        
        Request req3 = new Request("from1","to3", RequestType.CAMPAIGN_INVITE, campaign.getName());
        int index3 = viewModel.findIndexOfRemovedRequest(list, req3);
        assertEquals(0, index3);
    }
    
    @Test
    public void testFindAccountByIdAndFindCampaignByName() {
        assertNull(viewModel.findAccountById("nonexistent"));
        assertNull(viewModel.findCampaignByName("nonexistentCampaign"));
    }
    
    @Test
    public void testEditCampaign() {
        Campaign original = new Campaign("Orig", "OrigDesc", 10);
        viewModel.getCampaigns().add(original);
        viewModel.getName().set("Edited");
        viewModel.getDescription().set("EditedDesc");
        viewModel.getCampaignLimit().set("20");
        viewModel.editCampaign(original);
        
        assertFalse(viewModel.getCampaigns().contains(original));
    }
    
    @Test
    public void testCheckIfDungeonMaster() {
        AccountInfo dmAccount = new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword");
        Campaign camp = new Campaign("Camp", "Desc", 10);
        camp.setDungeonMaster(new DungeonMaster(dmAccount));
        viewModel.getCampaigns().add(camp);
        
        assertTrue(viewModel.checkIfDungeonMaster(dmAccount));
        AccountInfo other = new AccountInfo("123456", "John", "Doe", "john.doe@example.com", "johndoe6", "securepassword");
        assertFalse(viewModel.checkIfDungeonMaster(other));
    }
    
    @Test
    public void testCheckIfDungeonMasterAndNoCampaigns() {
        AccountInfo dmAccount = new AccountInfo("12345", "John", "Doe", "john.doe@example.com", "johndoe", "securepassword");
        assertFalse(viewModel.checkIfDungeonMaster(dmAccount));
        AccountInfo other = new AccountInfo("123456", "John", "Doe", "john.doe@example.com", "johndoe6", "securepassword");
        assertFalse(viewModel.checkIfDungeonMaster(other));
    }
}
