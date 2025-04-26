package edu.westga.cs3212.dungeonsAndDragonProject.test.viewmodel;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;

import com.google.gson.Gson;

import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.CharacterViewerViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.viewmodel.SystemContextViewModel;
import edu.westga.cs3212.dungeonsAndDragonProject.model.AccountInfo;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Attributes;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Character;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Creature;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Inventory;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Race;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Role;
import edu.westga.cs3212.dungeonsAndDragonProject.model.Size;
import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import edu.westga.cs3212.dungeonsAndDragonProject.server.ServerCommunicationHandler;
import edu.westga.cs3212.dungeonsAndDragonProject.test.server.TestServerCommunicationHandler.TestResponseUtil;

public class TestCharacterViewerViewModel {

	private AccountInfo account1;
	private File testCharactersFile;
	private static final Gson GSON = new Gson();
	private CharacterViewerViewModel characterViewer;
	private Character character;
	private String profList;
	private List<String> abilityList;
	private List<String> backgroundList;
	
	@Before
	public void setUp() throws Exception {
		this.account1 = new AccountInfo("539c5a55-f3d0-47c6-a552-f7441c04cb95", "John", "Doe", "john@example.com", "easy", "password123");
    	this.account1.addIncomingRequest(null);
    	SystemContextViewModel.getInstance().setCurrentAccount(this.account1);
    	
    	this.testCharactersFile = new File("temp-test-characters.json");
    	ServerCommunicationHandler.setCharacterFilePath(testCharactersFile.getPath());
    	
    	if (testCharactersFile.exists()) {
			testCharactersFile.delete();
		}
    	
    	overrideClientSendRequest();
    	
    	Attributes charAttributes = new Attributes(10, 12, 15, 14, 8, 10);
		Race charSpecies = new Race("Human", Creature.HUMANOID, Size.MEDIUM, 30, "short, hardy, and bearded.", 
				new String[] {"Dark Vision", "Dwarven Resilience", "Dwarven Tougness", "Stonecunning"});
		
		Set<String> featureSet = new HashSet<>();
		featureSet.add("feature1");
		
		Set<String> proficiencySet = new HashSet<>();
		proficiencySet.add("Animal Handling");
		
		Role charClass = new Role("Fighter", "Fighters are awesome", featureSet, proficiencySet);
		
		List<String> proficencies = new ArrayList<String>();
		proficencies.add("Running");
		proficencies.add("Swimming");
		
		List<String> weaponMastery = new ArrayList<String>();
		weaponMastery.add("Sword");
		weaponMastery.add("Axe");
		
		List<String> spells = new ArrayList<String>();
		spells.add("Fireball");
		
		Inventory charInventory = new Inventory();
		
		
		List<String> stringInventory = new ArrayList<String>();
		stringInventory.add("Sword");
		stringInventory.add("Axe");
		
		this.character = new Character("Testing John", 1, 10, 20, 20, charAttributes, charClass, proficencies, charInventory, weaponMastery, spells, spells, charSpecies, "Alignment",
				"Faith", "Hair", "Skin", "Eyes", "Height", "Weight", "Age", "Gender", false, "", this.account1.getAccountId());
		this.character.setInventoryByString(stringInventory);
		
		SystemContextViewModel.getInstance().setCharacterSelection(this.character);
		
		this.characterViewer = new CharacterViewerViewModel();
		
		this.profList = this.character.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getStrengthBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getWisdomBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getCharismaBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getIntelligenceBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getDexterityBonusValue() + "\n"
				+ this.character.getCharacterAttributes().getWisdomBonusValue();
		
		this.abilityList = new ArrayList<String>();
		this.abilityList.add("Class:\n");
		for (String ability : this.character.getCharacterClass().getFeatures()) {
			this.abilityList.add("\t" + ability + "\n");
		}
		
		this.abilityList.add("Race:\n");
		for (String ability : this.character.getCharacterSpecies().getTraits()) {
			this.abilityList.add("\t" + ability + "\n");
		}
		
		this.abilityList.add("Speed: " + this.character.getCharacterSpecies().getSpeed());
		
		this.abilityList.add("Skill Proficiencies: ");
		for (String skill : this.character.getSkillProficiencies()) {
			this.abilityList.add("\t" + skill + '\n');
		}
		
		this.backgroundList = new ArrayList<String>();
		String alignment = "Alignment: " + this.character.getCharacterAlignment();
		String faith = "Faith: " + this.character.getCharacterFaith();
		String hair = "Hair: " + this.character.getCharacterHair();
		String eyes = "Eyes: " + this.character.getCharacterEyes();
		String skin = "Skin: " + this.character.getCharacterSkin();
		String height = "Height: " + this.character.getCharacterHeight();
		String weight = "Weight: " + this.character.getCharacterWeight();
		String age = "Age: " + this.character.getCharacterAge();
		String gender = "Gender: " + this.character.getCharacterGender();

		this.backgroundList.add(alignment);
		this.backgroundList.add(faith);
		this.backgroundList.add(hair);
		this.backgroundList.add(eyes);
		this.backgroundList.add(skin);
		this.backgroundList.add(height);
		this.backgroundList.add(weight);
		this.backgroundList.add(age);
		this.backgroundList.add(gender);
	}
	
	@AfterEach
	void tearDown() {
		if (testCharactersFile.exists()) {
			testCharactersFile.delete();
		}
	}
	
	private void overrideClientSendRequest() throws Exception {
		Field field = Client.class.getDeclaredField("sendRequestDelegate");
		field.setAccessible(true);
		field.set(null, (Client.SendRequestDelegate) (req) -> TestResponseUtil.getFakeResponse(req));
	}
	
	@Test
	public void testHasInspirationDieAndUpdate() {
		assertFalse(this.characterViewer.getHasInspirationDie().getValue());
		this.characterViewer.updateHasInspiration();
		assertTrue(this.characterViewer.getHasInspirationDie().getValue());
		this.characterViewer.updateHasInspiration();
		assertFalse(this.characterViewer.getHasInspirationDie().getValue());
	}
	
	@Test
	public void testCharacterHealth() {
		assertEquals("20/20", this.characterViewer.getCharHealth().getValue());
		
		this.characterViewer.getHealthModifier().setValue("5");
		this.characterViewer.damageCharacter();
		assertEquals("15/20", this.characterViewer.getCharHealth().getValue());
		
		this.characterViewer.getHealthModifier().setValue("2");
		this.characterViewer.healCharacter();
		assertEquals("17/20", this.characterViewer.getCharHealth().getValue());
	}
	
	@Test
	public void testCharacterHealthBadValues() {
		assertEquals("20/20", this.characterViewer.getCharHealth().getValue());
		
		this.characterViewer.getHealthModifier().setValue(null);
		this.characterViewer.damageCharacter();
		this.characterViewer.healCharacter();
		
		this.characterViewer.getHealthModifier().setValue("");
		this.characterViewer.damageCharacter();
		this.characterViewer.healCharacter();
		
		assertEquals("20/20", this.characterViewer.getCharHealth().getValue());
	}
	
	@Test
	public void testCoinPurseChanges() {
		assertEquals("Coin: 0", this.characterViewer.getCoinPurse().getValue());
		
		this.characterViewer.getCoinModifier().setValue("200");
		this.characterViewer.addCoinToPurse();
		assertEquals("Coin: 200", this.characterViewer.getCoinPurse().getValue());
		
		this.characterViewer.getCoinModifier().setValue("50");
		this.characterViewer.removeCoinFromPurse();
		assertEquals("Coin: 150", this.characterViewer.getCoinPurse().getValue());
		
	}

	@Test
	public void testGetInventory() {
		assertEquals(this.character.getInventoryByString(), this.characterViewer.getInventoryList().getValue());
	}
	
	@Test
	public void testSpellList() {
		assertEquals(this.character.getCharacterSpells(), this.characterViewer.getSpellList().getValue());
	}
	
	@Test
	public void testFeatList() {
		assertEquals(this.character.getCharacterFeats(), this.characterViewer.getFeatList().getValue());
	}
	
	@Test
	public void testBackgroundList() {
		assertEquals(this.backgroundList, this.characterViewer.getBackgroundList().getValue());
	}
	
	@Test
	public void testNotes() {
		assertEquals(null, this.characterViewer.getCharacterCampaignNotes().getValue());
		
		this.characterViewer.getCharacterCampaignNotes().setValue("Testing Notes");
		this.characterViewer.saveCharacterCurrentStatus();
		assertEquals("Testing Notes", this.characterViewer.getCharacterCampaignNotes().getValue());
	}
	
	@Test
	public void testGetInformation() {
		assertEquals(this.character.getCharacterName(), this.characterViewer.getCharName().getValue());
		assertEquals("Level: " + this.character.getCharacterLevel(), this.characterViewer.getCharLevel().getValue());
		assertEquals("Race: " + this.character.getCharacterSpecies(), this.characterViewer.getCharRace().getValue());
		assertEquals("Class: " + this.character.getCharacterClass().getName(), this.characterViewer.getCharClass().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getStrength()), this.characterViewer.getCharStr().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getStrengthBonusValue()), this.characterViewer.getCharStrBonus().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getDexterity()), this.characterViewer.getCharDex().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getDexterityBonusValue()), this.characterViewer.getCharDexBonus().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getConstitution()), this.characterViewer.getCharCon().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getConstitutionBonusValue()), this.characterViewer.getCharConBonus().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getIntelligence()), this.characterViewer.getCharInt().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getIntelligenceBonusValue()), this.characterViewer.getCharIntBonus().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getWisdom()), this.characterViewer.getCharWis().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getWisdomBonusValue()), this.characterViewer.getCharWisBonus().getValue());
		
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getCharisma()), this.characterViewer.getCharCha().getValue());
		assertEquals(Integer.toString(this.character.getCharacterAttributes().getCharismaBonusValue()), this.characterViewer.getCharChaBonus().getValue());
		
		assertEquals("AC: " + Integer.toString(this.character.getCharacterArmorClass()), this.characterViewer.getCharAC().getValue());
		
		assertEquals(this.profList, this.characterViewer.getProficiencyBonus().getValue());
		
		assertEquals(this.abilityList, this.characterViewer.getAbilityList().getValue());
	}
}
