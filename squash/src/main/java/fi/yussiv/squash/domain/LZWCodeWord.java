package fi.yussiv.squash.domain;

import fi.yussiv.squash.util.ByteArray;
import java.util.Arrays;

/**
 * Utitlity class to help with byte array manipulations.
 */
public class LZWCodeWord {
    
    private ByteArray byteArray;

    public LZWCodeWord() {
        this.byteArray = new ByteArray();
    }

    public boolean add(byte b) {
        byteArray.add(b);
        return true;
    }

    public byte get(int index) {
        return byteArray.get(index);
    }

    /**
     * Returns a new code word with the supplied byte concatenated at the end.
     * @param b
     * @return 
     */
    public LZWCodeWord concatenate(byte b) {
        LZWCodeWord concatenated = clone();
        concatenated.add(b);
        return concatenated;
    }

    public void clear() {
        byteArray.clear();
    }

    public int size() {
        return byteArray.size();
    }

    public byte[] getBytes() {
        return byteArray.getBytes();
    }

    @Override
    public String toString() {
        return Arrays.toString(byteArray.getBytes());
    }

    @Override
    public LZWCodeWord clone() {
        LZWCodeWord clone = new LZWCodeWord();
        clone.byteArray = this.byteArray.duplicate();
        return clone;
    }
}
