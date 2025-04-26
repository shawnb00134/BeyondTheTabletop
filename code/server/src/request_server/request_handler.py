'''
Created on Mar 25, 2025

@author: kg00281
'''
from src.request_server import constants
import hashlib
import os
import base64
import json

os.makedirs(os.path.dirname(constants.RESOURCES_FILE), exist_ok=True)
images_dir = os.path.join(os.path.dirname(constants.RESOURCES_FILE), "images")
os.makedirs(images_dir, exist_ok=True)

accounts = {}
campaigns = {}
characters = {}

def handle_requests(request):

    response = {}

    if request["type"] == constants.CREATE_ACCOUNT:
        response = create_account(request)
    elif request["type"] == constants.LOGIN:
        response = login(request)
    elif request["type"] == constants.GET_ACCOUNT:
        response = get_account(request)
    elif request["type"] == constants.UPDATE_ACCOUNT:
        response = update_account(request)
    elif request["type"] == constants.DELETE_ACCOUNT:
        response = delete_account(request)
    elif request["type"] == constants.LIST_USERS:
        response = list_users(request)
    elif request["type"] == constants.CHECK_USERNAME:
        response = check_username(request)
    elif request["type"] == constants.CREATE_CHARACTER:
        response = create_character(request)
    elif request["type"] == constants.GET_CHARACTER:
        response = get_character(request)
    elif request["type"] == constants.UPDATE_CHARACTER:
        response = update_character(request)
    elif request["type"] == constants.DELETE_CHARACTER:
        response = delete_character(request)
    elif request["type"] == constants.LIST_CHARACTERS:
        response = list_characters(request)
    elif request["type"] == constants.CREATE_CAMPAIGN:
        response = create_campaign(request)
    elif request["type"] == constants.UPDATE_CAMPAIGN:
        response = update_campaign(request)
    elif request["type"] == constants.INVITE_TO_CAMPAIGN:
        response = invite_to_campaign(request)
    elif request["type"] == constants.GET_CAMPAIGN:
        response = get_campaign(request)
    elif request["type"] == constants.LIST_CAMPAIGNS:
        response = list_campaigns(request)
    elif request["type"] == constants.REMOVE_CAMPAIGN:
        response = remove_campaign(request)
    elif request["type"] == constants.OVERWRITE_CAMPAIGN:
        response = overwrite_campaign(request)
    elif request["type"] == constants.SEND_REQUEST:
        response = send_request(request)
    elif request["type"] == constants.ACCEPT_REQUEST:
        response = accept_request(request)
    elif request["type"] == constants.DENY_REQUEST:
        response = deny_request(request)
    elif request["type"] == constants.LIST_REQUESTS:
        response = list_requests(request)
    elif request["type"] == constants.UPLOAD_RESOURCE:
        response = upload_resource(request)
    elif request["type"] == constants.LIST_RESOURCES:
        response = list_resources(request)
    elif request["type"] == constants.DOWNLOAD_RESOURCE:
        response = download_resource(request)
    elif request["type"] == constants.DELETE_RESOURCE:
        response = delete_resource(request)
    elif request["type"] == constants.TOGGLE_VISIBILITY:
        response = toggle_visibility(request)
    else:
        return {"success code": constants.FAIL, "error description": "request type not found"}
    return response

def load_accounts_file():
    with open(constants.ACCOUNTS_FILE, 'r') as file:
        for line in file:
            data = json.loads(line)
            accounts[data["username"]] = data

def load_campaign_file():
    with open(constants.CAMPAIGNS_FILE, 'r') as file:
        for line in file: 
            data = json.loads(line)
            campaigns[data["name"]] = data

def load_character_file():
    with open(constants.CHARACTERS_FILE, 'r') as file:
        for line in file:
            data = json.loads(line)
            characters[data["characterName"]] = data
            
def save_all_accounts():
    try:
        with open(constants.ACCOUNTS_FILE, "w") as file:
            for acc in accounts.values():
                file.write(json.dumps(acc) + "\n")
    except Exception as e:
        print(f"[ERROR] Could not save accounts: {str(e)}")
        
"account management"

def create_account(request):
    load_accounts_file()

    required_fields = ["accountId", "firstName", "lastName", "email", "username", "password", "confirmPassword"]
    for field in required_fields:
        value = request.get(field, "")
        if not isinstance(value, str) or not value.strip():
            return {
                "success code": constants.FAIL,
                "error description": f"{field} is required"
            }

    first_name = request["firstName"].strip()
    last_name = request["lastName"].strip()
    email = request["email"].strip().lower()
    username = request["username"].strip()
    raw_password = request["password"].strip()
    confirm_password = request["confirmPassword"].strip()

    if len(first_name) < 3:
        return {
            "success code": constants.FAIL,
            "error description": "First name must be at least 3 characters"
        }

    if len(last_name) < 3:
        return {
            "success code": constants.FAIL,
            "error description": "Last name must be at least 3 characters"
        }

    import re
    email_regex = r"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$"
    if not re.match(email_regex, email):
        return {
            "success code": constants.FAIL,
            "error description": "Invalid email format"
        }

    if len(raw_password) < 8:
        return {
            "success code": constants.FAIL,
            "error description": "Password must be at least 8 characters"
        }
        
    if raw_password != confirm_password:
        return {
            "success code": constants.FAIL,
            "error description": "Password and confirmation do not match"
        }

    if username in accounts:
        return {
            "success code": constants.FAIL,
            "error description": "Username already exists"
        }

    for acc_data in accounts.values():
        if acc_data.get("email", "").lower() == email:
            return {
                "success code": constants.FAIL,
                "error description": "Email already in use"
            }

    salt = generate_salt()
    hashed_password = create_hash(raw_password, salt)

    new_account = {
        "accountId": request["accountId"],
        "firstName": first_name,
        "lastName": last_name,
        "email": email,
        "username": username,
        "hashedPassword": hashed_password,
        "salt": salt,
        "isDungeonMaster": False,
        "incomingRequests": [],
        "outgoingRequests": []
    }

    accounts[username] = new_account

    try:
        with open(constants.ACCOUNTS_FILE, 'a') as file:
            file.write(json.dumps(new_account) + "\n")
    except Exception as e:
        return {
            "success code": constants.FAIL,
            "error description": f"Error saving account: {str(e)}"
        }

    return {
        "success code": constants.SUCCESS,
        "accountInfo": new_account
    }

def login(request):
    load_accounts_file()
    username = request["username"]
    password = request["password"]
    
    if username not in accounts:
        return {"success code": constants.FAIL, "error description": "username does not exist"}
    
    stored_salt = accounts[username]["salt"]
    stored_hash = accounts[username]["hashedPassword"]
    
    hashed_input_password = create_hash(password, stored_salt)
    
    if hashed_input_password == stored_hash:
        account_info = accounts[username]
        return {"success code": constants.SUCCESS, "accountInfo": account_info}
    else:
        return {"success code": constants.FAIL, "error description": "password incorrect"}
    
    
def get_account(request):
    load_accounts_file()

    username = request.get("username", "").strip()
    account_id = request.get("accountId", "").strip()

    if not username and not account_id:
        return {
            "success code": constants.FAIL,
            "error description": "Either 'username' or 'accountId' is required"
        }

    if username:
        if username not in accounts:
            return {
                "success code": constants.FAIL,
                "error description": "User not found"
            }
        return {
            "success code": constants.SUCCESS,
            "accountInfo": accounts[username]
        }

    for user_data in accounts.values():
        if user_data.get("accountId") == account_id:
            return {
                "success code": constants.SUCCESS,
                "accountInfo": user_data
            }

    return {
        "success code": constants.FAIL,
        "error description": "No account with that ID found"
    }


def update_account(request):
    load_accounts_file()

    username = request.get("username", "").strip()
    if not username:
        return {
            "success code": constants.FAIL,
            "error description": "username is required"
        }

    if username not in accounts:
        return {
            "success code": constants.FAIL,
            "error description": "account does not exist"
        }

    account = accounts[username]

    if "firstName" in request:
        account["firstName"] = request["firstName"].strip()
    if "lastName" in request:
        account["lastName"] = request["lastName"].strip()

    if "new_email" in request:
        new_email = request["new_email"].strip().lower()
        for other_username, other_data in accounts.items():
            if other_username != username and other_data.get("email", "").lower() == new_email:
                return {
                    "success code": constants.FAIL,
                    "error description": "email already in use by another account"
                }
        account["email"] = new_email

    if "new_password" in request and request["new_password"].strip():
        salt = generate_salt()
        hashed_password = create_hash(request["new_password"].strip(), salt)
        account["salt"] = salt
        account["hashedPassword"] = hashed_password

    accounts[username] = account

    save_all_accounts()

    return {
        "success code": constants.SUCCESS,
        "accountInfo": account
    }


def delete_account(request):
    load_accounts_file()

    username = request.get("username", "").strip()
    if not username:
        return {"success code": constants.FAIL, "error description": "username is required"}

    if username not in accounts:
        return {"success code": constants.FAIL, "error description": "account does not exist"}

    del accounts[username]

    save_all_accounts()

    return {
        "success code": constants.SUCCESS,
        "message": f"Account '{username}' deleted successfully"
    }


def list_users(request):
    load_accounts_file()

    search_text = request.get("search", "").strip().lower()

    results = []
    for user_data in accounts.values():
        username = user_data["username"]
        if search_text and search_text not in username.lower():
            continue

        partial = {
            "username": username,
            "firstName": user_data["firstName"],
            "lastName": user_data["lastName"]
        }
        results.append(partial)

    return {
        "success code": constants.SUCCESS,
        "users": results
    }
    
    


def check_username(request):
    load_accounts_file()

    username = request.get("username", "").strip()
    if not username:
        return {"success code": constants.FAIL, "error description": "username required"}

    if username in accounts:
        return {
            "success code": constants.SUCCESS,
            "isTaken": True
        }
    else:
        return {
            "success code": constants.SUCCESS,
            "isTaken": False
        }

"character management"
def overwrite_character_file():
    '''print("Overwrite Called")'''
    with open(constants.CHARACTERS_FILE, "w") as file:
        for name, character in characters.items():
            '''print("Looped")'''
            json.dump(character, file)
            file.write("\n")

def create_character(request):
    character = request["character"]
    name = request["characterName"]
    preExisting = request["preExisting"]
    
    if preExisting:
        delete_character(request)
    
    characters[name] = character
    
    overwrite_character_file()
    return {"success code": constants.SUCCESS}

def get_character(request):
    load_character_file()
    characterN = request["characterName"]
    accountId = request["playerOwnerID"]
    
    try:
        for characterId, character in characters.items():
            if character["playerOwnerID"] == accountId and characters[characterN]:
                character_result = characters[characterN]
        return {"success code": constants.SUCCESS, "character result": character_result}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}

def update_character(request):
    former_character = request["character name"]
    new_character = request["new character"]
    new_character_name = request["new name"]
    
    characters[new_character_name] = characters.pop(former_character)
    characters[new_character_name] = new_character
    
    overwrite_character_file()
    
    return {"success code": constants.SUCCESS}

def delete_character(request):
    characterN = request["characterName"]
    accountId = request["playerOwnerID"]
    '''print(characterN)'''
    '''print(accountId)'''
    
    try:
        if characterN in characters:
            if characters[characterN]["playerOwnerID"] == accountId:
                del characters[characterN]
                overwrite_character_file()
                return {"success code": constants.SUCCESS}
        
        return {"success code": constants.FAIL, "error description": "Character not found or not owned by account"}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}
    
def list_characters(request):
    load_character_file()
    accountId = request["playerOwnerID"]
    players_characters_list = []
    
    for characterId, character in characters.items():
        if character["playerOwnerID"] == accountId:
            players_characters_list.append(character)
    return {"success code": constants.SUCCESS, "createdCharacters": players_characters_list}



"campaign management"
def overwrite_campaign_file():
    
    with open(constants.CAMPAIGNS_FILE, "w") as file:
        for name, campaign in campaigns.items():
            json.dump(campaign, file)
            file.write("\n")
            
def overwrite_account_file_for_campaign_invite():
    with open(constants.ACCOUNTS_FILE, "w") as file:
        for username, account in accounts.items():
            json.dump(account, file)
            file.write("\n")
    
def create_campaign(request):
    campaign = request["campaign"]
    name = request["name"]
    
    campaigns[name]= campaign
    
    overwrite_campaign_file()
    return {"success code": constants.SUCCESS}


def invite_to_campaign(request):
    recipient_username = request["recipient username"]
    recipient = request["recipient"]
    dm_username = request["dungeon master username"]
    dm = request["dungeon master"]
    
    try:
        del accounts[recipient_username]
        accounts[recipient_username] = recipient
        
        del accounts[dm_username]
        accounts[dm_username] = dm
        
        overwrite_account_file_for_campaign_invite()
        
        return {"success code": constants.SUCCESS}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}
    
    

def get_campaign(request):
    load_campaign_file()
    campaignN = request["name"]
    
    try:
        campaign_result = campaigns[campaignN]
        return {"success code": constants.SUCCESS, "campaign result": campaign_result}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}

def list_campaigns(request):
    load_campaign_file()
    username = request["username"]

    account = accounts[username]
    account_id = account["accountId"]
    
    account_in_campaign_list = []
    
    for campaign_name, campaign in campaigns.items():
        if campaign["dungeonMaster"]["accountInfo"]["accountId"] == account_id:
            account_in_campaign_list.append(campaign)
        
        for player in campaign["players"]:
            if player["accountInfo"]["accountId"] == account_id:
                account_in_campaign_list.append(campaign)
                break
    
    return {"success code": constants.SUCCESS, "joinedCampaigns": account_in_campaign_list}

def remove_campaign(request):
    
    campaignN = request["name"]
    
    try: 
        del campaigns[campaignN]
        overwrite_campaign_file()
        return {"success code": constants.SUCCESS}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}
          
def update_campaign(request):  
    former_campaign = request["campaign name"] 
    new_campaign = request["new campaign"]
    new_campaign_name = request["new name"]
    
    campaigns[new_campaign_name] = campaigns.pop(former_campaign)
    campaigns[new_campaign_name] = new_campaign  
    
    overwrite_campaign_file()    
     
    return {"success code": constants.SUCCESS}
    
def overwrite_campaign(request):
    campaign_name = request["name"]
    campaign_object = request["campaign"]
    
    campaigns[campaign_name] = campaign_object
    overwrite_campaign_file()
    return {"success code": constants.SUCCESS}





"request management"
def send_request(request):
    # TODO: Implement the logic for this function
    pass

def accept_request(request):
    recipient_username = request["recipient username"]
    recipient = request["recipient"]
    sender_username = request["sender username"]
    sender = request["sender"]
    campaign_name = request["campaign name"]
    campaign = request["campaign"]
    
    try:
        del accounts[recipient_username]
        accounts[recipient_username] = recipient
        
        del accounts[sender_username]
        accounts[sender_username] = sender
        
        campaigns[campaign_name]= campaign
        
        overwrite_account_file_for_campaign_invite()        
        overwrite_campaign_file()
        
        return {"success code": constants.SUCCESS}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}

def deny_request(request):
    recipient_username = request["recipient username"]
    recipient = request["recipient"]
    sender_username = request["sender username"]
    sender = request["sender"]
    
    try:
        del accounts[recipient_username]
        accounts[recipient_username] = recipient
        
        del accounts[sender_username]
        accounts[sender_username] = sender
        
        
        overwrite_account_file_for_campaign_invite()        
        overwrite_campaign_file()
        
        return {"success code": constants.SUCCESS}
    except KeyError as e:
        return {"success code": constants.FAIL, "error description": str(e)}

def list_requests(request):
    load_accounts_file()  

    username = request.get("username", "").strip()
    if not username:
        return {
            "success code": constants.FAIL,
            "error description": "username is required"
        }

    if username not in accounts:
        return {
            "success code": constants.FAIL,
            "error description": "Account not found"
        }
    
    account = accounts[username]
    outgoing_requests = account.get("outgoingRequests", [])

    return {
        "success code": constants.SUCCESS,
        "outgoingRequests": outgoing_requests
    }
    
    "resource Management"
def upload_resource(request):
    # pull the campaign from the top‑level of the message, not from data
    camp = request.get("campaign")
    data = request.get("data", {})
    name = data.get("name")
    b64  = data.get("content")

    if not camp or not name or not b64:
        return {
            "success code": constants.FAIL,
            "error description": "server error: missing campaign, name or content"
        }

    # decode + save file
    path = os.path.join(images_dir, camp, name)
    os.makedirs(os.path.dirname(path), exist_ok=True)
    try:
        with open(path, "wb") as imgf:
            imgf.write(base64.b64decode(b64))
    except Exception as ex:
        return {
            "success code": constants.FAIL,
            "error description": f"could not write image file: {ex}"
        }

    # append metadata to resources.json
    try:
        with open(constants.RESOURCES_FILE, "a") as meta:
            meta.write(json.dumps({
                "campaign": camp,
                "name":     name,
                "filename": os.path.basename(path),
                "visible":  True
            }) + "\n")
    except Exception as e:
        return {
            "success code": constants.FAIL,
            "error description": f"could not write metadata: {e}"
        }

    return {"success code": constants.SUCCESS}

def list_resources(request):
    camp = request.get("campaign")
    out  = []
    if not camp:
        return {"success code": constants.FAIL, "error description": "missing campaign"}
    with open(constants.RESOURCES_FILE, "r") as meta:
        for line in meta:
            obj = json.loads(line)
            if obj.get("campaign") == camp:
                out.append(obj)
    return {"success code": constants.SUCCESS, "resources": out}

def download_resource(request):
    camp = request.get("campaign")
    name = request.get("name")
    path = os.path.join(images_dir, camp, name)
    try:
        with open(path, "rb") as imgf:
            b64 = base64.b64encode(imgf.read()).decode('utf-8')
        return {
            "success code": constants.SUCCESS,
            "content":      b64,
            "filename":     name
        }
    except FileNotFoundError:
        return {"success code": constants.FAIL, "error description": "file not found"}

def delete_resource(request):
    camp = request.get("campaign")
    name = request.get("name")
    # remove file
    try:
        os.remove(os.path.join(images_dir, camp, name))
    except FileNotFoundError:
        pass
    # remove metadata
    kept = []
    with open(constants.RESOURCES_FILE, "r") as meta:
        for line in meta:
            obj = json.loads(line)
            if not (obj["campaign"] == camp and obj["filename"] == name):
                kept.append(line)
    with open(constants.RESOURCES_FILE, "w") as meta:
        meta.writelines(kept)
    return {"success code": constants.SUCCESS}

def toggle_visibility(request):
    camp = request.get("campaign")
    name = request.get("name")
    out     = []
    new_vis = None
    # read all, flip the matched one
    with open(constants.RESOURCES_FILE, "r") as meta:
        for line in meta:
            obj = json.loads(line)
            if obj["campaign"] == camp and obj["filename"] == name:
                obj["visible"] = not obj["visible"]
                new_vis = obj["visible"]
            out.append(json.dumps(obj) + "\n")
    with open(constants.RESOURCES_FILE, "w") as meta:
        meta.writelines(out)
    if new_vis is None:
        return {"success code": constants.FAIL, "error description": "not found"}
    return {"success code": constants.SUCCESS, "visible": new_vis}



def generate_salt():
    salt_bytes = os.urandom(16)
    salt = base64.b64encode(salt_bytes).decode('utf-8')
    return salt


def create_hash(password, salt):
    combined = salt + password
    hash_bytes = hashlib.sha256(combined.encode('utf-8')).digest()
    hashed_password = base64.b64encode(hash_bytes).decode('utf-8')
    return hashed_password


