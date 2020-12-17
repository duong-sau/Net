package SFrame.ContactPanel.ContactInput;

import SFrame.ContactPanel.ContactInput.ContactInputItem.ContactInputAdd;
import SFrame.ContactPanel.ContactInput.ContactInputItem.ContactInputText;

import javax.swing.*;
import java.awt.*;

public class ContactInput extends JPanel {
    ContactInputText contactInputText;
    ContactInputAdd contactInputAdd;
    public ContactInput() {
        set();
        contactInputText=new ContactInputText();
        contactInputAdd=new ContactInputAdd();
        add(contactInputText);
        add(contactInputAdd);
    }
    private void set(){
        setBackground(new Color(232, 214, 214,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
    }
}
