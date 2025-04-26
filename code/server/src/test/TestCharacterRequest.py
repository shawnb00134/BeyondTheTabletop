'''
Created on Apr 10, 2025

@author: Shawn Bretthauer
'''
import os
import unittest
from src.request_server import request_handler, constants
from src.request_server.request_handler import characters

class TestCharacterManagement(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.accounts_backup = None
        cls.campaigns_backup = None
        cls.characters_backup = None

        if os.path.exists(constants.ACCOUNTS_FILE):
            with open(constants.ACCOUNTS_FILE, 'r') as f:
                cls.accounts_backup = f.read()
        if os.path.exists(constants.CAMPAIGNS_FILE):
            with open(constants.CAMPAIGNS_FILE, 'r') as f:
                cls.campaigns_backup = f.read()
        if os.path.exists(constants.CHARACTERS_FILE):
            with open(constants.CHARACTERS_FILE, 'r') as f:
                cls.characters_backup = f.read()

    def setUp(self):
        request_handler.accounts.clear()
        request_handler.campaigns.clear()
        request_handler.characters.clear()
    
    def test_create_character(self):
        character_data = {
            "playerOwnerID": "4adc8cbe-555b-47b3-815b-f9355800db06",
            "characerName": "Test Character",
            "characterLevel": 1,
            "characterArmorClass": 15,
            "characterMaxHealthPoints": 20,
            "characterCurrentHealthPoints": 20,
            "characterAttributes": {"strength": 0, "dexterity": 0, "constitution": 0, "intelligence": 0, "wisdom": 0, "charisma": 0},
            "characterClass": {"name": "Wizard", "flavorText": "A scholarly magic-user capable of manipulating the structures of reality", "features": ["Second Wind", "Divine Domain", "Sneak Attack"], "proficiencies": ["Shield", "Medium Armor", "Rapier"]},
            "characterInventory": {"characterInventory": [], "inventoryCapacity": -2147483648, "currentInventoryWeight": 0, "characterCoinPurse": 999}, 
            "characterInventoryByString": ["Sword", "Axe", "Staff"],
            "weaponMasteries": ["Sword", "Axe"],
            "characterSpells": ["Fireball", "Ice Blast"],
            "characterSpecies": {"name": "Human", "creature": "HUMANOID", "size": "MEDIUM", "speed": 30, "flavorText": "Dull", "traits": ["Dark Vision", "Elven Lineage", "Fey Ancestry", "Keen Senses", "Trance"]}, 
            "alignment": "Bad guy", 
            "faith": "None", 
            "hair": "Shaved", 
            "skin": "Leathery", 
            "eyes": "Sunken", 
            "height": "Tall", 
            "weight": "Skinny", 
            "age": "Old as dirt", 
            "gender": "Unknown"
        }
        
        request = {
            "type": constants.CREATE_CHARACTER,
            "character": character_data,
            "characterName": "Test Character",
            "preExisting": False
        }
        
        response = request_handler.create_character(request)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertIn("Test Character", request_handler.characters)
        self.assertEqual(request_handler.characters["Test Character"], character_data)
        
    def test_get_characer(self):
        character_data = {
             "playerOwnerID": "4adc8cbe-555b-47b3-815b-f9355800db06", 
             "characterName": "1", 
             "characterLevel": 1, 
             "characterArmorClass": 0, 
             "characterMaxHealthPoints": 1, 
             "characterCurrentHealthPoints": 1, 
             "characterAttributes": {"strength": 0, "dexterity": 0, "constitution": 0, "intelligence": 0, "wisdom": 0, "charisma": 0}, 
             "characterClass": {"name": "Fighter", "flavorText": "A master of martial combat, skilled with a variety of weapons and armor", "features": ["Second Wind", "Divine Domain", "Sneak Attack"], "proficiencies": ["Shield", "Medium Armor", "Rapier"]}, 
             "characterInventory": {"characterInventory": [], "inventoryCapacity": -2147483648, "currentInventoryWeight": 0, "characterCoinPurse": 0}, "characterInventoryByString": [], "weaponMasteries": [None, None], "characterSpells": [], 
             "characterSpecies": {"name": "Human", "creature": "HUMANOID", "size": "MEDIUM", "speed": 30, "flavorText": "Dull", "traits": ["Dark Vision", "Elven Lineage", "Fey Ancestry", "Keen Senses", "Trance"]}
        }
        
        request = {
            "type": constants.CREATE_CHARACTER,
            "character": character_data,
            "characterName": "Test Character",
            "playerOwnerID": "4adc8cbe-555b-47b3-815b-f9355800db06",
            "preExisting": False
        }
        response = request_handler.create_character(request)
    
        create_request = {
            "type": constants.GET_CHARACTER,
            "character": character_data
            }
        response = request_handler.get_character(request)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertEqual(response["character result"], character_data)
    
    def test_list_characters(self):
        character_data = {
            "playerOwnerID": "4adc8cbe-555b-47b3-815b-f9355800db06",
            "characerName": "Test Character",
            "characterLevel": 1,
            "characterArmorClass": 15,
            "characterMaxHealthPoints": 20,
            "characterCurrentHealthPoints": 20,
            "characterAttributes": {"strength": 0, "dexterity": 0, "constitution": 0, "intelligence": 0, "wisdom": 0, "charisma": 0},
            "characterClass": {"name": "Wizard", "flavorText": "A scholarly magic-user capable of manipulating the structures of reality", "features": ["Second Wind", "Divine Domain", "Sneak Attack"], "proficiencies": ["Shield", "Medium Armor", "Rapier"]},
            "characterInventory": {"characterInventory": [], "inventoryCapacity": -2147483648, "currentInventoryWeight": 0, "characterCoinPurse": 999}, 
            "characterInventoryByString": ["Sword", "Axe", "Staff"],
            "weaponMasteries": ["Sword", "Axe"],
            "characterSpells": ["Fireball", "Ice Blast"],
            "characterSpecies": {"name": "Human", "creature": "HUMANOID", "size": "MEDIUM", "speed": 30, "flavorText": "Dull", "traits": ["Dark Vision", "Elven Lineage", "Fey Ancestry", "Keen Senses", "Trance"]}, 
            "alignment": "Bad guy", 
            "faith": "None", 
            "hair": "Shaved", 
            "skin": "Leathery", 
            "eyes": "Sunken", 
            "height": "Tall", 
            "weight": "Skinny", 
            "age": "Old as dirt", 
            "gender": "Unknown"
        }
        
        request = {
            "type": constants.CREATE_CHARACTER,
            "character": character_data,
            "characterName": "Test Character",
            "preExisting": False
        }
        response = request_handler.create_character(request)
        
        create_request = {
            "type": constants.LIST_CHARACTERS,
            "playerOwnerID": "4adc8cbe-555b-47b3-815b-f9355800db06"
            }
        response = request_handler.get_character(request)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertEqual(request_handler.characters["Test Character"], character_data)

    # def test_remove_character(self):
    #
    # def test_update_character(self):
    #
    # def test_overwrite_characters(self):
        
    @classmethod
    def tearDownClass(cls):
        if cls.accounts_backup is not None:
            with open(constants.ACCOUNTS_FILE, 'w') as f:
                f.write(cls.accounts_backup)
        if cls.campaigns_backup is not None:
            with open(constants.CAMPAIGNS_FILE, 'w') as f:
                f.write(cls.campaigns_backup)
        if cls.characters_backup is not None:
            with open(constants.CHARACTERS_FILE, 'w') as f:
                f.write(cls.characters_backup)

if __name__ == "__main__":
    unittest.main()