package AudioServer.Connect;

import Entity.Message;
import Entity.Room;
import SQL.SQLConnect;

import java.util.ArrayList;

public class AudioController {
    SQLConnect sqlConnect;
    public ArrayList<AudioThread> audioThreads=new ArrayList<>();
    public AudioController(SQLConnect sqlConnect) {
        this.sqlConnect=sqlConnect;
    }
    public void add(AudioThread audioThread){
        audioThreads.add(audioThread);
    }

    /**
     * chuyển cuộc gọi
     * @param message tin nhắn bên trong có đính kèm gói âm thanh
     */
    public void callRouting(Message message){
        ArrayList<Integer> userIds=sqlConnect.getUser(new Room(message.destinationId));
        for (Integer userId:userIds) {
            if (userId != message.sourceId) {
                for (AudioThread audioThread : audioThreads) {
                    if (audioThread.handshake.id == userId) {
                        audioThread.writeMessage(message);
                    }
                }
            }
        }
    }
}
