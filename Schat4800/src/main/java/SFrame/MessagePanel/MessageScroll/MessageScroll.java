package SFrame.MessagePanel.MessageScroll;
/**
 * khung tin nhắn có hỗ trợ con lăn
 */

import Connect.Connect;
import SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageCardLayout;

import javax.swing.*;
import java.awt.*;

public class MessageScroll extends JScrollPane {
    public MessageCardLayout messageCardLayout;

    public MessageScroll(Connect connect) {
        messageCardLayout=new MessageCardLayout(connect);
        set();
        setViewportView(messageCardLayout);

    }
    private void set(){
        setBackground(new Color(250, 175, 19,255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
        setPreferredSize(new Dimension(300,900));
    }

}
