'''
Created on Apr 8, 2025

@author: kg00281
'''
import unittest, zmq, json, time
from threading import Thread
from src.request_server import server, constants

class TestRunServer(unittest.TestCase):
    
    @classmethod
    def setUpClass(cls):
        cls.serverThread = Thread(target=lambda: server.runServer(), daemon=True)
        cls.serverThread.start()
        time.sleep(3)
        
        cls.context = zmq.Context()
        cls._socket = cls.context.socket(zmq.REQ)
        serverLocation = "tcp://127.0.0.1:5555"
        cls._socket.connect(serverLocation)

    def test_load_request(self):
        request = {"type": "start"}
        jsonRequest = json.dumps(request)
        
        self._socket.send_string(jsonRequest)
        
        jsonResponse = self._socket.recv_string()
        
        assert jsonResponse == "client and server communicating"
    
    def test_regular_request(self):
        request = {"type": "get campaign", "name": "miley"}
        jsonRequest = json.dumps(request)
        
        self._socket.send_string(jsonRequest)
        
        jsonResponse = self._socket.recv_string()
        response = json.loads(jsonResponse)
        self.assertEqual(response["success code"], constants.SUCCESS)
    
    def test_z_exit_server(self):
        request = {"type": "exit"}
        jsonRequest = json.dumps(request)
        self._socket.send_string(jsonRequest)
        
        jsonResponse = self._socket.recv_string()
        
        assert jsonResponse == "server closing"
    
    @classmethod
    def tearDownClass(cls):
        cls._socket.close()
        cls.context.term()
        time.sleep(1) 
    
if __name__ == "__main__":
    unittest.main()