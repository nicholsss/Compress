package fi.yussiv.squash.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * A class to handle writing to and reading from files.
 */
public class FileIO {

    public static Object readObjectFromFile(String filename) {
        try (
                FileInputStream file = new FileInputStream(filename);
                BufferedInputStream buff = new BufferedInputStream(file);
                ObjectInputStream in = new ObjectInputStream(buff);) {
            Object obj = in.readObject();
            
            in.close();
            
            return obj;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean writeObjectToFile(String filename, Object obj) {
        try (
                FileOutputStream file = new FileOutputStream(filename);
                BufferedOutputStream buff = new BufferedOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(buff);) {
            
            out.writeObject(obj);
            out.close();
            
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public static byte[] readBytesFromFile(String filename) {
        try (
                FileInputStream file = new FileInputStream(filename);
                BufferedInputStream buff = new BufferedInputStream(file);) {
            
            byte[] in = IOUtils.toByteArray(buff);
            
            return in;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean writeBytesToFile(String filename, byte[] bytes) {
        try (
                FileOutputStream file = new FileOutputStream(filename);
                BufferedOutputStream buff = new BufferedOutputStream(file);) {
            
            IOUtils.write(bytes, buff);
            
            return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
}
