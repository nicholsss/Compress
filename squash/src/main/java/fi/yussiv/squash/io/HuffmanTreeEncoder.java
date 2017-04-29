package fi.yussiv.squash.io;

import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.util.ByteArray;
import fi.yussiv.squash.util.ByteArrayReader;

/**
 * Converts HuffmanTree to a byte array for saving to file.
 */
public class HuffmanTreeEncoder {

    public byte[] encode(HuffmanTree tree) {
        if (tree == null) {
            return new byte[0];
        }
        ByteArray bytes = new ByteArray();
        encodeNode(tree, bytes);

        return bytes.getBytes();
    }

    public HuffmanTree decode(byte[] bytes) throws Exception {
        ByteArrayReader reader = new ByteArrayReader(bytes);
        HuffmanTree root = new HuffmanTree();
        decodeNode(root, reader);
        return root;
    }

    /**
     * Traverses the tree in-order and builds the byte representation.
     *
     * @param node
     * @param builder
     */
    private void encodeNode(HuffmanTree node, ByteArray bytes) {
        // Leaf node = 0, followed by node value, otherwise node = 1.
        if (node.isLeafNode()) {
            bytes.add((byte) 0);
            bytes.add(node.getValue());
        } else {
            bytes.add((byte) 1);
            encodeNode(node.getLeft(), bytes);
            encodeNode(node.getRight(), bytes);
        }
    }

    private void decodeNode(HuffmanTree node, ByteArrayReader reader) throws Exception {
        if (reader.hasNext()) {
            switch (reader.nextByte()) {
                case 1:
                    // not a leaf
                    HuffmanTree left = new HuffmanTree();
                    HuffmanTree right = new HuffmanTree();
                    node.setLeft(left);
                    node.setRight(right);
                    decodeNode(left, reader);
                    decodeNode(right, reader);
                    break;
                case 0:
                    // is a leaf node
                    node.setValue(reader.nextByte());
                    break;
                default:
                    throw new Exception("Could not parse HuffmanTree");
            }
        }
    }
}
