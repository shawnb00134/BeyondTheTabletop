package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;

class TestRace {
	
	@Test
	void testEmptyName() {
		
		assertThrows(IllegalArgumentException.class,
				()->{
					new Race("", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
							new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
	
				}
			);
	}
	
	@Test
	void testNullCreatureType() {
		
		assertThrows(IllegalArgumentException.class,
				()->{
					new Race("Elf",null, Size.MEDIUM, 30, "short, hardy, and bearded.", 
							new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
	
				}
			);
	}
	
	@Test
	void testNullSizeType() {
		assertThrows(IllegalArgumentException.class,()->{
			
			new Race("Elf", Creature.HUMANOID, null, 30, "short, hardy, and bearded.", 
					new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		});
		
	}
	
	@Test
	void testEmptyFlavorText() {
		assertThrows(IllegalArgumentException.class,()->{
			
			new Race("Elf",Creature.HUMANOID, Size.MEDIUM, 30, "", 
					new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
			
		});
		
	}
	
	
	
	@Test
	void testEmptyTraits() {
		assertThrows(IllegalArgumentException.class,()->{
			
			new Race("Elf",Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", new String[] {});
			
		});
		
	}
	
	
	
	
	@Test
	void testChangeRace() {
		Race dwarf = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		Race dummy = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		dummy.setName("dummy");
		dummy.setCreature(Creature.HUMANOID);
		dummy.setFlavorText("dummy");
		dummy.setSpeed(0);
		dummy.setSize(Size.SMALL);
		String[] list = new String[] {"a","b","c"};
		dummy.setTraits(list);
		dummy.getTraits();
		
		assertNotEquals(dummy.getName(), dwarf.getName(), "checking name of race");
		assertEquals(dummy.getCreature(), dwarf.getCreature(), "checking races type");
		assertNotEquals(dummy.getSize(), dwarf.getSize(), "checking races size");
		assertNotEquals(dummy.getSpeed(), dwarf.getSpeed(), "checking races speed");
		assertNotEquals(dummy.getFlavorText(), dwarf.getFlavorText(), "checking races flavor text");
		assertNotEquals(dummy.getTraitsAsArrayList(), dwarf.getTraitsAsArrayList(), "checking races traits");
		
	}
	
	@Test
	void testValidRace() {
		Race result = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "numerous and the most versatile and culturally diverse species", 
				new String[] {"Resourceful", "Skillful", "Versatile"});
		
		String text = "numerous and the most versatile and culturally diverse species";
		ArrayList<String> traits = new ArrayList<String>();
		traits.add("Resourceful");
		traits.add("Skillful");
		traits.add("Versatile");
		
		assertEquals(Creature.HUMANOID, result.getCreature(), "checking races type");
		assertEquals(Size.MEDIUM, result.getSize(), "checking races size");
		assertEquals(30, result.getSpeed(), "checking races speed");
		assertEquals(text, result.getFlavorText(), "checking races flavor text");
		assertEquals(traits, result.getTraitsAsArrayList(), "checking races traits");
	}
	
	@Test
	void testToString() {
		Race result = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "numerous and the most versatile and culturally diverse species", 
				new String[] {"Resourceful", "Skillful", "Versatile"});
		
		assertNotEquals("a", result.toString(), "checking to string method output: \n" + result.toString() );
	}
	
	@Test
	void testRaceDetails() {
		Race result = new Race("dwarf",Creature.HUMANOID, Size.MEDIUM, 30, "numerous and the most versatile and culturally diverse species", 
				new String[] {"Resourceful", "Skillful", "Versatile"});
		
		String details = "dwarf\n" + "Species: Race\n" + "Type: HUMANOID" + "   Speed: 30  " + "Size: MEDIUM\n" 
				+ "numerous and the most versatile and culturally diverse species\n" + "Traits: Resourceful, Skillful, Versatile";
		
		assertEquals(details, result.raceDetails());
	}
}
