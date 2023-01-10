import au.com.bytecode.opencsv.CSVWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    List<String[]> logs = new ArrayList<>();
    public void log(int productNum, int amount) {
        logs.add(((productNum)+ " , "+ amount).split(" "));
    }
    public  void exportAsCSV(File txtFile){
        try(CSVWriter csvWriter=new CSVWriter(new FileWriter(txtFile,true))){
            csvWriter.writeAll(logs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}