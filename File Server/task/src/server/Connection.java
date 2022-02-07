package server;

import server.clientreq.ClientReq;
import server.clientreq.DeleteClientReq;
import server.clientreq.GetClientReq;
import server.clientreq.PutClientReq;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection extends Thread{

    Socket socket;
    IDManager idManager;
    ServerSocket serverSocket;

    public Connection(Socket socket, IDManager idManager, ServerSocket serverSocket) {
        this.socket = socket;
        this.idManager = idManager;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try(DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream())) {

            var action = input.readUTF();
            if (action.equals("EXIT")) {
                SerializeIDManager.serialize(idManager);
                serverSocket.close();

            } else {
                ClientReq req = createClientReq(action, output, input, idManager);
                assert req != null;
                req.deal();
            }
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private ClientReq createClientReq(String action, DataOutputStream output, DataInputStream input, IDManager idManager) {
        switch (action) {
            case "PUT":
                return new PutClientReq(output, input, idManager);
            case "DELETE":
                return new DeleteClientReq(output, input, idManager);
            case "GET":
                return new GetClientReq(output, input, idManager);
            default:
                return null;
        }
    }
}