package fi.yussiv.squash.domain;

/**
 * A dynamically sized bit array representing the code word of a byte produced
 * from the Huffman tree.
 */
public class HuffmanCodeWord {

    private int size;
    private byte[] bits;

    public HuffmanCodeWord() {
        this(new byte[1], 0);
    }

    private HuffmanCodeWord(byte[] bits, int size) {
        this.bits = bits;
        this.size = size;
    }

    /**
     * Sets a bit at the given index (starting from the least significant bit)
     * to a one or a zero.
     *
     * @param index least significant byte == 0
     * @param isSet false = 0, true = 1
     */
    public void setBit(int index, boolean isSet) {
        int arrayIndex = index / 8;
        int bitOffset = index - arrayIndex * 8; // which bit in the byte

        if (arrayIndex >= bits.length) {
            resizeArray(arrayIndex);
        }

        if (isSet) {
            bits[arrayIndex] |= 1 << bitOffset;
        } else {
            byte mask = (byte) (1 << bitOffset);
            mask ^= 0xff; // invert bits
            bits[arrayIndex] &= mask;
        }

        if (index >= size) {
            size = index + 1;
        }
    }

    public void clearBit(int index) {
        if (index < size) {
            setBit(index, false);

            // the algorithm only clears the last bit, so this is the only thing I'm implementing
            if (index == size - 1) {
                size--;
            }
        }
    }

    /**
     * Returns a boolean value representing if the bit at the given index is
     * set.
     *
     * @param index
     * @return
     */
    public boolean getBit(int index) {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException("index was " + index + " but size is " + size);
        }
        int arrayIndex = index / 8;
        int bitOffset = index - arrayIndex * 8;
        byte mask = (byte) (1 << bitOffset);

        return (bits[arrayIndex] & mask) != 0;
    }

    /**
     * @return the amount of bits in the code word.
     */
    public int size() {
        return size;
    }

    public HuffmanCodeWord duplicate() {
        int newSize = (size - 1) / 8 + 1; // create just enough elements to fit the active bytes, not the whole array
        byte[] newBits = new byte[newSize];
        for (int i = 0; i < newSize; i++) {
            newBits[i] = bits[i];
        }
        return new HuffmanCodeWord(newBits, size);
    }

    private void resizeArray(int atLeastSize) {
        int newSize = Math.max(bits.length * 2, atLeastSize);
        byte[] newBits = new byte[newSize];
        for (int i = 0; i < bits.length; i++) {
            newBits[i] = bits[i];
        }

        // replace old array
        bits = newBits;
    }
}
