package Connect;
/**
 * tạo kết nối tới server  và xử lý gửi và nhận tin nhắn
 * ip 127.0.0.1
 * cổng 5000
 */

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

    /**
     * gửi lời chào đến server, khai báo id máy
     */
    public void handshake(){
        try {
            Handshake handshake=new Handshake(SFrameController.id);
            objectOutputStream.writeObject(handshake);

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
        else  if (message.command==7&&SFrameController.inCall){
            System.out.println(message.objects+"-------------------------------------------------------------------------");
             callReceiveThread.playAudio(message);
         }
        else if(message.command==6){
            System.out.println(" has call request");
         }
    }

    /**
     * hàm này hiển thị một tin nhắn mới lên màn hình
     * @param message
     */
    public void render(Message message){
        sFrameController.sFrame.messagePanel.messageScroll.messageCardLayout.getMessageView(String.valueOf(message.destinationId)).addContent(message.message,message.sourceId,message.file);
        sFrameController.sFrame.messagePanel.messageScroll.repaint();

    }

    /**
     * đọc file từ server và lưu vào máy
     * @param file
     */
    public void writeFile(File file){
        //xong
        try {
            FileInputStream fileInputStream=new FileInputStream(file);

            byte[] bytes=new byte[fileInputStream.available()];
                    fileInputStream.read(bytes);
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

    /**
     * gửi file tớ server
     * @param message
     */
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

    /**
     * tạo phòng chat
     * chưa làm xong
     * @param id
     */
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

    /**
     * gửi yêu cầu cuộc gọi tới server
     * bên server hình như chưa xử lý phần này
     */
    public void call(){
        Message message=new Message(SFrameController.roomId,"call start",5);
        writeMessage(message);
        callSentThread=new CallSentThread(this);
    }

    /**
     * kết thúc cuộc gọi
     * bên server cũng chưa xử lý phần này
     */
    public void callCancel(){
        Message message=new Message(SFrameController.roomId,"call cancel",8 );
        writeMessage(message);
        callSentThread.dispose();
    }

  }
