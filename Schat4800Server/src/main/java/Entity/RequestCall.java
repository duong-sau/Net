package Entity;
/**
 * gói này đại diện cho một lời mời cuộc gọi
 * phần này cũng chưa viết
 */

import java.io.Serializable;

public class RequestCall implements Serializable {
    String callId;
    String from;

    public RequestCall(String callId, String from) {
        this.callId = callId;
        this.from = from;
    }
}
