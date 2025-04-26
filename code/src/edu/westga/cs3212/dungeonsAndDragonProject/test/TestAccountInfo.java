package edu.westga.cs3212.dungeonsAndDragonProject.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Campaign;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Request;
import edu.westga.cs3212.dungeonsAndDragonProject.model.RequestType;

/**
 * Unit tests for the AccountInfo class.
 *
 * @author MoriaEl
 * @version Spring 2025
 */
class TestAccountInfo {
    
	private AccountInfo account;
    private final String accountId   = "acc-123";
    private final String firstName   = "Test";
    private final String lastName    = "User";
    private final String email       = "test.user@example.com";
    private final String username    = "testuser";
    private final String rawPassword = "MyP@ssw0rd!";

    @BeforeEach
    void setUp() {
        this.account = new AccountInfo(
            this.accountId, 
            this.firstName, 
            this.lastName, 
            this.email, 
            this.username, 
            this.rawPassword
        );
    }

    /**
     * Test constructor sets fields and defaults properly.
     */
    @Test
    void testConstructorSetsFields() {
        assertEquals(this.accountId, this.account.getAccountId());
        assertEquals(this.firstName, this.account.getFirstName());
        assertEquals(this.lastName, this.account.getLastName());
        assertEquals(this.email, this.account.getEmail());
        assertEquals(this.username, this.account.getUsername());
        
        assertFalse(this.account.isDungeonMaster(), 
                    "Dungeon Master flag should default to false");

        assertTrue(this.account.getIncomingRequests().isEmpty());
        assertTrue(this.account.getOutgoingRequests().isEmpty());
    }

    @Test
    void testVerifyPasswordCorrect() {
        assertTrue(this.account.verifyPassword(this.rawPassword),
                   "Verifying the correct password should return true");
    }

    @Test
    void testVerifyPasswordWrong() {
        assertFalse(this.account.verifyPassword("WrongPassword"),
                    "Verifying a wrong password should return false");
    }

    @Test
    void testDungeonMasterFlag() {
        assertFalse(this.account.isDungeonMaster());
        
        this.account.setDungeonMaster(true);
        assertTrue(this.account.isDungeonMaster());

        this.account.setDungeonMaster(false);
        assertFalse(this.account.isDungeonMaster());
    }

    @Test
    void testAddIncomingRequest() {
    	Campaign result = new Campaign("war", "stuff", 1);
        Request incoming = new Request("fromABC", this.accountId, RequestType.FRIEND, result.getName());
        this.account.addIncomingRequest(incoming);

        List<Request> incomingRequests = this.account.getIncomingRequests();
        assertEquals(1, incomingRequests.size(), 
                     "Should have 1 incoming request");
        assertSame(incoming, incomingRequests.get(0),
                   "Incoming request should match the one added");
    }

    @Test
    void testAddOutgoingRequest() {
    	Campaign result = new Campaign("war", "stuff", 1);
        Request outgoing = new Request(this.accountId, "toXYZ", RequestType.CAMPAIGN_INVITE, result.getName());
        this.account.addOutgoingRequest(outgoing);

        List<Request> outgoingRequests = this.account.getOutgoingRequests();
        assertEquals(1, outgoingRequests.size(), 
                     "Should have 1 outgoing request");
        assertSame(outgoing, outgoingRequests.get(0),
                   "Outgoing request should match the one added");
    }
    
    @SuppressWarnings("unlikely-arg-type")
	@Test
    void testAccountInfoDoesNotEqualRandomObject() {
    	Campaign result = new Campaign("war", "stuff", 1);
    	assertFalse(this.account.equals(result));
    }
    
	@Test
    void testHashCode() {
		assertTrue(this.account.hashCode() == Objects.hash(this.account.getAccountId()));
    }
}