package SFrame.ContactPanel.ContactScroll.ContactView.Contact.ContactItem;

import javax.swing.*;
import java.awt.*;

public class ContactName extends JLabel {
    public String name;
    public ContactName(String name) {
        this.name=name;
        set();
    }
    public void set(){
        setText(name);
        System.out.println("set name"+name);
        setBorder(BorderFactory.createLineBorder(new Color(255,25,255,255)));
    }
}
