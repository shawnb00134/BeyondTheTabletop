package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the DisplayClassInformation method of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestDisplayClassInformation {

	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testValidCall() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		Set<String> testFeatures = new HashSet<String>(Arrays.asList("test feature"));
		Set<String> testProficiencies = new HashSet<String>(Arrays.asList("test proficiency"));
		Role role = new Role("A Test role", "A Test role", testFeatures, testProficiencies);
		
		viewModel.displayClassInformation(role);

		assertAll(() -> {
			assertEquals("A Test role" + "\n" + "Features: test feature \nProficencies: test proficiency ", viewModel.updatedClassInformation().get());
		});
	}
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
