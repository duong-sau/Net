package Entity;
/**
 * dùng cho gửi file
 * gói này chứa chuỗi liên tục các byte của file sẽ gửi
 */

import java.io.Serializable;

public class FileTransfer implements Serializable {
    public String name;
    public byte[] content;

}
