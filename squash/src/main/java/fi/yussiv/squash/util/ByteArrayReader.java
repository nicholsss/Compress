package fi.yussiv.squash.util;

/**
 * A utility for reading content of a byte array sequentially.
 */
public class ByteArrayReader {

    private int position;
    private byte[] array;

    public ByteArrayReader(byte[] array) {
        this.array = array;
        this.position = 0;
    }

    public byte nextByte() {
        if (!hasNext()) {
            return 0;
        }
        return array[position++];
    }

    public boolean hasNext() {
        return position != array.length;
    }
}
