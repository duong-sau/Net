package Entity;

import java.io.Serializable;

public class AudioTransfer implements Serializable {
    public int bufferSize;
    public byte[] bytes;

    public AudioTransfer(int bufferSize, byte[] bytes) {
        this.bufferSize = bufferSize;
        this.bytes = bytes;
    }

}
