package SFrame.MessagePanel.MessageInput.MessageInputItem;
/**
 * khung nhập tin nhắn
 */

import javax.swing.*;
import java.awt.*;

public class MessageInputText extends JTextField {
    public MessageInputText() {
    set();
    }
    private void set(){
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setColumns(30);
        setPreferredSize(new Dimension(150,100));
    }
}
