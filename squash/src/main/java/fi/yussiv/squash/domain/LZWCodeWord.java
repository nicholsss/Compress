package fi.yussiv.squash.domain;

import java.util.Arrays;

/**
 * Utitlity class to help with byte array manipulations. Array size limited to
 * MAX_SIZE elements.
 */
public class LZWCodeWord {

    public static final int MAX_SIZE = 24;
    private final byte[] array;
    private int size;

    public LZWCodeWord() {
        this.array = new byte[MAX_SIZE];
        this.size = 0;
    }

    public boolean add(byte b) {
        if (size < MAX_SIZE) {
            array[size++] = b;
            return true;
        } else {
            return false;
        }
    }

    public byte get(int index) {
        if (index >= size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return array[index];
    }

    public LZWCodeWord concatenate(byte b) {
        LZWCodeWord concatenated = clone();
        if (concatenated.add(b)) {
            return concatenated;
        } else {
            return null;
        }
    }

    /**
     * Doesn't really reset the array contents, only sets the size to zero, so
     * it will be filled from the beginning next time.
     */
    public void clear() {
        size = 0;
    }

    public int size() {
        return size;
    }

    public byte[] getBytes() {
        return array;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public LZWCodeWord clone() {
        LZWCodeWord clone = new LZWCodeWord();
        clone.size = this.size;
        for (int i = 0; i < this.size; i++) {
            clone.array[i] = this.array[i];
        }
        return clone;
    }
}
