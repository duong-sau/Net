package CallFrame;

import javax.swing.*;

public class CallPanel extends JPanel {
    public JButton mute=new JButton("mute");
    public JButton video=new JButton("video");
    public JButton cancel=new JButton("cancel" );

    public CallPanel() {
        add(mute);
        add(video);
        add(cancel);
    }
}
