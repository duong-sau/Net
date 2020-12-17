package SFrame.MessagePanel.MessageInput;

import SFrame.MessagePanel.MessageInput.MessageInputItem.CallInput;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputFile;
import SFrame.MessagePanel.MessageInput.MessageInputItem.MessageInputText;

import javax.swing.*;
import java.awt.*;

public class MessageInput extends JPanel {
    public MessageInputText messageInputText;
    public MessageInputFile messageInputFile;
    public CallInput callInput;
    public MessageInput() {
        set();
        messageInputFile=new MessageInputFile();
        messageInputText=new MessageInputText();
        callInput=new CallInput();
        add(messageInputText);
        add(messageInputFile);
        add(callInput);
    }
    private void set(){
        setBackground(new Color(62, 8, 8,255));
        setBorder(BorderFactory.createLineBorder(new Color(100, 255, 0,255)));
        this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(300,100));
    }
}
