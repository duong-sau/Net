package Connect;

import Controller.RouteController;
import Entity.*;
import SQL.SQLConnect;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectThread extends Thread{
    boolean run=false;
    SQLConnect sqlConnect;
    public Handshake handshake;
    Socket socket;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    public RouteController routeController;
    public ConnectThread(Socket socket, RouteController routeController,SQLConnect sqlConnect) {
        this.socket = socket;
        this.sqlConnect=sqlConnect;
        this.routeController = routeController;
        routeController.add(this);

    }

    @Override
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
    public Message readMessage(){
        try {
            Message message=(Message) objectInputStream.readObject();
            System.out.println("cẩn gửi tin nhắn đến "+message.destinationId+" nội dung"+message.message);
            return message;

        }catch (Exception e){
            run=false;
        }
        return null;
    }
    public void writeMessage(Message message){

            try {
                objectOutputStream.writeObject(message);
                System.out.println("đã gửi nội dung  "+message.message+ " đếm máy đích  "+ message.destinationId);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    public void reconnect(){
        ArrayList<Integer> roomIds=sqlConnect.getRoom(handshake.id);
        System.out.println("reconnect");
        for (Integer roomId:roomIds) {
            getMessage(roomId);
        }
    }
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
    public void classify(Message message){
        if(message==null){}
        else if(message.command==0) {
            routeController.routing(message);
        }
        else if(message.command==1){
           readFile(message);
        }
        else if(message.command==2){
            writeFile(message);
        }
        else if(message.command==3){
            createRoom(message);
        }
        else if(message.command==5){
            System.out.println("call");
            requestCall(message);
        }
        else if(message.command==6){

        }else if(message.command==7){
            routingCall(message);
        }
    }
    public void readFile(Message message){
        //xong
        try {
            FileTransfer fileTransfer=(FileTransfer)message.objects.get(0);
            File file=new File(fileTransfer.name);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(fileTransfer.content);
            fileOutputStream.close();
            Message message1=new Message(message.sourceId,message.destinationId,file.getName(),0);
            message1.file=true;
            routeController.routing(message1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void writeFile(Message message){
        //xong
         try {
             FileTransfer fileTransfer=new FileTransfer();
             File file=new File(message.message);
             FileInputStream fileInputStream=new FileInputStream(file);
             fileTransfer.name=file.getName();
             fileTransfer.content=fileInputStream.readAllBytes();
             fileInputStream.close();
             Message message1=new Message(message.sourceId,"file from server",2);
             message1.objects.add(fileTransfer);
             writeMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createRoom(Message message){
        //xong
        try {
            Room room=(Room) message.objects.get(0);
            System.out.println("nhận đươc người dùng"+room.userIds.get(0));
            sqlConnect.createRoom(room.userIds,message.sourceId);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void destructor(){
        routeController.connectThreads.remove(this);
    }

    public void requestCall(Message message){
        RequestCall requestCall=new RequestCall(String.valueOf(message.sourceId),String.valueOf(message.sourceId));
        Message request=new Message(message.sourceId,message.destinationId,"request call", 6);
        request.objects.add(requestCall);
        routeController.callRouting(message);
    }
    public void responseCall(Message message){

    }
    public void routingCall(Message message){
        try {
            routeController.callRouting(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}