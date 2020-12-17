package Entity;


import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    public boolean file=false;
    public int sourceId= 0;
    public int destinationId;
    public String message;
    public int command=0;
    public ArrayList<Object> objects=new ArrayList<>();
    public Message(int destinationId, String message) {
        this.destinationId = destinationId;
        this.message = message;
    }

    public Message(int destinationId, String message, int command) {
        this.destinationId = destinationId;
        this.message = message;
        this.command = command;
    }
    public Message(int sourceId, int destinationId, String message, int command) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.message = message;
        this.command = command;
    }
}
