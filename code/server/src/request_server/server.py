'''
Created on Mar 25, 2025
@author: kg00281
'''
import zmq
import json
from src.request_server import request_handler

def runServer():
    context = zmq.Context()
    socket = context.socket(zmq.REP)
    socket.bind("tcp://127.0.0.1:5555")
    
    while True:
        '''print("waiting for message...")'''
        json_message = socket.recv_string()
        request = json.loads(json_message)
        '''print("Received request:", request.get("type"))'''
        
        if request["type"] == "exit":
            socket.send_string("server closing")
            break
        
        if request["type"] == "start":
            socket.send_string("client and server communicating")
            continue
        
        try:
            response = request_handler.handle_requests(request)
        except Exception as e:
            response = {
                "success code": "fail",
                "error description": f"server error: {e}"
            }
        
        json_response = json.dumps(response)
        socket.send_string(json_response)
        '''print("Response sent:", json_response)'''

    socket.close()

if __name__ == "__main__":
    runServer()
