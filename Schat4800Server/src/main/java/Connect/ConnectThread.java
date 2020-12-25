package Connect;
/**
 * gói này nhận thông điệp từ client
 */

import AudioServer.Connect.AudioThread;
import Controller.RouteController;
import Entity.*;
import SQL.SQLConnect;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class ConnectThread extends Thread{
    long last = 0;
    Random random= new Random();
    int manifestLength = 1048576;
    public AudioThread audioThread;
    boolean run=false;
    SQLConnect sqlConnect;
    public Handshake handshake;
    public Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    public RouteController routeController;
    FileMA fileConnect;
    public ConnectThread(Socket socket, RouteController routeController,SQLConnect sqlConnect) {
        this.socket = socket;
        this.sqlConnect=sqlConnect;
        this.routeController = routeController;
        routeController.add(this);

    }

    @Override
    /**
     * hàm này liên tục chờ nhận tin nhắn từ client rồi đem đi phân loại
     */
    public void run() {
        try {
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            handshake();
            reconnect();
            run=true;
            while (!socket.isClosed()&&run){
                System.out.println(socket.isClosed());
                Message message=readMessage();
                classify(message);
            }
            destructor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * hàm này tạo gọi sau khi nhận được lời chào từ client
     * nó gửi danh sách các phòng tới client
     */
    public void handshake(){
        try{
            handshake=(Handshake)objectInputStream.readObject();
            ArrayList<Integer> rooms=sqlConnect.getRoom(handshake.id);
            RoomHandshake roomHandshake=new RoomHandshake();
            Message message=new Message(handshake.id,"contact from server",4);
            for (Integer roomId:rooms){
                Room room=new Room(roomId);
                ArrayList<Integer> userIds=sqlConnect.getUser(room);
                System.out.println(" tên phòng "+ roomId);
                room.userIds=userIds;
                roomHandshake.rooms.add(room);
            }
            message.objects.add(roomHandshake);
            writeMessage(message);
            System.out.println("kết nối tới máy số"+handshake.id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * hàm này đọc tin nhắn
     * gần như khong có chức năng gì chỉ là viết cho cái lúc đọc tin nhắn gọn hơn thôi
     * @return
     */
    public Message readMessage(){
        try {
            Message message=(Message) objectInputStream.readObject();
            if(handshake!=null){
                message.sourceId=handshake.id;
            }
            System.out.println("cẩn gửi tin nhắn đến "+message.destinationId+" nội dung"+message.message);
            /*
            int a = random.nextInt(10);
            int b = (int) (System.currentTimeMillis()-last);
            sleep(10000*a/b);

             */
            return message;

        }catch (Exception e){
            run=false;
        }
        return null;
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
                e.printStackTrace();
            }
        }

    /**
     * hàm này xử lý kho có một máy kết nối lại
     */
    public void reconnect(){
        ArrayList<Integer> roomIds=sqlConnect.getRoom(handshake.id);
        System.out.println("reconnect");
        for (Integer roomId:roomIds) {
            getMessage(roomId);
        }
    }

    /**
     * hàm này lấy các tin nhắn trong phòng để gửi cho các máy kết nối lại thu được tin nhắn đã gửi trước đó
     * @param roomId
     */
    public void getMessage(int roomId){
        ArrayList<Message> messages;
        messages = sqlConnect.getMessage(roomId);
        try {
            for (Message message : messages) {
                writeMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * hàm này dùng để phân loại tin nhắn nhận được từ client
     *
     * @param message
     */
    public void classify(Message message){
        if(message==null){}
        else if(message.command==0) {
            message.sourceId=handshake.id;
            routeController.routing(message);
            Message message1 = new Message(message.sourceId,"oke",1 );
            writeMessage(message1);
        }
        else if(message.command==209){
           readFile(message);
        }
        else if(message.command==210){
            readContent(message);
        }
        else if(message.command==2){
            manifest(message);
        }
        else if(message.command==3){
            createRoom(message);
        }
        else if(message.command==110){
            System.out.println("call");
            requestCall(message);
        }
        else if(message.command==10){
            createRoom(message);
        }
        else if(message.command==1220){
            System.out.println("1220");
            Message message1 = new Message(message.destinationId, "in", 1210);
            routeController.routing(message1);
        }
        else if(message.command==1202){
            System.out.println("1202");
            Message message1 = new Message(message.destinationId, "in", 1201);
            routeController.routing(message1);
        }
    }

    /**
     * hàm đọc file từ client gửi cho lưu vào bộ nhớ của server, lúc nào có client tải sẽ đem ra gửi đi
     * @param message
     */
    public void readFile(Message message){
        try {
            Manifest manifest = (Manifest) message.objects.get(0);
            fileConnect = new FileMA(manifest.name, manifest.length);
            Message message1=new Message(handshake.id,message.destinationId,manifest.name,0);
            message1.file=true;
            routeController.routing(message1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void readContent(Message message){
        FileTransfer fileTransfer=(FileTransfer)message.objects.get(0);
        boolean finish = fileConnect.add(fileTransfer);
        if (finish){
            fileConnect = null;
        }
    }


    /**
     * hàm này tạo phòng chat
     * tôi chưa làm xong
     * @param message
     */
    public void createRoom(Message message){
        //xong
        try {
            Room room=(Room) message.objects.get(0);
            System.out.println("nhận đươc người dùng"+room.userIds.get(0));
            int id = sqlConnect.createRoom(room.userIds,message.sourceId);
            Message message1=new Message(message.sourceId, "create oke", 11);
            if(id!=-1) {
                room.id = id;
                message1.objects.add(room);
            }
            else {
                message1.command=12;
            }
            writeMessage(message1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * hàm này tôi dự tính để ngắt kết nối nhưng hình như không có ý nghĩa lắm
     */
    public void destructor(){
        routeController.connectThreads.remove(this);
    }

    /**
     * hàm này gửi nội yêu cầu cuộc gọi tới client
     * bên client chưa xử lý tin nhắn có command=6 nên không có hiện tượng gì đâu
     * ai đó viết hộ tôi phần xử lý bên client
     * @param message
     */
    public void requestCall(Message message){
        message.sourceId = handshake.id;
        message.message="Call";
        message.call=1;
        message.file=false;
        routeController.routing(message);
       routeController.requestRouting(message);

    }
    public void responseCall(Message message){

    }
    public void manifest(Message message){

        try {
            File file=new File(message.message);
            FileInputStream fileInputStream=new FileInputStream(file);
            Manifest manifest = new Manifest(1+fileInputStream.available()/manifestLength,file.getName());
            Message message1 = new Message(handshake.id, "manifest", 201);
            message1.objects.add(manifest);
            writeMessage(message1);
            writeFile(manifest, message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * hàm này gửi file đến client khi có yêu cầu tải file
     * @param message
     */
    public void writeFile(Manifest manifest, Message message){
        //xong
        try {
            File file=new File(manifest.name);
            FileInputStream fileInputStream=new FileInputStream(file);
            for (int i = 0; i < manifest.length; i++) {
                FileTransfer fileTransfer=new FileTransfer();
                fileTransfer.id = i+1;
                fileTransfer.name=file.getName();
                if(fileInputStream.available()<manifestLength) {
                    byte[] bytes = new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
                    fileTransfer.content = bytes;
                }
                else {
                    byte[] bytes = new byte[manifestLength];
                    fileInputStream.read(bytes);
                    fileTransfer.content = bytes;
                }
                Message message1=new Message(message.sourceId,"file from server",2);
                message1.objects.add(fileTransfer);
                writeMessage(message1);
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * hàm này chuyển tiếp tin nhắn có đính kèm nội dung âm thanh
     * @param message
     */

}
