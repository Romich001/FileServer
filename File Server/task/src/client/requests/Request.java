package client.requests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public abstract class Request {

    protected DataOutputStream outputStream;
    protected DataInputStream inputStream;
    protected Scanner scanner;
    protected String dataFolder = "E:\\projects\\File Server\\File Server\\task\\src\\client\\data";  //client's data folder


    protected Request(DataOutputStream outputStream, DataInputStream inputStream, Scanner scanner) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.scanner = scanner;
    }

     public void composeRequest() {
        try {
            sendMsgToServer();
            receiveServerMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    abstract void sendMsgToServer();

    //abstract void createUserMsg();

    abstract void receiveServerMsg() throws IOException;

    protected String getFileName(){
        System.out.print("Enter file name: ");

        return scanner.nextLine();
    }

}
