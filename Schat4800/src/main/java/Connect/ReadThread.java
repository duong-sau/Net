package Connect;
/**
 * luồng này lắng nghe tin nhắn từ server
 * do server có thể gửi tin nhắn đến bất kỳ lúc nào nên cần phải lắng nghe liên tực như server
 */

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
