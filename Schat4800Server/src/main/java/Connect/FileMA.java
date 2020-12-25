package Connect;

import Entity.FileTransfer;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class FileMA {
    long length;
    int current=0;
    ArrayList<FileTransfer> fileTransfers = new ArrayList<>();
    FileOutputStream fileOutputStream;
    String Name;

    public FileMA(String name, long length) {
        try {
            Name = name;
            this.length = length;
            fileOutputStream = new FileOutputStream(name);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean add(FileTransfer fileTransfer){
        try {
            fileTransfers.add(fileTransfer);
            //System.out.println(new String(fileTransfer.content, "UTF-8"));
            return writeFile(fileTransfer.id);
        }catch (Exception e){
            e.printStackTrace();
        }
return false;
    }
    public boolean writeFile(int id) throws Exception{
        System.out.println(id);
        if(id==current+1){
            boolean wrote = false;
            for (int i = 0; i < fileTransfers.size(); i++) {
                if(fileTransfers.get(i).id==id){
                    fileOutputStream.write(fileTransfers.get(i).content);
                    fileTransfers.remove(i);
                    current++;
                    System.out.println("write");
                    wrote = true;
                }
            }
            if(wrote&&current<length){
                writeFile(id+1);
                System.out.println("next");
            }
            else if(current==length){
               // fileOutputStream.write(fileTransfers.get(0).content);
                fileOutputStream.close();
                System.out.println("end");
                return true;
            }
            else if (!wrote){
                System.out.println("dại");
            }
            else System.out.println("default");
        }
        return false;
    }
}
