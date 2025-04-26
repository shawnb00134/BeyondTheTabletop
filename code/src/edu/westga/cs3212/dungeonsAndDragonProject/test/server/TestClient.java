package edu.westga.cs3212.dungeonsAndDragonProject.test.server;

import edu.westga.cs3212.dungeonsAndDragonProject.server.Client;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class TestClient {

    private final int port = 5555;
    private final String expectedReply = "{\"dummy\":\"ok\"}";
    private Thread serverThread;
    private volatile boolean running;

    @Before
    public void setUp() throws InterruptedException {
        running = true;
        serverThread = new Thread(() -> {
            try (ZContext context = new ZContext()) {
                ZMQ.Socket repSocket = context.createSocket(ZMQ.REP);
                String endpoint = "tcp://127.0.0.1:" + port;
                repSocket.bind(endpoint);
                repSocket.setReceiveTimeOut(100);

                while (running) {
                    byte[] requestBytes = repSocket.recv(0);
                    if (requestBytes != null) {
                        repSocket.send(expectedReply.getBytes(ZMQ.CHARSET), 0);
                    }
                }
                repSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
        Thread.sleep(500);
    }

    @Test
    public void testSendRequestA() {
        String testMessage = "Hello";
        String actualReply = Client.sendRequest(testMessage);
        Assert.assertEquals(expectedReply, actualReply);
    }
    
}