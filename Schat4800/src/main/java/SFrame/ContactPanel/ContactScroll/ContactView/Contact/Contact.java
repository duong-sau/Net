package SFrame.ContactPanel.ContactScroll.ContactView.Contact;
/**
 * hiển thị mã phòng và ảnh đại diện phòng
 * ảnh đại diện phòng chưa làm
 */

import SFrame.ContactPanel.ContactScroll.ContactView.Contact.ContactItem.ContactName;
import javax.swing.*;
import java.awt.*;

public class Contact extends JPanel {
    public ContactName contactName;
    public String name;
    public Contact(String name) {
        this.name=name;
        contactName=new ContactName(name);
        System.out.println("contact add contact name");
        add(contactName);
        set();
    }
    private void set(){
        setBorder(BorderFactory.createLineBorder(new Color(0x2428B6)));
        setPreferredSize(new Dimension(450,100));
    }
}
