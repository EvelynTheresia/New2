package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public abstract class DataReader{
 
    public List<String[]> readCSVFile(String filePath){
        List<String[]> data = new ArrayList<>();
        String line;
        String delimiter = ";";
;

        try (BufferedReader br = new BufferedReader(new FileReader (filePath))){
            while ((line = br.readLine()) != null){
                String[] fields = line.split(delimiter);
                data.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}