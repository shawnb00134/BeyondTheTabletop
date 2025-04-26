package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Armor;

public class TestArmor {

	@Test
	void testConstructorAllValid() {
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		
		assertEquals("Padded Armor", armor.getItemName());
		assertEquals("Light", armor.getArmorType());
		assertEquals(11, armor.getArmorClass());
		assertEquals(0, armor.getArmorStrengthRequirement());
		assertTrue(armor.getStealthDisadvantage());
		assertEquals(8, armor.getArmorWeight());
		assertEquals(5, armor.getItemCost());
		assertEquals("Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.", armor.getItemNotes());
	}
	
	@Test
	void testConstructorBlankName() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("", "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorNullName() {
		assertThrows(NullPointerException.class, ()-> {
			new Armor(null, "Light", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorBlankArmorType() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("Padded Armor", "", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorNullArmorType() {
		assertThrows(NullPointerException.class, ()-> {
			new Armor("Padded Armor", null, 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorArmorTypeMedium() {
		Armor armor = new Armor("Padded Armor", "Medium", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		
		assertEquals("Medium", armor.getArmorType());
	}
	
	@Test
	void testConstructorArmorTypeHeavy() {
		Armor armor = new Armor("Padded Armor", "Heavy", 11, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		
		assertEquals("Heavy", armor.getArmorType());
	}
	
	@Test
	void testConstructorArmorClassBelowZero() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("Padded Armor", "Light", -1, 0, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorStrengthRequirementBelowZero() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("Padded Armor", "Light", 1, -1, true, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorNoStealthDisadvantage() {
		Armor armor = new Armor("Padded Armor", "Light", 11, 0, false, 8, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		
		assertFalse(armor.getStealthDisadvantage());
	}
	
	@Test
	void testConstructorNegativeArmorWeight() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("Padded Armor", "Light", 1, 0, true, -1, 5, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorNegativeArmorCost() {
		assertThrows(IllegalArgumentException.class, ()-> {
			new Armor("Padded Armor", "Light", 1, 0, true, 8, -1, "Armor Class: 11 + Dex modifier. Disadvantage on stealth checks.");
		});
	}
	
	@Test
	void testConstructorArmorNotesNull() {
		assertThrows(NullPointerException.class, ()-> {
			new Armor("Padded Armor", "Light", 1, 0, true, 8, 5, null);
		});
	}
}
