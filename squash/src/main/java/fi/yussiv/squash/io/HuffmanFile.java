
package fi.yussiv.squash.io;

import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.util.ByteArray;

/**
 * Packages the constituent parts of a Huffman encoded file into one byte array.
 */
public class HuffmanFile {
    
    public static byte[] getBytes(HuffmanTree tree, byte[] data) {
        HuffmanTreeEncoder encoder = new HuffmanTreeEncoder();
        
        byte[] treeBytes = encoder.encode(tree);
        ByteArray content = new ByteArray();
        
        content.add(toBytes(0xcaccaa)); // file identifier
        content.add(toBytes(treeBytes.length));
        content.add(toBytes(data.length));
        content.add(treeBytes);
        content.add(data);
        
        return content.getBytes();
    }
    
    private static byte[] toBytes(int number) {
        byte[] out = new byte[4];
        for (int i = 0; i < 4; i++) {
            int tmp = number >> (3 - i) * 8;
            out[i] = (byte) tmp;
        }
        return out;
    }
}
