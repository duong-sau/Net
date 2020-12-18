package Entity;
/**
 * gói này dùng để server gửi các phòng đã tạo trước đó đến client
 *
 */

import java.io.Serializable;
import java.util.ArrayList;

public class RoomHandshake implements Serializable {
    public ArrayList<Room> rooms;

    public RoomHandshake(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public RoomHandshake() {
        rooms=new ArrayList<>();
    }
}
