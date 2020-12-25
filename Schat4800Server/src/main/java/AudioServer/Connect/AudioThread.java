package AudioServer.Connect;

import Controller.RouteController;
import Entity.AudioTransfer;
import Entity.Handshake;
import Entity.Message;



import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AudioThread extends Thread{
    boolean run= false;
    public Handshake handshake = new Handshake(-1);
    public Socket socket;
    public boolean isSet= false;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    RouteController routeController;
    AudioController audioController;
    public AudioThread(Socket socket, RouteController routeController, AudioController audioController) {
        this.socket = socket;
        this.routeController = routeController;
        this.audioController = audioController;
        audioController.add(this);
    }

    @Override
    public void run() {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            handshake();
            while (!isSet) {
                if(this.handshake.id!= -1) {
                    routeController.set(this);
                    sleep(1000);
                }
            }
            System.out.println("kết nối thành công---------------------------");
            run = true;
            while (run){
                Message message = (Message) objectInputStream.readObject();
                routingCall(message);

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void handshake(){
        try{
            handshake=(Handshake)objectInputStream.readObject();
            System.out.println("kết nối tới máy số"+handshake.id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void routingCall(Message message){
        try {
            //if(message.objects.size()>0) {
            //    AudioTransfer audioTransfer = (AudioTransfer) message.objects.get(0);
             //   if (audioTransfer.time < System.currentTimeMillis() + 3000) {

              //  } else {
                    message.sourceId = handshake.id;
                    audioController.callRouting(message);
                    System.out.println("routing");
              //  }
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * hàm này gửi 1 tin nhắn tời client
     * cũng như hàm đọc tin nhắn chỉ là viết cho gọn thôi
     * @param message
     */
    public void writeMessage(Message message){

        try {
            objectOutputStream.writeObject(message);
            System.out.println("đã gửi nội dung  "+message.message+ " đếm máy đích  "+ message.destinationId);
        }
       catch (Exception e){
            run = false;
            e.printStackTrace();
        }
    }

}
