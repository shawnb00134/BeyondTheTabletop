package edu.westga.cs3212.dungeonsAndDragonProject.server;

import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * Client that communicates with the server
 * 
 * @author kelis, El
 * @version Spring 2025
 */
public class Client {
	private static final String SERVER_ADDRESS = "tcp://127.0.0.1:5555";

	private static ZMQ.Socket socket;
	private static ZContext context;
	private static SendRequestDelegate sendRequestDelegate = null;			
	
	static {
		context = new ZContext();
		socket = context.createSocket(ZMQ.REQ);
		socket.connect(SERVER_ADDRESS);
		socket.setReceiveTimeOut(10000);	
	}

	/**
	 * Interface SendRequestDelegate
	 */
	public interface SendRequestDelegate {

		/**
		 * Sends the request.
		 * 
		 * @param request the request to send
		 * @return the request
		 */
		String send(String request);
	}
	
	/**
	 * Sends request to server
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @param message to send to server
	 * @return String as a response
	 */
	public static synchronized String sendRequest(String message) {
		if (sendRequestDelegate != null) {
			return sendRequestDelegate.send(message);
		}
		try {
			socket.send(message.getBytes(), 0);
			byte[] reply = socket.recv(0);
			return new String(reply);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Error in communication: " + ex.getMessage();
		}
	}
}
