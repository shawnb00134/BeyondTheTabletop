package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Tools;

public class TestTools {

	@Test
	void testToolConstructorAllValid() {
		Tools tool = new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		
		assertEquals("Thieve's Tools", tool.getItemName());
		assertEquals("Dexterity", tool.getToolAbilityCheck());
		assertEquals("DC 15", tool.getToolCheckRoll());
		assertEquals(15, tool.getItemCost());
		assertEquals("Pick a lock or disarm a trap.", tool.getItemNotes());
	}
	
	@Test
	void testToolConstructorBlankName() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Tools("", "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorNullName() {
		assertThrows(NullPointerException.class, ()-> {
			new Tools(null, "Dexterity", "DC 15", 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorBlankAbilityCheck() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Tools("Thieve's Tools", "", "DC 15", 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorNullAbilityCheck() {
		assertThrows(NullPointerException.class, ()-> {
			new Tools("Thieve's Tools", null, "DC 15", 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorBlankRollCheck() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Tools("Thieve's Tools", "Dexterity", "", 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorNullRollCheck() {
		assertThrows(NullPointerException.class, ()-> {
			new Tools("Thieve's Tools", "Dexterity", null, 15, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorCostBelowZero() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Tools("Thieve's Tools", "Dexterity", "DC 15", -1, "Pick a lock or disarm a trap.");
		});
	}
	
	@Test
	void testToolConstructorNullToolNotes() {
		assertThrows(NullPointerException.class, ()-> {
			new Tools("Thieve's Tools", "Dexterity", "DC 15", 15, null);
		});
	}
}
