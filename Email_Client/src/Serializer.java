import java.io.*;
import java.util.ArrayList;

public class Serializer {
    public static <T> void serialize(T object, String fileName) {
        try {
            File file = new File(fileName);
            file.createNewFile();
            // Creating an new FileOutputStream object
            FileOutputStream fos = new FileOutputStream(fileName, true);
            // If there is nothing to be write onto file
            if (file.length() == 0) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(object);
                oos.close();
            }

            // There is content in file to be write on
            else {
                MyObjectOutputStream oos = new MyObjectOutputStream(fos);
                oos.writeObject(object);
                oos.close();
            }
            fos.close();
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }

    public static <T> ArrayList<T> deSerialize(String fileName, Class<T> type) {
        ArrayList<T> list = new ArrayList<>();
        try {
            File file = new File(fileName);
            if (file.length() != 0) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() != 0) {
                    list.add((T) ois.readObject());
                }
                fis.close();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}

//this class is used to append multiple objects
class MyObjectOutputStream extends ObjectOutputStream {
    MyObjectOutputStream() throws IOException {
        super();
    }

    MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    public void writeStreamHeader() throws IOException {
        return;
    }
}