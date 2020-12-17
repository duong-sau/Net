package CallFrame;

import SFrame.SFrameController;
import javax.swing.*;
import java.awt.*;

public class CallFrame extends JFrame {
    public CallPanel callPanel;
    public SFrameController sFrameController;
    public CallFrame(SFrameController sFrameController)  {
        this.sFrameController=sFrameController;
        setSize(new Dimension(500,500));
        setVisible(true);
       callPanel=new CallPanel();
       add(callPanel);
    }
}
