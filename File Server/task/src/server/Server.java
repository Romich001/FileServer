package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    final static private String ADDRESS = "127.0.0.1";
    final static private int PORT = 23456;
    volatile static ExecutorService executor = Executors.newCachedThreadPool();

    static public void startServer() {
        System.out.println("Server started!");

        IDManager idManager = SerializeIDManager.deserialize();
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {

            while (!serverSocket.isClosed()) {

                executor.submit(new Connection(serverSocket.accept(), idManager, serverSocket));


            }

        } catch (IOException e) {
           // e.printStackTrace();
        }
        executor.shutdown();
    }

}
