package SFrame;
/**
 * mà hình chính
 */

import Connect.Connect;
import SFrame.ContactPanel.ContactPanel;
import SFrame.MessagePanel.MessagePanel;

import javax.swing.*;
import java.awt.*;

public class SFrame extends JFrame{
    public Connect connect;
    public ContactPanel contactPanel;
    public MessagePanel messagePanel;
    JPanel sPanel=new JPanel();
    public SFrame(Connect connect) {
        this.connect=connect;
        set();
        messagePanel=new MessagePanel(connect);
        contactPanel=new ContactPanel(messagePanel);
        sPanel.add(contactPanel);
        sPanel.add(messagePanel);
        this.setSize(970,850);
    }
    public void set(){

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        sPanel.setSize(960,800);
        sPanel.setLayout(new GridLayout(1,2));
        this.add(sPanel);
        this.setLayout(null);
        System.out.println("tạo");
    }

}
