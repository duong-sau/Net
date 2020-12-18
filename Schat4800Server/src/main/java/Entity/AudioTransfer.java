package Entity;
/**
 * gói này chứa chuỗi liên tục các byte của âm thanh sẽ gửi
 * dùng cho cuộc gọi
 */

import java.io.Serializable;

public class AudioTransfer implements Serializable {
    public int bufferSize;
    public byte[] bytes;

    public AudioTransfer(int bufferSize, byte[] bytes) {
        this.bufferSize = bufferSize;
        this.bytes = bytes;
    }

}
