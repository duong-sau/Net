package Connect;
/**
 * tạo kết nối tới server  và xử lý gửi và nhận tin nhắn
 * ip 127.0.0.1
 * cổng 5000
 */

import Entity.*;
import SFrame.SFrameController;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
public class Connect {
    int manifestLength = 1048576;
    FileConnect fileConnect;
    public AudioConnect audioConnect;
    public Socket socket;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    SFrameController sFrameController;
    public Connect(SFrameController sFrameController) {
        try {
            this.sFrameController=sFrameController;
            socket=new Socket(SFrameController.ip, SFrameController.port);
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

    /**
     * gửi lời chào đến server, khai báo id máy
     */
    public void handshake(){
        try {
            Handshake handshake=new Handshake(SFrameController.id);
            objectOutputStream.writeObject(handshake);
            audioConnect = new AudioConnect();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * đọc tin nhắn
     * @return
     */
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

    /**
     * phân loại tin nhắn
     * @param message
     */
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
        else if(message.command==6){
            callRequest(message);
            System.out.println(" has call request");
         }
        else if(message.command==1){
            messageOke();
         }else if(message.command==11){
             addContact(message);
         }
        else if(message.command==12){
            roomInvalid();
        }else if(message.command==201){
            fileConnect= manifest(message);
         }else if(message.command==1210){
            System.out.println("1111");
             personIn(message);
         }else if(message.command==1201){
             personOut(message);
         }
    }

    /**
     * hàm này hiển thị một tin nhắn mới lên màn hình
     * @param message
     */
    public void render(Message message){
        sFrameController.sFrame.messagePanel.messageScroll.messageCardLayout.getMessageView(String.valueOf(message.destinationId)).addContent(message.message,message.sourceId,message.file);
        sFrameController.sFrame.messagePanel.messageScroll.repaint();
       sFrameController.sFrame.invalidate();
       sFrameController.sFrame.validate();
        sFrameController.sFrame.repaint();
    }

    /**
     * đọc file từ server và lưu vào máy
     * @param file
     */
    public void writeFile(File file){
        try {
        FileInputStream fileInputStream=new FileInputStream(file);
        Manifest manifest = new Manifest(1+fileInputStream.available()/manifestLength,file.getName());
        Message message1 = new Message(SFrameController.roomId, file.getName(), 209);
        message1.objects.add(manifest);
        writeMessage(message1);
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
                Message send=new Message(0,"file from server",210);
                send.objects.add(fileTransfer);
                writeMessage(send);
            }
            fileInputStream.close();
            System.out.println("da gui file");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * gửi file tớ server
     * @param message
     */
    public void readFile(Message message){
        //xong
        try {
            FileTransfer fileTransfer=(FileTransfer)message.objects.get(0);
            boolean finish = fileConnect.add(fileTransfer);
            sFrameController.sFrame.messagePanel.fileOke();
            if (finish){
                fileConnect = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public FileConnect manifest(Message message){
        Manifest manifest = (Manifest) message.objects.get(0);
        FileConnect fileConnect = new FileConnect(manifest.name, manifest.length);
        return fileConnect;
    }
    /**
     * thêm danh sách phòng chat nhận được từ server sau khi bắt tay
     * hiển thị danh sác này lên màn hình
     * @param message
     */
    public void getContact(Message message){
        //xong
    RoomHandshake roomHandshake=(RoomHandshake)message.objects.get(0);
        for (Room room:roomHandshake.rooms){
            String userIds=String.valueOf(room.id);
           sFrameController.sFrame.contactPanel.contactScroll.contactView.addContact(userIds);
        }
    }
    public void addContact(Message message){
        Room room = (Room) message.objects.get(0);
        String userIds=String.valueOf(room.id);
        sFrameController.sFrame.contactPanel.contactScroll.contactView.addContact(userIds);
        sFrameController.sFrame.messagePanel.createRoom();
    }

    public void roomInvalid(){
        sFrameController.sFrame.messagePanel.roomNotOk();
    }
    public void messageOke(){
        sFrameController.sFrame.messagePanel.MessageOk();
    }
    public void callRequest(Message message){
        System.out.println("call");
        sFrameController.sFrame.messagePanel.messageInput.callInput.allow();
        sFrameController.removeStart();
    }
    public void personOut(Message message){
        SFrameController.hasPerson--;
    }
    public void personIn(Message message){
        SFrameController.hasPerson++;
    }
    public void accept(){
        Message message = new Message(SFrameController.roomId,"accept call", 1220);
        writeMessage(message);
    }
    public void out(){
        Message message = new Message(SFrameController.roomId,"accept call", 1202);
        writeMessage(message);
    }

}
