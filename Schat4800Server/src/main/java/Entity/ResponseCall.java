package Entity;
/**
 * gói này đại diện cho 1 lời phản hồi cuộc gọi
 * phần này chưa viết
 */

import java.io.Serializable;

public class ResponseCall implements Serializable {
    String callId;
    String from;

    public ResponseCall(String callId, String from) {
        this.callId = callId;
        this.from = from;
    }
}
