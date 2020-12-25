package SFrame.ContactPanel.ContactInput;
/**
 * chứa thanh và nút tìm kiếm người dùng
 */

import SFrame.ContactPanel.ContactInput.ContactInputItem.ContactInputAdd;
import SFrame.ContactPanel.ContactInput.ContactInputItem.ContactInputText;

import javax.swing.*;
import java.awt.*;

public class ContactInput extends JPanel {
    public ContactInputText contactInputText;
    public ContactInputAdd contactInputAdd;
    public ContactInput() {
        set();
        contactInputText=new ContactInputText();
        contactInputAdd=new ContactInputAdd();
        add(contactInputText);
        add(contactInputAdd);

    }
    private void set(){
        setBackground(new Color(88, 151, 255,255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
    }
}
