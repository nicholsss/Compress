package fi.yussiv.squash.domain;

import java.util.Arrays;

/**
 * Utitlity class to help with byte array manipulations.
 * Array size limited to MAX_SIZE elements.
 */
public class ByteArray {

    public static final int MAX_SIZE = 24;
    private final byte[] array;
    private int size;

    public ByteArray() {
        this.array = new byte[MAX_SIZE];
        this.size = 0;
    }

    public void add(byte b) {
        if (size < MAX_SIZE) {
            array[size++] = b;
        } else {
            System.err.println("byte array full!!!");
        }
    }

    public byte get(int index) {
        if (index >= size && index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }

        return array[index];
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
}