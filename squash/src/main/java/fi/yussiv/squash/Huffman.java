package fi.yussiv.squash;

import fi.yussiv.squash.domain.HuffmanCodeWord;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.domain.MinHeap;
import fi.yussiv.squash.util.ByteArray;

/**
 * Compresses and decompresses input based on byte frequencies using Huffman
 * coding.
 */
public class Huffman {

    /**
     * Generates a Huffman parse tree based on character frequencies.
     *
     * @param input the contents to be encoded
     * @return
     */
    public static HuffmanTree generateParseTree(byte[] input) {
        HuffmanTree[] byteOccurences = countByteOccurences(input);

        MinHeap nodes = new MinHeap();
        for (HuffmanTree tree : byteOccurences) {
            nodes.add(tree);
        }

        while (nodes.size() > 1) {
            HuffmanTree right = nodes.poll();
            HuffmanTree left = nodes.poll();

            HuffmanTree newRoot = new HuffmanTree(left.getCount() + right.getCount(), left, right);
            nodes.add(newRoot);
        }

        return nodes.poll();
    }

    /**
     * Encodes the given input using the given Huffman parse tree. The first
     * byte of the returned array is the effective length of the last byte.
     *
     * @param input
     * @param tree
     * @return array of bytes, where the first byte contains the amount of
     * effectives bits of the last byte
     */
    public static byte[] encode(byte[] input, HuffmanTree tree) {
        HuffmanCodeWord[] codeArray = constructCodeArray(tree);
        ByteArray byteList = new ByteArray();
        HuffmanCodeWord current;
        short offset = 0;
        byte nextByte = 0;
        byteList.add((byte) (offset)); // reserve space for offset

        for (int i = 0; i < input.length; i++) {
            current = codeArray[128 + input[i]];
            for (int j = 0; j < current.size(); j++) {
                if (offset < 8) {
                    if (current.getBit(j)) {
                        nextByte |= 1 << offset;
                    }
                    offset++;
                } else {
                    byteList.add(nextByte);
                    nextByte = 0;
                    if (current.getBit(j)) {
                        nextByte = 1;
                    }
                    offset = 1;
                }
            }
        }
        // last byte onboard
        byteList.add(nextByte);
        // update offset to the actual value
        byteList.set(0, (byte) (offset));

        return byteList.getBytes();
    }

    /**
     * Decodes the contents using the parse tree that was used to create the
     * encoding.
     *
     * @param input
     * @param treeRoot
     * @return
     */
    public static byte[] decode(byte[] input, HuffmanTree treeRoot) {
        ByteArray decodedBytes = new ByteArray();
        HuffmanTree current = treeRoot;

        // first byte is the length of the last byte
        int lastByteLength = input[0];

        int bits = 8;
        for (int i = 1; i < input.length; i++) {
            if (i == input.length - 1) {
                // last byte
                bits = lastByteLength;
            }
            for (int j = 0; j < bits; j++) {
                if (((1 << j) & input[i]) == 0) {
                    current = current.getLeft();
                } else {
                    current = current.getRight();
                }
                // leaves have no children. Also, a Huffman tree is either full or empty, so it is only necessary to check one child.
                if (current.getLeft() == null) {
                    decodedBytes.add(current.getValue());
                    current = treeRoot;
                }
            }

        }

        return decodedBytes.getBytes();
    }

    /**
     * Counts the occurances of each byte in the input byte array that is to be
     * encoded.
     *
     * @param bytes unencoded input byte array
     * @return
     */
    public static HuffmanTree[] countByteOccurences(byte[] bytes) {
        HuffmanTree[] occurrences = new HuffmanTree[256];
        for (int i = 0; i < 256; i++) {
            occurrences[i] = new HuffmanTree((byte) i);
        }
        int idx = 0;
        for (int i = 0; i < bytes.length; i++) {
            idx = 0xff & bytes[i]; // translate byte to index
            occurrences[idx].incrementCount();
        }
        return occurrences;
    }

    /**
     * Builds an array containing the Huffman code words for each byte.
     *
     * @param tree
     * @return
     */
    private static HuffmanCodeWord[] constructCodeArray(HuffmanTree tree) {
        HuffmanCodeWord[] codes = new HuffmanCodeWord[256];
        HuffmanCodeWord code = new HuffmanCodeWord();

        // traverse tree to the left
        code.setBit(0, false);
        walkTree(tree.getLeft(), codes, code);
        // traverse tree to the right
        code.setBit(0, true);
        walkTree(tree.getRight(), codes, code);

        return codes;
    }

    /**
     * Recursive helper method for the building of the bit representations.
     *
     * @param node the current tree node
     * @param map the map being built
     * @param bits the bit representation built thus far based on edges
     * traversed
     */
    private static void walkTree(HuffmanTree node, HuffmanCodeWord[] codes, HuffmanCodeWord code) {
        if (node == null) {
            return;
        }
        if (node.getLeft() == null) {
            codes[128 + node.getValue()] = code.duplicate();
        } else {
            int idx = code.size();

            code.setBit(idx, false);
            walkTree(node.getLeft(), codes, code);
            code.setBit(idx, true);
            walkTree(node.getRight(), codes, code);

            // going up in the recursion -> clear this depth
            code.clearBit(idx);
        }
    }
}
