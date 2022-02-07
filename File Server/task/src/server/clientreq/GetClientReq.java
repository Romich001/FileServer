package server.clientreq;

import server.IDManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GetClientReq extends ClientReq{
    public GetClientReq(DataOutputStream outputStream, DataInputStream inputStream, IDManager idManager) {
        super(outputStream, inputStream, idManager);
    }

    @Override
    public void deal() {
        try {
            var fileName = getFileName();
            var file = new File(pathToDataDir + "\\" + fileName);
            if (file.exists()) {
                sendResponse("200");
                byte[] data = Files.readAllBytes(file.toPath());
                sendResponse(data.length);
                sendResponse(data);


            } else {
                sendResponse("404");
            }
        } catch (IOException e) {
            sendResponse("404");
            e.printStackTrace();
        }

    }

}
