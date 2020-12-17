package SFrame.ContactPanel.ContactInput.ContactInputItem;

import javax.swing.*;
import java.awt.*;

public class ContactInputText extends JTextField {
    public ContactInputText() {
        set();
    }

    private void set(){
        setBackground(new Color(22, 255, 9,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setColumns(30);
    }
}
