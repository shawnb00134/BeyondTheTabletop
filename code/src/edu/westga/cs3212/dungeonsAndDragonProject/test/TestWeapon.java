package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Weapon;

public class TestWeapon {

	@Test
	void testConstructorAllValid() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		Weapon weapon = new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		
		assertEquals("Club", weapon.getItemName());
		assertEquals("1d8 Bludgeoning", weapon.getWeaponDamage());
		assertEquals(properties, weapon.getWeaponProperties());
		assertEquals("Slow", weapon.getWeaponMastery());
		assertEquals(5, weapon.getWeaponWeight());
		assertEquals(10, weapon.getItemCost());
		assertEquals("Heavy club.", weapon.getItemNotes());
	}
	
	@Test
	void testConstructorBlankName() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorNullName() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed"); 
		assertThrows(NullPointerException.class, ()-> {
			new Weapon(null, "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorBlankDamage() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed");
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("Club", "", properties, "Slow", 5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorNullProperties() {
		List<String> properties = null;
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorBlankMastery() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed"); 
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("Club", "1d8 Bludgeoning", properties, "", 5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorDamageBelowZero() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed"); 
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", -5, 10, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorCostBelowZero() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed"); 
		assertThrows(IllegalArgumentException.class, ()-> {
			new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, -1, "Heavy club.");
		});
	}
	
	@Test
	void testConstructorNullItemNotes() {
		List<String> properties = new ArrayList<String>();
		properties.add("Two-Handed"); 
		assertThrows(NullPointerException.class, ()-> {
			new Weapon("Club", "1d8 Bludgeoning", properties, "Slow", 5, 10, null);
		});
	}
	
}
