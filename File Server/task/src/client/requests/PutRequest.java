package client.requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class PutRequest extends Request{

    public PutRequest(DataOutputStream outputStream, DataInputStream inputStream, Scanner scanner) {
        super(outputStream, inputStream, scanner);
    }

    @Override
    void sendMsgToServer() {
        String clientFileName = getFileName();    //file's name on the client's hard disk
        String nameToSave = getNameToSave();      //save with this name in the server; if it is "" save as clientFileName

            try {
                outputStream.writeUTF("PUT");  //PUT
                outputStream.writeUTF(nameToSave.isEmpty()? clientFileName : nameToSave);   //NAME
                byte[] data = Files.readAllBytes(Paths.get(dataFolder + "\\" + clientFileName)); //data to write
                outputStream.writeInt(data.length); //length
                outputStream.write(data);  //DATA
            } catch (IOException e) {
                e.printStackTrace();
            }

    }


    protected String getNameToSave() {
        System.out.print("Enter name of the file to be saved on server: ");
        return scanner.nextLine();
    }

//Response says that file is saved! ID = **, where ** is an id of the file!
    @Override
    void receiveServerMsg() throws IOException {
        var msg = inputStream.readUTF();
        switch (msg.substring(0, 3)) {
            case "200":
                System.out.print("Response says that file is saved! ");
                var id = inputStream.readInt();
                System.out.printf("ID = %d, where %d is an id of the file!%n", id, id);
                break;
            case "403":
                System.out.println("The response says that creating the file was forbidden!");
                break;
            default:
                System.out.println("Error. GetRequest. receiveServerMsg()");
        }
    }

}
