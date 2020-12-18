package Controller;
/**
 * lớp này nhiệm vụ chuyển tiếp tin nhắn từ máy này qua máy khác
 */

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
        sqlConnect.saveMessage(message);
        ArrayList<Integer> userIds=sqlConnect.getUser(new Room(message.destinationId));
        for (Integer userId:userIds) {
            for (ConnectThread connectThread : connectThreads) {
                if (connectThread.handshake.id == userId) {
                    connectThread.writeMessage(message);
                }
            }
        }
    }

    /**
     * chuyển cuộc gọi
     * @param message tin nhắn bên trong có đính kèm gói âm thanh
     */
    public void callRouting(Message message){
        ArrayList<Integer> userIds=sqlConnect.getUser(new Room(message.destinationId));
        for (Integer userId:userIds) {
            if (userId != message.destinationId) {
                for (ConnectThread connectThread : connectThreads) {
                    if (connectThread.handshake.id == userId) {
                        connectThread.writeMessage(message);
                    }
                }
            }
        }
    }

}
