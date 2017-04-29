package fi.yussiv.squash.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.io.IOUtils;

/**
 * A class to handle writing to and reading from files.
 */
public class FileIO {

    /**
     * @deprecated
     * Use readBytesToFile() and HuffmanParser instead.
     */
    public static Object readObjectFromFile(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        try (
                FileInputStream file = new FileInputStream(filename);
                BufferedInputStream buff = new BufferedInputStream(file);
                ObjectInputStream in = new ObjectInputStream(buff);) {
            Object obj = in.readObject();

            in.close();

            return obj;
        } 
    }

    /**
     * @deprecated
     * Use writeBytesToFile() in conjunction with HuffmanFile.getBytes().
     */
    public static void writeObjectToFile(String filename, Object obj) throws FileNotFoundException, IOException {
        try (
                FileOutputStream file = new FileOutputStream(filename);
                BufferedOutputStream buff = new BufferedOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(buff);) {

            out.writeObject(obj);
            out.close();
        }
    }

    public static byte[] readBytesFromFile(String filename) throws FileNotFoundException, IOException {
        try (
                FileInputStream file = new FileInputStream(filename);
                BufferedInputStream buff = new BufferedInputStream(file);) {

            byte[] in = IOUtils.toByteArray(buff);

            return in;
        } 
    }

    public static void writeBytesToFile(String filename, byte[] bytes) throws FileNotFoundException, IOException {
        try (
                FileOutputStream file = new FileOutputStream(filename);
                BufferedOutputStream buff = new BufferedOutputStream(file);) {

            IOUtils.write(bytes, buff);
        }
    }
}
