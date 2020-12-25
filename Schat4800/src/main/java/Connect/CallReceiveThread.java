package Connect;
/**
 * lớp này nhận gói tin nhắn có kèm âm thanh và phát ra loa
 */

import Entity.AudioTransfer;
import Entity.Message;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
public class CallReceiveThread extends Thread {
    AudioConnect audioConnect;
    SourceDataLine sourceDataLine;
    DataLine.Info dataLineInfo;

    public CallReceiveThread(AudioConnect audioConnect) {
        this.audioConnect = audioConnect;
        try {
            dataLineInfo = new DataLine.Info(SourceDataLine.class, Audio.audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            sourceDataLine.open(Audio.audioFormat);
            sourceDataLine.start();
            start();
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
                if(message.objects.size()==0){System.out.println("aaaaaaaaaaaaaaaaa");}
                else {
                    //System.out.println("bbbbbbbbbbbb");
                    AudioTransfer audioTransfer = (AudioTransfer) message.objects.get(0);
                    //if(audioTransfer.time< System.currentTimeMillis()+30000){

                    //}
                    //else {
                        sourceDataLine.write(audioTransfer.bytes, 0, audioTransfer.bytes.length);
                   // }
                }
                }catch (Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public void run() {
        while (true) {
            Message message = audioConnect.readMessage();
            playAudio(message);
            //System.out.println("mess");
        }
    }

    public void dispose() {
        this.stop();
    }

}
