package Connect;
/**
 * lớp này chờ để tạo kết nối tới client
 * phục vụ đa luồng
 * cổng 5000
 * ip 127.0.0.1
 */

import AudioServer.Connect.AudioClient;
import AudioServer.Connect.AudioThread;
import Controller.RouteController;
import SQL.SQLConnect;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectClient {
    public static int port = 5009;
    public static int portAudio = 5011;
    SQLConnect sqlConnect=new SQLConnect();
    ServerSocket socket;
    Socket clientSocket;
    RouteController routeController;
    AudioClient audioClient;
    public ConnectClient() {
        sqlConnect=new SQLConnect();
        routeController=new RouteController(sqlConnect);
        audioClient=new AudioClient(routeController, sqlConnect);
        try {
            socket = new ServerSocket(port,100);
            while (true) {
                System.out.println("bắt đầu");
                clientSocket = socket.accept();
                ConnectThread connectThread=new ConnectThread(clientSocket,routeController,sqlConnect);
                connectThread.start();
                System.out.println("Nhận được kết nối từ client "+ clientSocket);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
