package SFrame;

import CallFrame.CallFrame;
import Connect.*;
import Entity.Message;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputText;
import javax.swing.*;
import java.awt.event.*;

public class SFrameController {
    public static boolean inCall=false;
    SFrameController sFrameController;
    public static int id=15;
    public static int roomId=0;
    public SFrame sFrame;
    Connect connect;
    ReadThread readThread;
    JFileChooser jFileChooser;
    public SFrameController() {
        sFrameController=this;
        connect=new Connect(this);
        sFrame=new SFrame(connect);
        readThread=new ReadThread(connect);
        jFileChooser=new JFileChooser();
        sFrame.messagePanel.messageInput.messageInputFile.addActionListener(chooseFile);
        sFrame.messagePanel.messageInput.messageInputText.addKeyListener(keyAdapter);
        sFrame.messagePanel.messageInput.callInput.addActionListener(callStart);
        readThread.start();

    }
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
    CallFrame callFrame;
    AbstractAction callStart=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inCall=true;
            connect.call();
            callFrame=new CallFrame(sFrameController);
            connect.callReceiveThread=new CallReceiveThread(connect);
            callFrame.callPanel.cancel.addActionListener(callCancel);
        }
    };
    AbstractAction callCancel=new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            inCall=false;
            connect.callCancel();
            callFrame.dispose();
            connect.callReceiveThread.dispose();
        }
    };

}
