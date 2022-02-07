package server.clientreq;

import server.IDManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class DeleteClientReq extends ClientReq{

    public DeleteClientReq(DataOutputStream outputStream, DataInputStream inputStream, IDManager idManager) {
        super(outputStream, inputStream, idManager);
    }

    @Override
    public void deal() {
        try {
            var fileName = getFileName();
            var filePath = pathToDataDir + "\\" + fileName;
            var file = new File(filePath);
            if (file.delete()) {
                idManager.deleteMapNote(fileName);
                sendResponse("200");
            } else {
                sendResponse("404");
            }
        } catch (IOException e) {
            sendResponse("404");
            e.printStackTrace();
        }

    }




}
