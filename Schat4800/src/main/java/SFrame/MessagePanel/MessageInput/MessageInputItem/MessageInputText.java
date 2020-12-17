package SFrame.MessagePanel.MessageInput.MessageInputItem;

import javax.swing.*;
import java.awt.*;

public class MessageInputText extends JTextField {
    public MessageInputText() {
    set();
    }
    private void set(){
        setBackground(new Color(22, 255, 9,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setColumns(30);
        setPreferredSize(new Dimension(15,300));
    }
}
