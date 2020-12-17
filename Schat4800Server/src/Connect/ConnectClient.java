package Connect;

import Controller.RouteController;
import SQL.SQLConnect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectClient {
    SQLConnect sqlConnect=new SQLConnect();
    ServerSocket socket;
    Socket clientSocket;
    RouteController routeController;
    public ConnectClient() {
        sqlConnect=new SQLConnect();
        routeController=new RouteController(sqlConnect);
        try {
            socket = new ServerSocket(5000);
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
