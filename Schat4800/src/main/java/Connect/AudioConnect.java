package Connect;

import Entity.Handshake;
import Entity.Message;
import SFrame.SFrameController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AudioConnect {
    Socket socket;
    public CallSentThread callSentThread;
    public CallReceiveThread callReceiveThread;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    public AudioConnect() {
        try {
            socket = new Socket(SFrameController.ip, SFrameController.portAudio);
            objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            objectInputStream=new ObjectInputStream(socket.getInputStream());

            handshake();
        } catch (IOException e) {
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

    public void writeMessage(Message message){
        //xong
        try{
            objectOutputStream.writeObject(message);
        }catch (Exception e){
            e.printStackTrace();
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
