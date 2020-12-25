package SFrame;
/**
 * lớp điều khiển tổng thể'
 * chứa id máy , id phòng nhắn tin hiện tại
 */

import Connect.*;
import Entity.Message;
import Entity.Room;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputText;
import javax.swing.*;
import java.awt.event.*;

public class SFrameController {
    public static String ip = "127.0.0.1";
    public static int portAudio = 5011;
    public static int port = 5009;
    public static boolean inCall=false;
    SFrameController sFrameController;
    public static int id=15;   // id máy của mình
    public static int roomId=0; // id phòng đang nhắn tin
    public SFrame sFrame;
    Connect connect;
    ReadThread readThread;
    JFileChooser jFileChooser;
    public static int hasPerson= 0;
    public SFrameController() {
        sFrameController=this;
        connect=new Connect(this);
        sFrame=new SFrame(connect);
        readThread=new ReadThread(connect);
        jFileChooser=new JFileChooser();
        sFrame.messagePanel.messageInput.messageInputFile.addActionListener(chooseFile);
        sFrame.messagePanel.messageInput.messageInputText.addKeyListener(keyAdapter);
        sFrame.messagePanel.messageInput.callInput.addActionListener(callStart);
        sFrame.contactPanel.contactInput.contactInputAdd.addActionListener(addRoom);
        readThread.start();

    }

    /**
     * sự kiện gửi file
     */
    AbstractAction chooseFile=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fileS=jFileChooser.showOpenDialog(sFrame);
            if(fileS==JFileChooser.APPROVE_OPTION) {
                connect.writeFile(jFileChooser.getSelectedFile());
                System.out.println("gửi file");
            }
            else {
                System.out.println(" không gửi file");
            }
        }
    };
    /**
     * sự kiện gửi tin nhắn
     */
    KeyAdapter keyAdapter=new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                super.keyTyped(e);
                System.out.println("enter");
                Message message=new Message(roomId,((MessageInputText)e.getComponent()).getText());
                connect.writeMessage(message);
                ((MessageInputText)e.getComponent()).setText("");
            }
        }
    };
    public void removeStart(){
        sFrame.messagePanel.messageInput.callInput.addActionListener(callListen);
        sFrame.messagePanel.messageInput.callInput.removeActionListener(callStart);
    }
    AbstractAction callListen=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inCall=true;
            connect.audioConnect.call();
            connect.audioConnect.callReceiveThread=new CallReceiveThread(connect.audioConnect);
            connect.accept();
            sFrame.messagePanel.messageInput.callInput.addActionListener(callCancel);
            sFrame.messagePanel.messageInput.callInput.removeActionListener(this);
            sFrame.messagePanel.messageInput.callInput.removeActionListener(callStart);
            sFrame.messagePanel.messageInput.callInput.cancel();
        }
    };

    /**
     * sự kiện bắt đầu cuộc gọi
     */
    AbstractAction callStart=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inCall=true;
            connect.audioConnect.call();
            connect.audioConnect.callReceiveThread=new CallReceiveThread(connect.audioConnect);
            Message message = new Message(roomId,"call", 110);
            connect.writeMessage(message);
            sFrame.messagePanel.messageInput.callInput.addActionListener(callCancel);
            sFrame.messagePanel.messageInput.callInput.removeActionListener(this);
            sFrame.messagePanel.messageInput.callInput.removeActionListener(callListen);
            sFrame.messagePanel.messageInput.callInput.cancel();
        }
    };
    /**
     * sự kiện kết thức cuojc gọi
     */
    AbstractAction callCancel=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Message message = new Message(roomId,"call", 112);
            connect.writeMessage(message);
            inCall=false;
            connect.out();
            connect.audioConnect.callCancel();
            connect.audioConnect.callReceiveThread.dispose();
            sFrame.messagePanel.messageInput.callInput.addActionListener(callStart);
            sFrame.messagePanel.messageInput.callInput.removeActionListener(this);
            sFrame.messagePanel.messageInput.callInput.de();

        }
    };
    AbstractAction addRoom =new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                Message message = new Message(id, "creatRoom", 10);
                Room room = new Room(sFrame.contactPanel.contactInput.contactInputText.get());
                message.objects.add(room);
                connect.writeMessage(message);
            }
            catch (Exception exception){
                sFrame.messagePanel.roomNotOk();
            }
        }
    };
}
