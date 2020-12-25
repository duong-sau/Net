package Controller;
/**
 * lớp này nhiệm vụ chuyển tiếp tin nhắn từ máy này qua máy khác
 */

import AudioServer.Connect.AudioThread;
import Connect.ConnectThread;
import Entity.Message;
import Entity.Room;
import SQL.SQLConnect;

import java.util.ArrayList;

public class RouteController {
    SQLConnect sqlConnect;
    public ArrayList<ConnectThread> connectThreads=new ArrayList<>();
    public RouteController(SQLConnect sqlConnect) {
        this.sqlConnect=sqlConnect;
    }
    public void add(ConnectThread connectThread){
        connectThreads.add(connectThread);
    }

    /**
     * chuyển tin nhắn
     * @param message tin nhắn
     */
    public void routing(Message message){
        if(message.command==0) {
            sqlConnect.saveMessage(message);
        }
        ArrayList<Integer> userIds=sqlConnect.getUser(new Room(message.destinationId));
        for (Integer userId:userIds) {
            for (ConnectThread connectThread : connectThreads) {
                if (connectThread.handshake.id == userId) {
                    connectThread.writeMessage(message);
                }
            }
        }
    }
    public void set(AudioThread audioThread){
        for (ConnectThread connectThread : connectThreads) {
            if (connectThread.handshake.id == audioThread.handshake.id) {
                connectThread.audioThread = audioThread;
                audioThread.isSet = true;
            }
        }
    }
    public void requestRouting(Message message){

        ArrayList<Integer> userIds=sqlConnect.getUser(new Room(message.destinationId));
        Message request = new Message(0,"call", 6);
        for (Integer userId:userIds) {
            if (userId != message.sourceId) {
            for (ConnectThread connectThread : connectThreads) {
                if (connectThread.handshake.id == userId) {
                    connectThread.writeMessage(request);
                }
            }
            }
        }
    }

}
