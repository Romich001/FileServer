package client.requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class ExitRequest extends Request {

    public ExitRequest(DataOutputStream outputStream, DataInputStream inputStream, Scanner scanner) {
        super(outputStream, inputStream, scanner);
    }

    @Override
    public void composeRequest() {
        sendMsgToServer();
    }

    @Override
    void sendMsgToServer() {
        try {
            outputStream.writeUTF("EXIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    void receiveServerMsg() throws IOException {

    }
}
