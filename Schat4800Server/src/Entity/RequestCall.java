package Entity;

import java.io.Serializable;

public class RequestCall implements Serializable {
    String callId;
    String from;

    public RequestCall(String callId, String from) {
        this.callId = callId;
        this.from = from;
    }
}
