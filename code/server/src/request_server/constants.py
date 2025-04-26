'''
Created on Mar 25, 2025

@author: kg00281 && MORIAEL
'''
import os 

current_dir = os.path.dirname(os.path.abspath(__file__))
java_project_dir = os.path.abspath(os.path.join(current_dir, "..", "..", "..", "src", "edu","westga","cs3212", "dungeonsAndDragonProject", "assets"))


ACCOUNTS_FILE = os.path.join(java_project_dir, "json", "accounts.json")
CAMPAIGNS_FILE = os.path.join(java_project_dir, "json", "campaigns.json")
CHARACTERS_FILE = os.path.join(java_project_dir, "json", "characters.json")


"account management"
CREATE_ACCOUNT = "create account"
LOGIN = "login"
GET_ACCOUNT = "get account"
UPDATE_ACCOUNT = "update account"
DELETE_ACCOUNT = "delete account"
LIST_USERS = "list users"
CHECK_USERNAME = "check username"
LIST_ACCOUNTS = "list accounts"

"character management"
CREATE_CHARACTER = "create character"
GET_CHARACTER = "get character"
UPDATE_CHARACTER = "update character"
DELETE_CHARACTER = "delete character"
SAVE_CHARACTER = "save character"
LIST_CHARACTERS = "list characters"

"campaign management"
CREATE_CAMPAIGN = "create campaign"
UPDATE_CAMPAIGN = "update campaign"
JOIN_CAMPAIGN = "join campaign"
INVITE_TO_CAMPAIGN  = "invite to campaign"
GET_CAMPAIGN = "get campaign"
LIST_CAMPAIGNS = "list campaigns"
REMOVE_CAMPAIGN = "remove campaign"
OVERWRITE_CAMPAIGN = "overwrite campaign"

"request handling"
SEND_REQUEST = "send request"
ACCEPT_REQUEST = "accept request"
DENY_REQUEST = "deny request"
LIST_REQUESTS = "list requests" 

"feedback handling"
SUCCESS = "success"
FAIL = "fail"

"resources management"
UPLOAD_RESOURCE    = "upload resource"
DOWNLOAD_RESOURCE  = "download resource"
LIST_RESOURCES     = "list resources"
DELETE_RESOURCE    = "delete resource"
TOGGLE_VISIBILITY  = "toggle visibility"
RESOURCES_FILE = os.path.join(java_project_dir, "json", "resources.json")









