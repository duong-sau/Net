package SFrame.ContactPanel.ContactScroll;
/**
 * cũng là danh sác các phòng nhưng hỗ trợ con lăn để kéo xuống xen thêm
 */

import SFrame.ContactPanel.ContactScroll.ContactView.ContactView;
import SFrame.MessagePanel.MessagePanel;

import javax.swing.*;
import java.awt.*;


public class ContactScroll extends JScrollPane {
    public ContactView contactView;
    public ContactScroll(MessagePanel messagePanel) {
        set();
        contactView=new ContactView(messagePanel);
        setViewportView(contactView);
    }
    private void set(){
    //    setBackground(new Color(62, 34, 34,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        setPreferredSize(new Dimension(300,890));
    }
}
