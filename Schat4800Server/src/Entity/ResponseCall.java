package Entity;

import java.io.Serializable;

public class ResponseCall implements Serializable {
    String callId;
    String from;

    public ResponseCall(String callId, String from) {
        this.callId = callId;
        this.from = from;
    }
}
