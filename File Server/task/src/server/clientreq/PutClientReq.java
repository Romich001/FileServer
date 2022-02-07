package server.clientreq;

import server.IDManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PutClientReq extends ClientReq{
    public PutClientReq(DataOutputStream outputStream, DataInputStream inputStream, IDManager idManager) {
        super(outputStream, inputStream, idManager);
    }

    @Override
    public void deal() {
        try {
            var name = inputStream.readUTF();
            var pathToFile = pathToDataDir + "\\" + name; //name
            File file = new File(pathToFile);
            if (file.createNewFile()) {
                writeIncomingData(file);
                sendResponse("200");
                var id = idManager.getID(name);
                sendResponse(id);
            } else {
                sendResponse("403");
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendResponse("403");
        }
    }

    private void writeIncomingData(File file) throws IOException {
        var length = inputStream.readInt();   //length
        byte[] message = new byte[length];
        inputStream.readFully(message, 0, length);  //data
        Files.write(file.toPath(), message);
    }

}
