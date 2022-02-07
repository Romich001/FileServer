package server.clientreq;

import server.IDManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ClientReq {

    DataOutputStream outputStream;
    DataInputStream inputStream;
    IDManager idManager;
    String pathToDataDir = "E:\\projects\\File Server\\File Server\\task\\src\\server\\data";

    protected ClientReq(DataOutputStream outputStream, DataInputStream inputStream, IDManager idManager) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.idManager = idManager;
    }

    public abstract void deal();

    void sendResponse(String result) {
        try {
            outputStream.writeUTF(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendResponse(int x) {
        try {
            outputStream.writeInt(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void sendResponse(byte[] x) {
        try {
            outputStream.write(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getFileName() throws IOException {
        switch (inputStream.readInt()) {
            case 1:
                return inputStream.readUTF();
            case 2:
                return idManager.getNameById(inputStream.readInt());
            default:
                return null;
        }

    }
}
