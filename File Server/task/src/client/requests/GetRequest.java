package client.requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class GetRequest extends Request{

    protected String pathToData = "E:\\projects\\File Server\\File Server\\task\\src\\client\\data";

    public GetRequest(DataOutputStream outputStream, DataInputStream inputStream, Scanner scanner) {
        super(outputStream, inputStream, scanner);
    }

    @Override
    void sendMsgToServer() {
        try {
            outputStream.writeUTF("GET");
            sendNameOrID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void receiveServerMsg() throws IOException {
        var status = inputStream.readUTF();
        if (printStatus(status)) {
            var fileName = getFileName();
            var file = new File(pathToData.concat("\\" + fileName));
            var dataLength = inputStream.readInt();
            byte[] data = new byte[dataLength];
            inputStream.readFully(data);
            Files.write(file.toPath(), data);
            System.out.println("File saved on the hard drive!");
        }
    }

    private boolean printStatus(String status) {
        switch (status) {
            case "200":
                System.out.print("The file was downloaded! ");
                return true;
            case "404":
                System.out.println("The response says that this file is not found!");
                break;
            default:
                System.out.println("Error. GetRequest. receiveServerMsg()");
        }
        return false;
    }
     protected String getFileName() {
         System.out.print("Specify a name for it: ");
         return scanner.nextLine();
    }
    private void sendNameOrID() throws IOException {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
        var choice = scanner.nextLine();
        switch (choice) {
            case "1":    //get by name
                outputStream.writeInt(1);     //send to server "1" means that delete by name
                outputStream.writeUTF(super.getFileName());
                break;//send name
            case "2":   //get by id
                outputStream.writeInt(2);
                System.out.print("Enter id: ");
                var id = scanner.nextInt();
                scanner.nextLine();
                outputStream.writeInt(id);
                break;

        }
    }
}
