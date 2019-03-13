package HistoryUser;

import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HistoryUser {
    private String userName;
    private File file;
    private File DIR;

    public HistoryUser (String userName) {
        this.userName=userName;
    }

    public void createFile () {
        this.DIR = new File("history/");
        DIR.mkdirs();
        this.file = new File(DIR+"\\Hystory_"+userName+".txt");
        if (!file.exists()) {

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("File exist");
        }
    }

    public void renameFile (String newName) {
        userName=newName;
        if (file.renameTo(new File(DIR+"\\Hystory_"+userName+".txt"))) {
            System.out.println("File rename");
            createFile ();// если я этого не делаю он при новой записи создаст файл со старым именем пользователя и продолжет писать историю туда, а не в переименованный файл
        }else {
            System.out.println("File dont rename!!!");
        }
    }


    public void writeHistory (String msg) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(msg+"\n");
        }catch ( IOException ex) {
            System.out.println("Сообщение не записано в файл!");
        }

    }

    public List<String> readHistory () throws IOException {
        byte counter=0;
        List<String> result= new LinkedList<>();
        try(ReversedLinesFileReader object = new ReversedLinesFileReader(file)) {// не разобралась с RandomAccessFile
            String str;
            while ((str = object.readLine()) != null && counter < 10) {// чтобы проще тестировать counter=10
                System.out.println(str);
                result.add(str);
                counter++;

            }
        }catch (IOException ex) {
            System.out.println("файл не читается!!!");
        }
        return result;

    }

}
