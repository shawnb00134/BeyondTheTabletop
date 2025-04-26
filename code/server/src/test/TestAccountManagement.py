'''
Created on Apr 9, 2025

@author: makaya1
'''

import importlib
import tempfile
import unittest
from pathlib import Path

from src.request_server import constants, request_handler


class TestAccountManagement(unittest.TestCase):
    @staticmethod
    def _valid_create_req(**overrides) -> dict:
        base = {
            "type": constants.CREATE_ACCOUNT,
            "accountId": "A‑1",
            "firstName": "Alice",
            "lastName": "Smith",
            "email": "alice@example.com",
            "username": "alice",
            "password": "SuperPass1",
            "confirmPassword": "SuperPass1",
        }
        base.update(overrides)
        return base

    def setUp(self) -> None:
        self._tmp_dir = tempfile.TemporaryDirectory()
        tmp_accounts = Path(self._tmp_dir.name) / "accounts.ndjson"
        tmp_accounts.touch()

        constants.ACCOUNTS_FILE = str(tmp_accounts)

        importlib.reload(request_handler)

        request_handler.accounts.clear()

        self.rh = request_handler

    def tearDown(self) -> None:
        self._tmp_dir.cleanup()

    def test_create_account_success(self):
        resp = self.rh.create_account(self._valid_create_req())

        self.assertEqual(resp["success code"], constants.SUCCESS)
        self.assertEqual(resp["accountInfo"]["username"], "alice")

        with open(constants.ACCOUNTS_FILE, encoding="utf‑8") as fh:
            self.assertEqual(len(fh.readlines()), 1)

    def test_create_account_duplicate_username(self):
        self.rh.create_account(self._valid_create_req())
        resp = self.rh.create_account(
            self._valid_create_req(accountId="A‑2", email="alice2@ex.com")
        )

        self.assertEqual(resp["success code"], constants.FAIL)
        self.assertEqual(resp["error description"], "Username already exists")

    def test_login_success_and_wrong_password(self):
        self.rh.create_account(self._valid_create_req())

        ok = self.rh.login(
            {"type": constants.LOGIN, "username": "alice", "password": "SuperPass1"}
        )
        bad = self.rh.login(
            {"type": constants.LOGIN, "username": "alice", "password": "wrong"}
        )

        self.assertEqual(ok["success code"], constants.SUCCESS)
        self.assertEqual(bad["success code"], constants.FAIL)
        self.assertEqual(bad["error description"], "password incorrect")

    def test_get_account_by_username_and_id(self):
        self.rh.create_account(self._valid_create_req())

        by_name = self.rh.get_account(
            {"type": constants.GET_ACCOUNT, "username": "alice"}
        )
        by_id = self.rh.get_account(
            {"type": constants.GET_ACCOUNT, "accountId": "A‑1"}
        )

        self.assertEqual(by_name["success code"], constants.SUCCESS)
        self.assertEqual(by_id["success code"], constants.SUCCESS)
        self.assertEqual(by_id["accountInfo"]["username"], "alice")

    def test_update_account_change_email_and_password(self):
        self.rh.create_account(self._valid_create_req())

        upd = self.rh.update_account(
            {
                "type": constants.UPDATE_ACCOUNT,
                "username": "alice",
                "new_email": "newalice@ex.com",
                "new_password": "BrandNew9",
            }
        )
        self.assertEqual(upd["success code"], constants.SUCCESS)
        self.assertEqual(upd["accountInfo"]["email"], "newalice@ex.com")

        ok = self.rh.login(
            {"type": constants.LOGIN, "username": "alice", "password": "BrandNew9"}
        )
        bad = self.rh.login(
            {"type": constants.LOGIN, "username": "alice", "password": "SuperPass1"}
        )
        self.assertEqual(ok["success code"], constants.SUCCESS)
        self.assertEqual(bad["success code"], constants.FAIL)

    def test_delete_account(self):
        self.rh.create_account(self._valid_create_req())

        del_resp = self.rh.delete_account(
            {"type": constants.DELETE_ACCOUNT, "username": "alice"}
        )
        self.assertEqual(del_resp["success code"], constants.SUCCESS)

        fail_login = self.rh.login(
            {"type": constants.LOGIN, "username": "alice", "password": "SuperPass1"}
        )
        self.assertEqual(fail_login["success code"], constants.FAIL)

    def test_list_users_and_search(self):
        self.rh.create_account(self._valid_create_req())
        self.rh.create_account(
            self._valid_create_req(
                accountId="A‑2",
                email="bob@ex.com",
                username="bob",
                firstName="Bob",
            )
        )

        all_users = self.rh.list_users({"type": constants.LIST_USERS})
        self.assertEqual(len(all_users["users"]), 2)

        only_bob = self.rh.list_users(
            {"type": constants.LIST_USERS, "search": "bo"}
        )
        self.assertEqual(len(only_bob["users"]), 1)
        self.assertEqual(only_bob["users"][0]["username"], "bob")

    def test_check_username_taken_vs_free(self):
        self.rh.create_account(self._valid_create_req())

        taken = self.rh.check_username(
            {"type": constants.CHECK_USERNAME, "username": "alice"}
        )
        free = self.rh.check_username(
            {"type": constants.CHECK_USERNAME, "username": "charlie"}
        )

        self.assertTrue(taken["isTaken"])
        self.assertFalse(free["isTaken"])


if __name__ == "__main__":
    unittest.main()