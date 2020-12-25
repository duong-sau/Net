package Entity;
/**
 * gói này đại diện cho 1 tin nhắn
 * tất các các class khác trong Entity đều được đính kèm vào gói này
 * server và client giao tiếp với nhau qua tin nhắn này
 * xem hàm khởi tạo
 */

import java.io.Serializable;
import java.util.ArrayList;


public class Message implements Serializable {
    public boolean file=false;
    public short call = 0;

    public int sourceId= 0;
    public int destinationId;
    public String message;
    public int command=0;
    public ArrayList<Object> objects=new ArrayList<>(); // chỗ này là gói tin đính kèm
    public Message(int destinationId, String message) {
        this.destinationId = destinationId;
        this.message = message;
    }

    public Message(int destinationId, String message, int command) {
        this.destinationId = destinationId;
        this.message = message;
        this.command = command;
    }

    /**
     * hàm này khởi tạo một tin nhắn
     * @param sourceId id người gửi
     * @param destinationId id phòng nhắn tin
     * @param message nội dung tin nhắn ( nội dung tin nhắn sẽ được lấy ra lưu vào csdl và chuyển đi nếu là tin nhắn chữ, còn các loại tin nhắn khác sẽ không quan tâm tới nội dung này
     * @param command yêu cầu từ client hoặc từ server dùng để phân loại tin nhắn , biết được chức năn của tin nhắn là gửi file, tạo phòng hay gì gì đó
     */
    public Message(int sourceId, int destinationId, String message, int command) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.message = message;
        this.command = command;
    }
}
