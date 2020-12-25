package SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView.MessageContent;
/**
 * khung nội dung tin nhắn
 * màu đỏ là tin nhắn của bạn
 * màu đen là tin nhắn của mình
 * @file là tin nhắn ở dạng file, cliclk vào đó file sẽ tải về
 * chưa làm thông báo file tải thành công
 */

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
        if(sourceId==SFrameController.id){
            mine=true;
            this.setText("<html>"+sourceId+":     <font color='black'>"+content+"</font></html>");
            setAlignmentX(JLabel.WEST);
        }
        if (file&&sourceId==SFrameController.id){
            mine = true;
            this.setText("<html>"+sourceId+":     <font color='red'>"+"@FILE: "+content+"</font></html>");
            setAlignmentX(JLabel.WEST);
            addMouseListener(mouseAdapter);
        }
        if (file&&sourceId!=SFrameController.id){
            mine = true;
            this.setText("<html>"+sourceId+":     <font color='red'>"+"@FILE: "+content+"</font></html>");
            addMouseListener(mouseAdapter);
        }
        if(sourceId!=SFrameController.id&&!file){
            mine = false;
            this.setText("<html>"+sourceId+":     <font color='black'>"+" "+content+"</font></html>");

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
