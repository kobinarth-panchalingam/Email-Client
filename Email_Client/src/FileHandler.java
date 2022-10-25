import java.io.*;
import java.util.ArrayList;
public class FileHandler {
    public static void write(String record, String fileName) {
        try {
            FileWriter out = new FileWriter(fileName, true);    //opening file in append mode
            out.write(record + "\n");     //writing to the file
            out.close();        //closing the file
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static ArrayList<String> read(String fileName) {
        ArrayList<String> records = new ArrayList<>();
        try {
            File file = new File(fileName);
            file.createNewFile();   //creating a file if it doesn't exist
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = fileReader.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return records;

    }


}
