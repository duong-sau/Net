package SFrame.ContactPanel;
/**
 * cũng là danh sách các phòng
 * dùng để hiển thị lên màn hình
 */

import SFrame.ContactPanel.ContactInput.ContactInput;
import SFrame.ContactPanel.ContactScroll.ContactScroll;
import SFrame.MessagePanel.MessagePanel;


import javax.swing.*;
import java.awt.*;

public class ContactPanel extends JPanel {
    public ContactInput contactInput;
    public ContactScroll contactScroll;
    public ContactPanel(MessagePanel messagePanel) {
        set();
        contactInput=new ContactInput();
        contactScroll=new ContactScroll(messagePanel);
        add(contactInput);
        add(contactScroll);
    }
    private void set(){
       // setBackground(new Color(255, 0, 0,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }
}
