package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
//this class manages with ids and file names
//create ids, storage pairs of ids(key) and value in the map, return name by id, delete an entry
public class IDManager implements Serializable {

     private int curID = 1;
     private Map<Integer,String> idMap = new HashMap<>();
//creates id and set it into the map. <id, file name>
    //gets name, sets it as a value of map and counts the id
    public int getID(String key) {
        int id;
        id = curID++;
        idMap.put(id, key);

        return id;
    }
//gets id and return file name
    public String getNameById(int readInt) {
        return idMap.get(readInt);
    }
//gets file name, find the entry of map with such value and delete that entry


    public void deleteMapNote(String name) {
        for(var pair : idMap.entrySet()) {
            if(pair.getValue().equals(name)) {
                idMap.remove(pair.getKey());
                break;
            }
        }
    }
}
