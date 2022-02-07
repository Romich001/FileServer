package server;

import java.io.*;

public class SerializeIDManager {

    private static final String FILE_NAME = "E:\\projects\\File Server\\File Server\\task\\src\\server\\idManager.data";

    public static void serialize(Object obj){
        try(FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static IDManager deserialize() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (IDManager) ois.readObject();
        } catch (FileNotFoundException e) {
            return new IDManager();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
