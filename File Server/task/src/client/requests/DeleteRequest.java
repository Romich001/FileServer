package client.requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class DeleteRequest extends Request{

    public DeleteRequest(DataOutputStream outputStream, DataInputStream inputStream, Scanner scanner) {
        super(outputStream, inputStream, scanner);
    }

    @Override
    void sendMsgToServer() {
        try {
            outputStream.writeUTF("DELETE");
            sendNameOrID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void receiveServerMsg() throws IOException {
        var msg = inputStream.readUTF();
        switch (msg) {
            case "200":
                System.out.println("The response says that the file was successfully deleted!");
                break;
            case "404":
                System.out.println("The response says that the file was not found!");
                break;
            default:
                System.out.println("Error. GetRequest. receiveServerMsg()");
        }
    }
    private void sendNameOrID() throws IOException {
        System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id): ");
        var choice = scanner.nextLine();
        switch (choice) {
            case "1":    //delete by name
                outputStream.writeInt(1);     //send to server "1" means that delete by name
                outputStream.writeUTF(getFileName());//send name
                break;

            case "2":   //delete by id
                outputStream.writeInt(2);
                System.out.print("Enter id: ");
                var id = scanner.nextInt();
                scanner.nextLine();
                outputStream.writeInt(id);
                break;

        }
    }
}
