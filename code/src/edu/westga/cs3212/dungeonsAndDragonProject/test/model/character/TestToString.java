package edu.westga.cs3212.dungeonsAndDragonProject.test.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;

public class TestToString {

	@Test
	void testToString() {
		Character character = new Character();
		
		assertEquals("", character.getCharacterName());
		assertEquals(8, character.getCharacterAttributes().getCharisma());
		assertEquals(8, character.getCharacterAttributes().getDexterity());
		assertEquals(8, character.getCharacterAttributes().getConstitution());
		assertEquals(8, character.getCharacterAttributes().getIntelligence());
		assertEquals(8, character.getCharacterAttributes().getStrength());
		assertEquals(8, character.getCharacterAttributes().getWisdom());
		assertEquals(null, character.getCharacterSpecies());
		assertEquals(null, character.getCharacterClass());
		assertTrue(character.getCharacterInventory() != null);
		
		character.setCharacterName("John Doe");
		assertEquals("John Doe", character.toString());
	}
}
