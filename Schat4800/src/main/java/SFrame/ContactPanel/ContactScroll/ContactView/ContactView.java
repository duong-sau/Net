package SFrame.ContactPanel.ContactScroll.ContactView;
/**
 * danh sách các phòng
 */

import SFrame.ContactPanel.ContactScroll.ContactView.Contact.Contact;
import SFrame.MessagePanel.MessagePanel;
import SFrame.SFrameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ContactView extends JPanel {
    public ArrayList<Contact> contacts;
    public MessagePanel messagePanel;
    public ContactView(MessagePanel messagePanel) {
        this.messagePanel=messagePanel;
        contacts=new ArrayList<>();
        set();
    }
    private void set(){
       // setBackground(new Color(0, 10, 255,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }

    public void addContact(String id){
        Contact contact=new Contact(id);
        contacts.add(contact);
        add(contact);
        messagePanel.messageScroll.messageCardLayout.addMessageView(id);
        System.out.println("contact view add contact");
        contact.addMouseListener(mouseAdapter);
        repaint();
    }
    MouseAdapter mouseAdapter=new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("click on contact");
            String name=((Contact)e.getComponent()).name;
            System.out.println(name);
            messagePanel.messageScroll.messageCardLayout.cardLayout.show(messagePanel.messageScroll.messageCardLayout,name);
            SFrameController.roomId=Integer.parseInt(name);
        }
    };
}
