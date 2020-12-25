package Connect;
/**
 * luồng này liên tực gửi tin nhắn đính kèm âm thanh
 */

import Entity.AudioTransfer;
import Entity.Message;
import SFrame.SFrameController;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class CallSentThread extends Thread{
    AudioConnect audioConnect;
    TargetDataLine targetDataLine;
    DataLine.Info dataLineInfo;
    boolean running=true;


    public CallSentThread(AudioConnect audioConnect) {
       this.audioConnect = audioConnect;
        try {
            dataLineInfo = new DataLine.Info(TargetDataLine.class, Audio.audioFormat);
            if (!AudioSystem.isLineSupported(dataLineInfo)) {
                System.out.println("vui lòng bật mic");
            } else {
                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                targetDataLine.open(Audio.audioFormat);
                targetDataLine.start();
                start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    /**
     * gửi tin nhắn đi
     * tin nhắn gửi đi có command=7
     */
    public void run() {
        try {
            System.out.println("call start------");
                while (running){
                    if(SFrameController.hasPerson>0) {
                        Message message = new Message(SFrameController.roomId, "audio", 7);
                        AudioTransfer audioTransfer = new AudioTransfer(targetDataLine.getBufferSize() / 5, getAudio());
                        message.objects.add(audioTransfer);
                        audioConnect.writeMessage(message);
                    }
                    else {
                        sleep(100);
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public byte[] getAudio(){
        byte[] bytes = new byte[targetDataLine.getBufferSize() / 5];
        targetDataLine.read(bytes, 0, bytes.length);
        return bytes;
    }
    public void dispose(){
        targetDataLine.stop();
        targetDataLine.close();
        running=false;
    }
}
