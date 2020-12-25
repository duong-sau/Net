/**
 * bắt đầu chạy chương trình ở đây
 */

import Connect.ConnectClient;
import SQL.SQLConnect;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if(args.length!=0){
            ConnectClient.port=Integer.parseInt(args[0]);
        }
        if(args.length>1){
            ConnectClient.portAudio=Integer.parseInt(args[1]);
        }
        ConnectClient connect=new ConnectClient();
/*
        // nếu muốn khởi tạo lại csdl thì bỏ comment vùng này
        SQLConnect sqlConnect=new SQLConnect();
        sqlConnect.reset();
        ArrayList<Integer> integerArrayList=new ArrayList<>();
        integerArrayList.add(50);
        sqlConnect.createRoom(integerArrayList,15);




 */


    }
}
