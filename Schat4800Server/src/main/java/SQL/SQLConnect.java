package SQL;
/**
 * đây là lớp kết nối tới csdl
 */

import Entity.Message;
import Entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class SQLConnect {
    private static String url = "jdbc:mysql://remotemysql.com/CQfhezArNu";
    private static String userName = "CQfhezArNu";
    private static String password = "BvbPZxslEU";
    Connection connection;
    Statement statement;

    /**
     * tạo kết nối đến csdl
     */
    public SQLConnect(){
        try {
            connection = DriverManager.getConnection(url,userName,password);
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * lưu tin nhắn vào cở sở dữ liệu
     * @param message
     */
    public void saveMessage(Message message){
        System.out.println("lưu tin nhắn vào cơ sở dữ liệu");
        int id=0;
        int sourceId;
        int destinationId;
        boolean file;
        String messageContent;
        sourceId=message.sourceId;
        destinationId=message.destinationId;
        messageContent=message.message;
        file=message.file;
        String value="("+id+","+ sourceId +","+destinationId+",'"+messageContent+ "',"+file+")";
        try {
            statement.execute("INSERT INTO Message (id,sourceId,destinationId,message,file) VALUE "+value);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * chỗ này khởi tạo lại cơ sở dữ liệu từ đầu
     */
    public void reset(){
        try {
            statement.execute("DROP TABLE Message");
            statement.execute("DROP TABLE Room");
            statement.execute("DROP TABLE User");
            statement.execute("CREATE TABLE Message (id INT , sourceId INT , destinationId INT, message VARCHAR(255),file BOOL )");
            statement.executeUpdate("CREATE TABLE Room(id INT , userId int)");
            statement.execute("CREATE TABLE User (id INT,token VARCHAR (255),name VARCHAR (255),image VARCHAR (255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * lấy nội dung tin nhắn từ csdl
     * @param destinationId
     * @return
     */
    public ArrayList<Message> getMessage( int destinationId){
        ArrayList<Message> messages=new ArrayList<>();
        String query="SELECT message,sourceId,file FROM Message WHERE destinationId=" +destinationId;
        try {
            System.out.println(" vào mess");
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println("nội dung tin nhắn "+resultSet.getString(1));
                Message message=new Message(resultSet.getInt(2),destinationId,resultSet.getString(1),0);
                message.file=resultSet.getBoolean(3);
                messages.add(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return messages;
    }

    /**
     * tạo phòng chat
     * nếu muốn 2 người nhắn tin hcho nhau thì tạo phòng chỉ có 2 người
     * ở bên client tôi chưa viết hàm này
     * chỉ gọi khi reset csdl thôi
     * @param userIds danh sách các người dùng được thêm vào phòng
     * @param sourceId id người tạo phòng(cũng sẽ được thêm vào phòng nên không cần có trong danh sách ở trên
     *
     */
    public void createRoom(ArrayList<Integer> userIds, int sourceId){
            try {
                String rID="";
                for (Integer userId:userIds){
                    rID=rID.concat(String.valueOf(userId));
                }
                rID=rID.concat(String.valueOf(sourceId));
               int roomId=Integer.parseInt(rID);
                System.out.println(roomId);
                userIds.add(sourceId);
             for (Integer userId:userIds) {
                 System.out.println("tạo nhóm");
                 String query="INSERT INTO Room (id,userId) VALUE "+"("+(roomId)+","+userId+")";
                 statement.executeUpdate(query);
             }
                }catch (Exception e){
                e.printStackTrace();
            }
    }

    /**
     * lấy danh sách các phòng
     * dùng khi người dùng kết nối lại để lấy các phòng chat mà người ta đã chat trước đó
     * @param destinationId id của người lấy danh sách phòng
     * @return
     */
    public ArrayList<Integer> getRoom(int destinationId){
        try{
            ArrayList<Integer> roomIds=new ArrayList<>();
            String query="SELECT id FROM Room WHERE userId="+destinationId;
            ResultSet resultSet= statement.executeQuery(query);
            while (resultSet.next()){
                roomIds.add(resultSet.getInt(1));
            }
            return roomIds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * lấy danh sách thành viên trong 1 phòng
     * khi gửi tin nhắn thì client chỉ thông báo mã phòng, server lấy danh sách các thành viên để chuyển tiếp tin nhắn
     * @param room
     * @return
     */
    public ArrayList<Integer> getUser(Room room){
        try {
            ArrayList<Integer> userIds=new ArrayList<>();
            String query="SELECT userId FROM Room WHERE id="+room.id;
            ResultSet resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                userIds.add(resultSet.getInt(1));
            }
            return userIds;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
