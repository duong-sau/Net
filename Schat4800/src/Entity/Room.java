package Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    public int id=0;
    public ArrayList<Integer> userIds;

    public Room(ArrayList<Integer> userIds) {
        this.userIds = userIds;
    }

    public Room(int id, ArrayList<Integer> userIds) {
        this.id = id;
        this.userIds = userIds;
    }

    public Room(int id) {
        this.id = id;
    }
}
