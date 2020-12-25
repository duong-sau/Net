package AudioServer.Connect;

import Connect.ConnectClient;
import Connect.ConnectThread;
import Controller.RouteController;
import SQL.SQLConnect;

import java.net.ServerSocket;
import java.net.Socket;

public class AudioClient extends Thread{
    SQLConnect sqlConnect;
    ServerSocket socket;
    Socket clientSocket;
    RouteController routeController;
    AudioController audioController;
    public AudioClient(RouteController routeController, SQLConnect sqlConnect) {
        this.sqlConnect = sqlConnect;
        audioController = new AudioController(this.sqlConnect);
        this.routeController = routeController;
        try {
            socket = new ServerSocket(ConnectClient.portAudio);
            start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("bắt đầu audio");
                clientSocket = socket.accept();
                AudioThread audioThread = new AudioThread(clientSocket, routeController, audioController);
                audioThread.start();
                System.out.println("Nhận được kết nối audio " + clientSocket);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
