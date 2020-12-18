package Connect;
/**
 * lớp này nhận gói tin nhắn có kèm âm thanh và phát ra loa
 */

import Entity.AudioTransfer;
import Entity.Message;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
public class CallReceiveThread {
    Connect connect;
    SourceDataLine sourceDataLine;
    DataLine.Info dataLineInfo;

    /**
     * chỗ này khởi tạo loa
     * @param connect
     */
    public CallReceiveThread(Connect connect) {
        this.connect = connect;
        try {

            dataLineInfo = new DataLine.Info(SourceDataLine.class, Audio.audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(Audio.audioFormat);
            sourceDataLine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * chõ này phát âm thanh
     * @param message
     */
    public void playAudio(Message message){
            try {
                AudioTransfer audioTransfer = (AudioTransfer) message.objects.get(0);
                sourceDataLine.write(audioTransfer.bytes, 0, audioTransfer.bytes.length);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public void dispose() {
    }
}
