package SFrame.MessagePanel;
/**
 * khung tin nhắn hiển thị lên màn hình
 */

import Connect.Connect;
import SFrame.MessagePanel.MessageInput.MessageInput;
import SFrame.MessagePanel.MessageScroll.MessageScroll;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    public MessageInput messageInput;
    public MessageScroll messageScroll;

    public MessagePanel(Connect connect) {
        set();
        messageInput=new MessageInput();
        messageScroll=new MessageScroll(connect);
        add(messageInput);
        add(messageScroll);
        JLabel jLabel=new JLabel("message View");
        add(jLabel);
    }
    private void set(){
        setBackground(new Color(149, 48, 48,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }
}
