package Connect;

import Entity.*;
import SFrame.SFrameController;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
public class Connect {
    public Socket socket;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    SFrameController sFrameController;
    CallSentThread callSentThread;
    public CallReceiveThread callReceiveThread;
    public Connect(SFrameController sFrameController) {
        try {
            this.sFrameController=sFrameController;
            socket=new Socket("127.0.0.1",5000);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());
            handshake();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeMessage(Message message){
        //xong
        try{
        objectOutputStream.writeObject(message);
    }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void handshake(){
        try {
            Handshake handshake=new Handshake(SFrameController.id);
            objectOutputStream.writeObject(handshake);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    synchronized public Message readMessage(){
        try {
            Message message=(Message) objectInputStream.readObject();
          //  System.out.println("nhận được tin nhắn từ  "+message.destinationId+" nội dung"+message.message);
            return message;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    synchronized public void classify(Message message){
        System.out.println("phan loai tin nhan");
         if(message.command==2){
            readFile(message);
        }
        else if(message.command==0){
            render(message);
        }
        else if(message.command==4){
            System.out.println("get rôm");
            getContact(message);
         }
        else  if (message.command==7&&SFrameController.inCall){
            System.out.println(message.objects+"-------------------------------------------------------------------------");
             callReceiveThread.playAudio(message);
         }
        else if(message.command==6){
            System.out.println(" has call request");
         }
    }
    public void render(Message message){
        sFrameController.sFrame.messagePanel.messageScroll.messageCardLayout.getMessageView(String.valueOf(message.destinationId)).addContent(message.message,message.sourceId,message.file);
        sFrameController.sFrame.messagePanel.messageScroll.repaint();

    }
    public void writeFile(File file){
        //xong
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            byte[] bytes=fileInputStream.readAllBytes();
            FileTransfer fileTransfer=new FileTransfer();
            fileTransfer.name=file.getName();
            fileTransfer.content=bytes;
            Message message=new Message(SFrameController.roomId,"file",1);
            message.objects.add(fileTransfer);
            writeMessage(message);
            System.out.println("da gui file");

        } catch (Exception e) {
            e.printStackTrace();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void createRoom(int id){
        //xong
        try {
            ArrayList<Integer> userIds=new ArrayList<>();
            userIds.add(id);
            Room room=new Room(userIds);
            Message message=new Message(0,"create room",3);
            message.objects.add(room);
            writeMessage(message);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getContact(Message message){
        //xong
    RoomHandshake roomHandshake=(RoomHandshake)message.objects.get(0);
        for (Room room:roomHandshake.rooms){
            String userIds=String.valueOf(room.id);
           sFrameController.sFrame.contactPanel.contactScroll.contactView.addContact(userIds);
        }
    }
    public void call(){
        Message message=new Message(SFrameController.roomId,"call start",5);
        writeMessage(message);
        callSentThread=new CallSentThread(this);
    }
    public void callCancel(){
        Message message=new Message(SFrameController.roomId,"call cancel",8 );
        writeMessage(message);
        callSentThread.dispose();
    }

  }
