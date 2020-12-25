package SFrame.MessagePanel;
/**
 * khung tin nhắn hiển thị lên màn hình
 */

import Connect.Connect;
import SFrame.MessagePanel.MessageInput.MessageInput;
import SFrame.MessagePanel.MessageScroll.MessageScroll;
import java.util.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class MessagePanel extends JPanel {
    Timer timer = new Timer();

    public MessageInput messageInput;
    public MessageScroll messageScroll;
    Color oke = new Color(1,153,1,255);
    Color de = new Color(66,138,222,255);
    Color notOk = new Color(255, 3, 3,255);
    JLabel jLabel;
    public MessagePanel(Connect connect) {
        set();
        messageInput=new MessageInput();
        messageScroll=new MessageScroll(connect);
        add(messageInput);
        add(messageScroll);
        jLabel=new JLabel("Message view");
        add(jLabel);
    }
    private void set(){
        setBackground(de);
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0,255)));
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
    }
    public void MessageOk(){
        jLabel.setText("MessageOke");
        setBackground(oke);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jLabel.setText("MessageView");
                setBackground(de);
            }
        };
        timer.schedule(timerTask,1200);
    }

    public void createRoom(){
        jLabel.setText("Creact room sussu");
        setBackground(oke);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jLabel.setText("MessageView");
                setBackground(de);
            }
        };
        timer.schedule(timerTask,2000);
    }
    public void roomNotOk(){
        jLabel.setText("Room is invalid");
        setBackground(notOk);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jLabel.setText("MessageView");
                setBackground(de);
            }
        };
        timer.schedule(timerTask,2000);
    }
    public void fileOke(){
        jLabel.setText("File dowload oke");
        setBackground(oke);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jLabel.setText("MessageView");
                setBackground(de);
            }
        };
        timer.schedule(timerTask,2000);
    }
}
