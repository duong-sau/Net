package Connect;

import Entity.Message;

public class ReadThread extends Thread{
    Connect connect;
    public ReadThread(Connect connect ) {
        this.connect = connect;
    }

    @Override
    public void run() {
        System.out.println("run read thread");
        Message message;
        while (true) {
            message=connect.readMessage();
            System.out.println(message.command);
            connect.classify(message);
        }
    }
}
