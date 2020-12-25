package Entity;

import java.io.Serializable;

public class AudioTransfer implements Serializable {
    public int bufferSize;
    public byte[] bytes;
    public long time;
    public AudioTransfer(int bufferSize, byte[] bytes) {
        this.bufferSize = bufferSize;
        this.bytes = bytes;
        time = System.currentTimeMillis();

    }
}
