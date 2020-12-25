package SFrame.MessagePanel.MessageInput;
/**
 * khung nhập các nội dung tin nhắn
 */

import SFrame.MessagePanel.MessageInput.MessageInputItem.CallInput;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputFile;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputText;

import javax.swing.*;
import java.awt.*;

public class MessageInput extends JPanel {
    public MessageInputText messageInputText;
    public MessageInputFile messageInputFile;
    public CallInput callInput;
    public MessageInput() {
        set();
        messageInputFile=new MessageInputFile();
        messageInputText=new MessageInputText();
        callInput=new CallInput();
        add(messageInputText);
//        add(messageInputFile);
//        add(callInput);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2,1));
        jPanel.add(messageInputFile);
        jPanel.add(callInput);
        add(jPanel);
    }
    private void set(){
        setBackground(new Color(255, 255, 255,255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(300,100));
    }
}
