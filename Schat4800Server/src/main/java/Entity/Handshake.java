package Entity;
/**
 * dùng để gửi lời chào tới server
 * server dùng gói này để xác định id máy
 * do tôi chưa làm được đăng nhập nên id máy dó client gửi qua gói này
 */

import java.io.Serializable;

public class Handshake implements Serializable {
    public int id;
    public Handshake(int id){
        this.id=id;
    }
}
