package SFrame.ContactPanel.ContactInput.ContactInputItem;
/**
 * khung nhập để tìm người dùng
 * cũng chưa bắt sự kiện
 * ai biết làm tự động gợi ý làm hộ
 */

import javax.swing.*;
import java.awt.*;

public class ContactInputText extends JTextField {
    public ContactInputText() {
        set();
    }

    private void set(){
        setSize(200,50);
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setColumns(30);
    }
}
