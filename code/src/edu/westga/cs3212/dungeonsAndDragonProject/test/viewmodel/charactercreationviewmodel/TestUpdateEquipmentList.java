package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel.charactercreationviewmodel;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterCreationViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;

/**
 * Tests the updateEquipmentList of the CharacterCreationViewModel
 * 
 * @author Liam Worthington
 * @version Spring 2025
 */
public class TestUpdateEquipmentList {
	
	private AccountInfo account1;
	
	@BeforeEach
	void setUp() {
        this.account1 = new AccountInfo("acc-123", "John", "Doe", "john@example.com", "johndoe", "password123");
        SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
	}
	
	@Test
	void testWhenValidEquipmentToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.equipmentProperty().set("Iron Sword");
		viewModel.updateEquipmentList();

		assertAll(() -> {
			assertEquals("Iron Sword", viewModel.getEquipmentList().get().getFirst());
			assertEquals(1, viewModel.getEquipmentList().size());
		});
	}
	
	@Test
	void testWhenNullEquipmentToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.equipmentProperty().setValue(null);;
		viewModel.updateEquipmentList();

		assertAll(() -> {
			assertEquals(0, viewModel.getEquipmentList().size());
		});
	}
	
	@Test
	void testWhenEmptyEquipmentToAdd() {
		CharacterCreationViewModel viewModel = new CharacterCreationViewModel();
		
		viewModel.equipmentProperty().setValue("");
		viewModel.updateEquipmentList();

		assertAll(() -> {
			assertEquals(0, viewModel.getEquipmentList().size());
		});
	}
	@AfterClass
    public static void tearDownAfterClass() throws Exception {
        Client.sendRequest("exit");
    }
}
