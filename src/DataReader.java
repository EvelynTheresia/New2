package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides functionality to read data from CSV files.
 * Subclasses can use this method to populate objects from CSV rows.
 */
public abstract class DataReader {

    /**
     * Reads a CSV file and parses each line into a string array, using ';' as the delimiter.
     *
     * @param filePath the path to the CSV file
     * @return a list of string arrays, each representing a row of values
     */
    public List<String[]> readCSVFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        String line;
        String delimiter = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter);
                data.add(fields);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
