package fi.yussiv.squash.util;

import java.util.BitSet;

/**
 * Represents the code word of a byte produced from the Huffman tree.
 */
public class HuffmanCodeWord {

    private BitSet bits;

    // BitSet doesn't count leading zeroes, so we keep track of bits that are in use
    private BitSet activeBits; 

    public HuffmanCodeWord() {
        this.bits = new BitSet();
        this.activeBits = new BitSet();
    }

    private HuffmanCodeWord(BitSet bits, BitSet activeBits) {
        this.bits = bits;
        this.activeBits = activeBits;
    }

    /**
     * Sets a bit at the given index (starting from the least significant bit)
     * to a one or a zero.
     *
     * @param index least significant bit = 0
     * @param isSet false = 0, true = 1
     */
    public void setBit(int index, boolean isSet) {
        bits.set(index, isSet);
        activeBits.set(index);
    }
    
    public void clearBit(int index) {
        activeBits.clear(index);
    }

    /**
     * Returns a boolean value representing if the bit at the given index is
     * set.
     *
     * @param index
     * @return
     */
    public boolean getBit(int index) {
        return bits.get(index);
    }

    /**
     * @return the amount of bits in the code word.
     */
    public int size() {
        return activeBits.length();
    }
    
    @Override
    public HuffmanCodeWord clone() throws CloneNotSupportedException {
        return new HuffmanCodeWord((BitSet)bits.clone(), (BitSet)activeBits.clone());
    }
}
