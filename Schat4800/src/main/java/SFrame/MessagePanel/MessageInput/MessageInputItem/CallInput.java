package SFrame.MessagePanel.MessageInput.MessageInputItem;
/**
 * nút yêu cầu cuộc gọi
 */

import javax.swing.*;
import java.awt.*;

public class CallInput extends JButton {
    ImageIcon call = new ImageIcon("./phone-call.png");
    Image img = call.getImage().getScaledInstance(27,27, Image.SCALE_SMOOTH);
    ImageIcon newCall = new ImageIcon(img);
    public CallInput() {
        setIcon(newCall);
        setBackground(new Color(66, 138, 222,255));
    }
    public void de(){
        setBackground(new Color(66, 138, 222,255));

    }
    public void cancel(){
        setBackground(new Color(255, 0, 20,255));

    }
    public void allow(){
        setBackground(new Color(45, 255, 0,255));

    }
}
