package client;

import client.requests.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    final private String ADDRESS = "127.0.0.1";
    final private int PORT = 23456;

    public static void startClient() {

        Client client = new Client();
        client.getConnection();

    }



    private String getAction(Scanner scanner) {
        String action;
        System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");

        action = scanner.nextLine();

        return action;
    }

    private void getConnection() {

        try (
                Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in)
        ) {

            String action = getAction(scanner);
            Request request = createRequestClass(action, scanner ,input, output);    //create chosen request
            request.composeRequest();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request createRequestClass(String action,
                                    Scanner scanner,
                                    DataInputStream inputStream,
                                    DataOutputStream outputStream) {
        Request request = null;
        switch (action) {
            case "1":
                request = new GetRequest(outputStream, inputStream, scanner);
                
                break;
            case "2":
                request = new PutRequest(outputStream, inputStream, scanner);
                
                break;
            case "3":
                request = new DeleteRequest(outputStream, inputStream, scanner);
                break;
            case "exit":
                request = new ExitRequest(outputStream, inputStream, scanner);

                break;
            default:
                System.out.println("Error. Client, createRequestClass()");
        }
        assert request != null;

        return request;
    }
}
