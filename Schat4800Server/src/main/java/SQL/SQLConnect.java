package SQL;

import Entity.Handshake;
import Entity.Message;
import Entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class SQLConnect {
    private static String url = "jdbc:mysql://sql12.freesqldatabase.com/sql12376804";
    private static String userName = "sql12376804";
    private static String password = "qjyEbYU93I";
    Connection connection;
    Statement statement;
    public SQLConnect(){
        try {
            connection = DriverManager.getConnection(url,userName,password);
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
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
