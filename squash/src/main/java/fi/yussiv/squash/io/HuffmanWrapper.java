
package fi.yussiv.squash.io;

import fi.yussiv.squash.domain.HuffmanTree;
import java.io.Serializable;

/**
 * I/O class used to save an encoded Huffman file.
 */
public class HuffmanWrapper implements Serializable {
    public byte[] data;
    public HuffmanTree tree;

    public HuffmanWrapper(byte[] data, HuffmanTree tree) {
        this.data = data;
        this.tree = tree;
    }
}
