
import SFrame.SFrameController;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        if(args.length!=0){
            SFrameController.id=Integer.parseInt(args[0]);
        }
        if(args.length>1){
            SFrameController.ip = args[1];
        }
        if(args.length>2){
            SFrameController.portAudio = Integer.parseInt(args[2]);
        }
        if(args.length>3){
            SFrameController.port = Integer.parseInt(args[3]);
        }
        SFrameController sFrameController=new SFrameController();
    }
}
