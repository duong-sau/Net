package Entity;

import java.io.Serializable;

public class Manifest implements Serializable {
    public int length;
    public String name;

    public Manifest(int length, String name) {
        this.length = length;
        this.name = name;
    }
}
