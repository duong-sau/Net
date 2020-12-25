package SFrame.ContactPanel.ContactInput.ContactInputItem;
/**
 * khung nhập để tìm người dùng
 * cũng chưa bắt sự kiện
 * ai biết làm tự động gợi ý làm hộ
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactInputText extends JTextField {
    public ContactInputText() {
        set();
    }

    private void set(){
        setSize(200,50);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
        this.setColumns(30);
    }
    public ArrayList<Integer> get() throws Exception{
        ArrayList<Integer> integers= new ArrayList<>();
        String s = this.getText();
        String[] strings = s.split("\\s");
        for (int j = 0; j < strings.length; j++) {
            integers.add(Integer.parseInt(strings[j]));
        }
        setText("");
        return integers;
    }
}
