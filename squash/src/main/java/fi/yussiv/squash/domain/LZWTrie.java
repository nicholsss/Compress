package fi.yussiv.squash.domain;

import java.util.NoSuchElementException;

/**
 * A trie implementation to act as the LZW encoding dictionary.
 */
public class LZWTrie {

    private TrieNode[] root;
    private int size;

    /**
     * Initializes the root level of the trie. As bytes are 8 bits, it always
     * contains 256 elements.
     */
    public LZWTrie() {
        this.root = new TrieNode[256];

        byte b = Byte.MIN_VALUE;
        while (true) {
            this.root[128 + b] = new TrieNode(b, 128 + b);
            if (b == Byte.MAX_VALUE) {
                break;
            }
            b++;
        }
        this.size = 256;
    }

    /**
     * Returns the codeword representing the variable length byte string.
     *
     * @param bytes
     * @return codeword as 32bit integer value
     */
    public int getCodeWord(LZWCodeWord bytes) {
        if (bytes.size() == 0) {
            throw new IllegalArgumentException("Byte string must not be empty");
        }
        if (bytes.size() == 1) {
            int i = 128 + bytes.get(0); // byte value range [-128, 127]
            return root[i].getCodeWord();
        }
        TrieNode node = traverseTree(bytes);
        if (node == null) {
            throw new NoSuchElementException();
        }
        return node.getCodeWord();
    }

    public boolean contains(LZWCodeWord bytes, byte postfix) {
        if (bytes.size() == 0) {
            return true; // if bytes is empty, we are on the root level, which is always populated
        }
        TrieNode node = traverseTree(bytes);
        // did not even find the prefix
        if (node == null) {
            return false;
        }
        // does not have a child with postfix as a value
        if (node.getChild(postfix) == null) {
            return false;
        }
        return true;
    }

    public boolean add(LZWCodeWord bytes, byte postfix) {
        TrieNode node = traverseTree(bytes);
        // the size of the trie acts as the next codeword for the trie node
        node.addChild(postfix, size++);
        return true;
    }

    public int size() {
        return size;
    }

    /**
     * Walks through the trie structure based on the codeword bytes and returns
     * the element it ends up pointing to.
     *
     * @param bytes
     * @return
     */
    private TrieNode traverseTree(LZWCodeWord bytes) {
        TrieNode current = root[128 + bytes.get(0)]; // byte value range [-128, 127], indexes start from 0
        for (int i = 1; i < bytes.size(); i++) {
            current = current.getChild(bytes.get(i));
            if (current == null) {
                return null;
            }
        }
        return current;
    }

}
