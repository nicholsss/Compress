package fi.yussiv.squash.io;

import fi.yussiv.squash.domain.HuffmanTree;

/**
 * Parses a Huffman encoded file from a byte array.
 */
public class HuffmanParser {

    private static final int TREE_OFFSET = 12;
    private byte[] bytes;
    private byte[] dataBytes;
    private HuffmanTree tree;

    public HuffmanParser(byte[] bytes) throws Exception {
        this.bytes = bytes;
        parse();
    }

    public byte[] getData() {
        return dataBytes;
    }

    public HuffmanTree getHuffmanTree() {
        return tree;
    }

    private void parse() throws Exception {
        int id = readInt(0);
        int treeSize = readInt(4);
        int dataSize = readInt(8);

        byte[] treeBytes = getTreeBytes(treeSize);
        HuffmanTreeEncoder encoder = new HuffmanTreeEncoder();
        tree = encoder.decode(treeBytes);
        dataBytes = getDataBytes(treeSize + 12, dataSize);
    }

    public int readInt(int index) {
        if (index >= bytes.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int out = 0;
        for (int i = 3; i >= 0; i--) {
            out |= (0xff & bytes[index++]) << (8 * i);
        }
        return out;
    }

    private byte[] getTreeBytes(int length) {
        byte[] treeBytes = new byte[length];
        int j = 0;
        for (int i = TREE_OFFSET; i < length + TREE_OFFSET; i++) {
            treeBytes[j++] = bytes[i];
        }
        return treeBytes;
    }

    private byte[] getDataBytes(int start, int length) {
        byte[] data = new byte[length];
        int j = 0;
        for (int i = start; i < start + length; i++) {
            data[j++] = bytes[i];
        }
        return data;
    }
}
