'''
Created on Apr 8, 2025

@author: kg00281
'''
import os
import unittest
from src.request_server import request_handler, constants

class TestCampaignManagement(unittest.TestCase):
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

    def test_create_campaign(self):
        campaign_data = {
            "name": "easy2", 
            "description": "adawdw", 
            "players": [], 
            "dungeonMaster": {
                "accountInfo": {
                    "accountId": "539c5a55-f3d0-47c6-a552-f7441c04cb95", "firstName": "easy", "lastName": "peasy", "email": "ep@gmail.com", "username": "easy", "hashedPassword": "1wKZ/FdJ8rEXA4/32XsaGJbmeda5g4IheKFtVqVV98I=", "salt": "ToCyn3uc3zGr3cpIWbRT8Q==", "incomingRequests": [], "outgoingRequests": []}, "characters": [], "joinedCampaigns": []}, 
            "notes": "", 
            "campaignLimit": 1, 
            "campaignPlayers": {}
            }

        request = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_data,
            "name": "easy2"
        }
        response = request_handler.create_campaign(request)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertIn("easy2", request_handler.campaigns)
        self.assertEqual(request_handler.campaigns["easy2"], campaign_data)

    def test_invite_to_campaign(self):
        
        recipient_initial = {"accountId": "876409dc-468d-44e7-ba5d-a5a820fe36df", "firstName": "star", "lastName": "blaze", "email": "starb@gmail.com", "username": "star123", "hashedPassword": "XgRfSMy9OttdLRPQOxV0kKkSP1R6PAIgEPUUbWvx16A=", "salt": "g8Oz7QX0Ye3FIoFz9Y7JyA==", "isDungeonMaster": True, "incomingRequests": [], "outgoingRequests": []}
        dm_initial = {"accountId": "539c5a55-f3d0-47c6-a552-f7441c04cb95", "firstName": "easy", "lastName": "peasy", "email": "ep@gmail.com", "username": "easy", "hashedPassword": "1wKZ/FdJ8rEXA4/32XsaGJbmeda5g4IheKFtVqVV98I=", "salt": "ToCyn3uc3zGr3cpIWbRT8Q==", "isDungeonMaster": True, "incomingRequests": [], "outgoingRequests": []}

        
        request_handler.accounts["star123"] = recipient_initial
        request_handler.accounts["easy"] = dm_initial

        
        recipient_updated = {"accountId": "876409dc-468d-44e7-ba5d-a5a820fe36df", "firstName": "star", "lastName": "blaze", "email": "starb@gmail.com", "username": "star123", "hashedPassword": "XgRfSMy9OttdLRPQOxV0kKkSP1R6PAIgEPUUbWvx16A=", "salt": "g8Oz7QX0Ye3FIoFz9Y7JyA==", "isDungeonMaster": True, "incomingRequests": [{"fromAccountId": "539c5a55-f3d0-47c6-a552-f7441c04cb95", "toAccountId": "876409dc-468d-44e7-ba5d-a5a820fe36df", "requestType": "CAMPAIGN_INVITE", "status": "PENDING", "campaign": "easy"}], "outgoingRequests": []}
        dm_updated = {"accountId": "539c5a55-f3d0-47c6-a552-f7441c04cb95", "firstName": "easy", "lastName": "peasy", "email": "ep@gmail.com", "username": "easy", "hashedPassword": "1wKZ/FdJ8rEXA4/32XsaGJbmeda5g4IheKFtVqVV98I=", "salt": "ToCyn3uc3zGr3cpIWbRT8Q==", "isDungeonMaster": True, "incomingRequests": [], "outgoingRequests": [{"fromAccountId": "539c5a55-f3d0-47c6-a552-f7441c04cb95", "toAccountId": "876409dc-468d-44e7-ba5d-a5a820fe36df", "requestType": "CAMPAIGN_INVITE", "status": "PENDING", "campaign": "easy"}]}

        invite_req = {
            "type": constants.INVITE_TO_CAMPAIGN,
            "recipient username": "star123",
            "recipient": recipient_updated,
            "dungeon master username": "easy",
            "dungeon master": dm_updated
        }
        response = request_handler.invite_to_campaign(invite_req)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertEqual(request_handler.accounts["star123"], recipient_updated)
        self.assertEqual(request_handler.accounts["easy"], dm_updated)

    def test_get_campaign(self):
        campaign_data = {
            "name": "CampaignGet",
            "dungeonMaster": {"accountInfo": {"accountId": "dm001"}},
            "players": []
        }
        create_req = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_data,
            "name": "CampaignGet"
        }
        create_resp = request_handler.create_campaign(create_req)
        self.assertEqual(create_resp["success code"], constants.SUCCESS)

        request = {
            "type": constants.GET_CAMPAIGN,
            "name": "CampaignGet"
        }
        response = request_handler.get_campaign(request)
        self.assertEqual(response["success code"], constants.SUCCESS)
        self.assertEqual(response["campaign result"], campaign_data)

    def test_list_campaigns(self):
        
        account_data = {"accountId": "a001", "username": "user1"}
        request_handler.accounts["user1"] = account_data

        campaign_dm = {
            "name": "Campaign01",
            "dungeonMaster": {"accountInfo": {"accountId": "a001"}},
            "players": []
        }
        create_dm = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_dm,
            "name": "Campaign01"
        }
        resp_dm = request_handler.create_campaign(create_dm)
        self.assertEqual(resp_dm["success code"], constants.SUCCESS)

        campaign_player = {
            "name": "Campaign02",
            "dungeonMaster": {"accountInfo": {"accountId": "dm002"}},
            "players": [{"accountInfo": {"accountId": "a001"}}]
        }
        create_player = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_player,
            "name": "Campaign02"
        }
        resp_player = request_handler.create_campaign(create_player)
        self.assertEqual(resp_player["success code"], constants.SUCCESS)

        list_req = {
            "type": constants.LIST_CAMPAIGNS,
            "username": "user1"
        }
        list_resp = request_handler.list_campaigns(list_req)
        self.assertEqual(list_resp["success code"], constants.SUCCESS)
        joined = list_resp["joinedCampaigns"]
        
        self.assertEqual(len(joined), 2)

    def test_remove_campaign(self):
        campaign_data = {
            "dungeonMaster": {"accountInfo": {"accountId": "dm003"}},
            "players": []
        }
        create_req = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_data,
            "name": "CampaignToRemove"
        }
        create_resp = request_handler.create_campaign(create_req)
        self.assertEqual(create_resp["success code"], constants.SUCCESS)

        remove_req = {
            "type": constants.REMOVE_CAMPAIGN,
            "name": "CampaignToRemove"
        }
        remove_resp = request_handler.remove_campaign(remove_req)
        self.assertEqual(remove_resp["success code"], constants.SUCCESS)
        self.assertNotIn("CampaignToRemove", request_handler.campaigns)

    def test_update_campaign(self):
        campaign_data = {
            "dungeonMaster": {"accountInfo": {"accountId": "dm004"}},
            "players": []
        }
        create_req = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_data,
            "name": "CampaignOld"
        }
        create_resp = request_handler.create_campaign(create_req)
        self.assertEqual(create_resp["success code"], constants.SUCCESS)

        new_campaign_data = {
            "dungeonMaster": {"accountInfo": {"accountId": "dm004_updated"}},
            "players": []
        }
        update_req = {
            "type": constants.UPDATE_CAMPAIGN,
            "campaign name": "CampaignOld",
            "new campaign": new_campaign_data,
            "new name": "CampaignNew"
        }
        update_resp = request_handler.update_campaign(update_req)
        self.assertEqual(update_resp["success code"], constants.SUCCESS)
        self.assertNotIn("CampaignOld", request_handler.campaigns)
        self.assertIn("CampaignNew", request_handler.campaigns)
        self.assertEqual(request_handler.campaigns["CampaignNew"], new_campaign_data)

    def test_overwrite_campaign(self):
        campaign_data = {
            "dungeonMaster": {"accountInfo": {"accountId": "dm005"}},
            "players": []
        }
        create_req = {
            "type": constants.CREATE_CAMPAIGN,
            "campaign": campaign_data,
            "name": "CampaignToOverwrite"
        }
        create_resp = request_handler.create_campaign(create_req)
        self.assertEqual(create_resp["success code"], constants.SUCCESS)

        new_campaign_data = {
            "dungeonMaster": {"accountInfo": {"accountId": "dm005_overwritten"}},
            "players": []
        }
        overwrite_req = {
            "type": constants.OVERWRITE_CAMPAIGN,
            "name": "CampaignToOverwrite",
            "campaign": new_campaign_data
        }
        overwrite_resp = request_handler.overwrite_campaign(overwrite_req)
        self.assertEqual(overwrite_resp["success code"], constants.SUCCESS)
        self.assertEqual(request_handler.campaigns["CampaignToOverwrite"], new_campaign_data)

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
