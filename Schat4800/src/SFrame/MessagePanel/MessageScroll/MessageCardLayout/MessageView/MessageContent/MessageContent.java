package SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView.MessageContent;

import Entity.Message;
import SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView.MessageView;
import SFrame.SFrameController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MessageContent extends JLabel {
    String content;
    public int sourceId;
    boolean mine=false;
    boolean file=false;

    public MessageContent(String content, int sourceId, boolean file) {
        this.content = content;
        this.sourceId = sourceId;
        this.file = file;
        set();
        if(sourceId== SFrameController.id){
            mine=true;
            this.setText("<html>"+sourceId+":     <font color='red'>"+content+"</font></html>");setAlignmentX(JLabel.WEST);
        }
        if (file){
            this.setText("@file"+content);
            addMouseListener(mouseAdapter);
        }
    }

    private void set(){
        setText(content);
    }
    MouseAdapter mouseAdapter=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Message message=new Message(0,content,2);
            ((MessageView)getParent()).connect.writeMessage(message);
        }
    };
}
