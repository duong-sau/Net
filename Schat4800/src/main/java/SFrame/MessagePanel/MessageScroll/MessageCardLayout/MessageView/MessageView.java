package SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView;
/**
 * danh sách các tin nhắn
 */

import Connect.Connect;
import Entity.Message;
import SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView.MessageContent.MessageContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MessageView extends JPanel {
    public String id;
    public Connect connect;
    public ArrayList<MessageContent> messageContents;
    public MessageView(String id,Connect connect) {
        this.id=id;
        this.connect=connect;
        set();
        addContent(id,0,false);
    }
    public void addContent(String content,int sourceId, boolean file) {
        MessageContent messageContent=new MessageContent(content,sourceId,file);
        add(messageContent);
        System.out.println("add content to message view");

    }
    private void set(){
        setBackground(new Color(255, 255, 255,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        setPreferredSize(new Dimension(500,900));
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }


}
