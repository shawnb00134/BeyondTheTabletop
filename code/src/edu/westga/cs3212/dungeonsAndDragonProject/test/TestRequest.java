package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestStatus;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;

public class TestRequest {
	
    @Test
    void testConstructor() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	new Request("acc-123", "acc-124", RequestType.CAMPAIGN_INVITE, result.getName());
    }
    
    @Test
    void testGetters() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("acc-123", "acc-124", RequestType.CAMPAIGN_INVITE, result.getName());
    	
    	assertEquals("acc-123", newRequest.getFromAccountId());
    	assertEquals("acc-124", newRequest.getToAccountId());
    	assertEquals(RequestStatus.PENDING, newRequest.getStatus());
    	assertEquals(RequestType.CAMPAIGN_INVITE, newRequest.getRequestType());
    	assertEquals(result.getName(), newRequest.getCampaign());
    }
    
    @Test
    void testAccept() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("acc-123", "acc-124", RequestType.CAMPAIGN_INVITE, result.getName());
    	newRequest.accept();
    }
    
    @Test
    void testReject() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("acc-123", "acc-124", RequestType.CAMPAIGN_INVITE, result.getName());
    	newRequest.reject();
    }
    
    @Test
    void testCampaignInviteToString() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("acc-123", "acc-124", RequestType.CAMPAIGN_INVITE, result.getName());
    	
    	assertEquals("Campaign Invite from:  for campaign: war", newRequest.toString());
    }
    
    @Test
    void testCampaignJoinToString() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("acc-123", "acc-124", RequestType.CAMPAIGN_JOIN, result.getName());
    	
    	assertEquals("Request From: ", newRequest.toString());
    }
    
    @Test
    void testFakeRequestRealUserToString() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	Request newRequest = new Request("7528d626-b747-4d18-a3d4-9e3fe3fdcd95", "acc-124", RequestType.CAMPAIGN_JOIN, result.getName());
    	
    	assertEquals("Request From: one", newRequest.toString());
    }
    
}
