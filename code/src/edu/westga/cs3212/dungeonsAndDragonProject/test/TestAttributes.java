package edu.westga.cs3212.dungeonsAndDragonProject.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;

public class TestAttributes {

	@Test
	void testBaseLineStartingAttributesIndividualCall() {
		Attributes charAttributes = new Attributes();
		
		assertEquals(8, charAttributes.getStrength());
		assertEquals(8, charAttributes.getDexterity());
		assertEquals(8, charAttributes.getConstitution());
		assertEquals(8, charAttributes.getIntelligence());
		assertEquals(8, charAttributes.getWisdom());
		assertEquals(8, charAttributes.getCharisma());
	}
	
	@Test
	void testBaseLineStartingAttributesListCall() {
		Attributes charAttributes = new Attributes();
		List<Integer> baseLineAttributes = new ArrayList<Integer>();
		
		for (int i = 0; i < 6; i++) {
			baseLineAttributes.add(8);
		}
		
		assertEquals(baseLineAttributes, charAttributes.getAllAttributes());
	}
	
	@Test
	void testSettingAttributesTrue() {
		Attributes charAttributes = new Attributes();
		int strength = 15;
		int dexterity = 10;
		int constitution = 10;
		int intelligence = 8;
		int wisdom = 10;
		int charisma = 13;
		
		assertTrue(charAttributes.setStrength(strength));
		assertTrue(charAttributes.setDexterity(dexterity));
		assertTrue(charAttributes.setConstituion(constitution));
		assertTrue(charAttributes.setIntelligence(intelligence));
		assertTrue(charAttributes.setWisdom(wisdom));
		assertTrue(charAttributes.setCharisma(charisma));
	}
	
	@Test
	void testSettingAttributesFalseNegative() {
		Attributes charAttributes = new Attributes();
		int strength = -1;
		int dexterity = -1;
		int constitution = -1;
		int intelligence = -1;
		int wisdom = -1;
		int charisma = -1;
		
		assertFalse(charAttributes.setStrength(strength));
		assertFalse(charAttributes.setDexterity(dexterity));
		assertFalse(charAttributes.setConstituion(constitution));
		assertFalse(charAttributes.setIntelligence(intelligence));
		assertFalse(charAttributes.setWisdom(wisdom));
		assertFalse(charAttributes.setCharisma(charisma));
	}
	
	@Test
	void testSettingAttributesTrueZero () {
		Attributes charAttributes = new Attributes();
		int strength = 0;
		int dexterity = 0;
		int constitution = 0;
		int intelligence = 0;
		int wisdom = 0;
		int charisma = 0;
		
		assertTrue(charAttributes.setStrength(strength));
		assertTrue(charAttributes.setDexterity(dexterity));
		assertTrue(charAttributes.setConstituion(constitution));
		assertTrue(charAttributes.setIntelligence(intelligence));
		assertTrue(charAttributes.setWisdom(wisdom));
		assertTrue(charAttributes.setCharisma(charisma));
	}
	
	@Test
	void testSettingAttriutesTrueOne() {
		Attributes charAttributes = new Attributes();
		int strength = 1;
		int dexterity = 1;
		int constitution = 1;
		int intelligence = 1;
		int wisdom = 1;
		int charisma = 1;
		
		assertTrue(charAttributes.setStrength(strength));
		assertTrue(charAttributes.setDexterity(dexterity));
		assertTrue(charAttributes.setConstituion(constitution));
		assertTrue(charAttributes.setIntelligence(intelligence));
		assertTrue(charAttributes.setWisdom(wisdom));
		assertTrue(charAttributes.setCharisma(charisma));
	}
	
	@Test
	void testGettingBonusValues() {
		Attributes charAttributes = new Attributes();
		int strength = 15;
		int dexterity = 16;
		int constitution = 12;
		int intelligence = 15;
		int wisdom = 11;
		int charisma = 13;
		
		charAttributes.setStrength(strength);
		charAttributes.setDexterity(dexterity);
		charAttributes.setConstituion(constitution);
		charAttributes.setIntelligence(intelligence);
		charAttributes.setWisdom(wisdom);
		charAttributes.setCharisma(charisma);
		
		List<Integer> bonusValues = new ArrayList<Integer>();
		bonusValues.add(2);
		bonusValues.add(3);
		bonusValues.add(1);
		bonusValues.add(2);
		bonusValues.add(0);
		bonusValues.add(1);
		
		assertEquals(bonusValues, charAttributes.getAllBonusValues());
	}
	
	@Test
	void testOverloadedConstructor() {
		Attributes charAttributes = new Attributes(1, 2, 3, 4, 5, 6);
		
		assertEquals(1, charAttributes.getStrength());
		assertEquals(2, charAttributes.getDexterity());
		assertEquals(3, charAttributes.getConstitution());
		assertEquals(4, charAttributes.getIntelligence());
		assertEquals(5, charAttributes.getWisdom());
		assertEquals(6, charAttributes.getCharisma());
	}
	
	@Test
	void testOverLoadedConstructorNegative() {
		Attributes charAttributes = new Attributes(-1, -1, -1, -1, -1, -1);
		
		assertEquals(8, charAttributes.getStrength());
		assertEquals(8, charAttributes.getDexterity());
		assertEquals(8, charAttributes.getConstitution());
		assertEquals(8, charAttributes.getIntelligence());
		assertEquals(8, charAttributes.getWisdom());
		assertEquals(8, charAttributes.getCharisma());
	}
	
	@Test
	void testSetAllAttributesTrue() {
		Attributes charAttributes = new Attributes();
		
		assertTrue(charAttributes.setAllAttributes(1, 2, 3, 4, 5, 6));
		
		assertEquals(1, charAttributes.getStrength());
		assertEquals(2, charAttributes.getDexterity());
		assertEquals(3, charAttributes.getConstitution());
		assertEquals(4, charAttributes.getIntelligence());
		assertEquals(5, charAttributes.getWisdom());
		assertEquals(6, charAttributes.getCharisma());
	}
	
	@Test
	void testSetAllAtributesFalse() {
		Attributes charAttributes = new Attributes();
		
		assertFalse(charAttributes.setAllAttributes(-1, 0, 0, 0, 0, 0));
		assertFalse(charAttributes.setAllAttributes(0, -1, 0, 0, 0, 0));
		assertFalse(charAttributes.setAllAttributes(0, 0, -1, 0, 0, 0));
		assertFalse(charAttributes.setAllAttributes(0, 0, 0, -1, 0, 0));
		assertFalse(charAttributes.setAllAttributes(0, 0, 0, 0, -1, 0));
		assertFalse(charAttributes.setAllAttributes(0, 0, 0, 0, 0, -1));
	}
	
	@Test
	void testGetIndivdualBonusValues() {
		Attributes charAttributes = new Attributes();
		
		charAttributes.setAllAttributes(10, 12, 14, 12, 15, 14);
		
		assertEquals(0, charAttributes.getStrengthBonusValue());
		assertEquals(1, charAttributes.getDexterityBonusValue());
		assertEquals(2, charAttributes.getConstitutionBonusValue());
		assertEquals(1, charAttributes.getIntelligenceBonusValue());
		assertEquals(2, charAttributes.getWisdomBonusValue());
		assertEquals(2, charAttributes.getCharismaBonusValue());
	}
}
