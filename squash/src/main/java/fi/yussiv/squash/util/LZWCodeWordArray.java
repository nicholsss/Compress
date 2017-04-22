package fi.yussiv.squash.util;

import fi.yussiv.squash.domain.LZWCodeWord;

/**
 * Dynamically sized array for the LZW decoding dictionary.
 */
public class LZWCodeWordArray {

    private static final int INITIAL_SIZE = 1024;
    private LZWCodeWord[] array;
    private int size;

    public LZWCodeWordArray() {
        this.array = new LZWCodeWord[INITIAL_SIZE];
        this.size = 0;
        populateArray();
    }

    public void add(LZWCodeWord element) {
        if (size == array.length) {
            resizeArray();
        }
        array[size++] = element;
    }

    public LZWCodeWord get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("index " + index + " requested, but size is " + size);
        }
        return array[index];
    }

    public int size() {
        return size;
    }

    public void reset() {
        this.size = 256;
    }

    private void resizeArray() {
        LZWCodeWord[] newArray = new LZWCodeWord[size * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    /**
     * Populates the dictionary with the first 256 codewords.
     */
    private void populateArray() {
        byte i = Byte.MIN_VALUE;
        while (true) {
            LZWCodeWord codeword = new LZWCodeWord();
            codeword.add(i);
            array[size++] = codeword;
            if (i == Byte.MAX_VALUE) {
                break;
            }
            i++;
        }
    }
}
