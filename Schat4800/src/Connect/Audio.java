package Connect;

import javax.sound.sampled.AudioFormat;

public class Audio {
    static float sampleRate = 16000;
    static int sampleSizeInBits = 8;
    static int channels = 2;
    static AudioFormat audioFormat=new AudioFormat(sampleRate, sampleSizeInBits, channels, true, true);

}
