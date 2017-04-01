package fi.yussiv.squash;

/**
 * Utility data structure used in the building of a huffman encoding/decoding dictionary.
 */
public class HuffmanTree {

    private Character value;
    private long count;
    private HuffmanTree right;
    private HuffmanTree left;
    private int depth;

    public HuffmanTree(Character value, long count, HuffmanTree left, HuffmanTree right) {
        this.value = value;
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public long getCount() {
        return count;
    }

    public Character getValue() {
        return value;
    }

    public HuffmanTree getRight() {
        return right;
    }

    public HuffmanTree getLeft() {
        return left;
    }

}
