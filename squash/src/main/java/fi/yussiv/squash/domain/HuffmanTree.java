package fi.yussiv.squash.domain;

import java.io.Serializable;

/**
 * Utility data structure used in the building of a huffman encoding/decoding dictionary.
 */
public class HuffmanTree implements Serializable {

    private byte value;
    private long count;
    private HuffmanTree right;
    private HuffmanTree left;

    public HuffmanTree(byte value) {
        this.value = value;
        this.count = 0;
        this.right = null;
        this.left = null;
    }
    
    public HuffmanTree(long count, HuffmanTree left, HuffmanTree right) {
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public long getCount() {
        return count;
    }

    public byte getValue() {
        return value;
    }

    public HuffmanTree getRight() {
        return right;
    }

    public HuffmanTree getLeft() {
        return left;
    }

    public void incrementCount() {
        count++;
    }
}
