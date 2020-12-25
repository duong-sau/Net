package SFrame.MessagePanel.MessageInput.MessageInputItem;
/**
 * nút gửi file
 */

import javax.swing.*;
import java.awt.*;

public class MessageInputFile extends JButton {
    ImageIcon file = new ImageIcon("./file.png");
    Image img = file.getImage().getScaledInstance(27,27, Image.SCALE_SMOOTH);
    ImageIcon newFile = new ImageIcon(img);
    public MessageInputFile() {
        setIcon(newFile);
        setBackground(new Color(66, 138, 222,255));
    }
}
