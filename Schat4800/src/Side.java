import javax.swing.*;
import java.awt.*;

public class Side extends JPanel {
    JPanel contentPanel=new JPanel();
    JPanel sidePanel =new JPanel();

    public Side() {
    }

    @Override
    public Component add(Component comp) {
        return contentPanel.add(comp);
    }
}
