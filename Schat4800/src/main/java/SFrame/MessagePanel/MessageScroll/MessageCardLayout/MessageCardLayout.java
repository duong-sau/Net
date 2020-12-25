package SFrame.MessagePanel.MessageScroll.MessageCardLayout;
/**
 * dùng để thay đổi khung tin nhắn khi click vào phòng chat khác
 */

import Connect.Connect;
import SFrame.MessagePanel.MessageScroll.MessageCardLayout.MessageView.MessageView;
import SFrame.SFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MessageCardLayout extends JPanel {
    public CardLayout cardLayout=new CardLayout();
    public ArrayList<MessageView> messageViews;
    public Connect connect;
    public MessageCardLayout(Connect connect) {
        messageViews=new ArrayList<>();
        this.connect=connect;
        set();
        addMessageView("click the drawer bar");
    }

    private void set(){
        setBackground(new Color(196, 164, 97,255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
        setPreferredSize(new Dimension(300,900));
        setLayout(cardLayout);
    }
    public MessageView getMessageView(String id){
        for(MessageView messageView:messageViews){
            if(messageView.id.equals(id)){
                return messageView;
            }
        }
        return null;
    }
    public void addMessageView(String id){
        MessageView messageView=new MessageView(id,connect);
        messageViews.add(messageView);
        this.add(messageView);
        this.add(id,messageView);
    }
}
