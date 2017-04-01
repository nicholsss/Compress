package fi.yussiv.squash.util;

import java.util.LinkedList;

/**
 * Node class for the LZWTrie.
 */
class TrieNode {

    private int codeWord;
    private byte value;
    private LinkedList<TrieNode> children;

    public TrieNode(byte value, int codeWord) {
        this.codeWord = codeWord;
        this.value = value;
        this.children = new LinkedList<>();
    }

    /**
     * @return the value of the code word
     */
    public byte getValue() {
        return value;
    }

    public int getCodeWord() {
        return codeWord;
    }

    /**
     * Look for a child node with the given byte value.
     *
     * @param value
     * @return found child node or null in not found
     */
    public TrieNode getChild(byte value) {
        for (TrieNode child : children) {
            if (child.getValue() == value) {
                return child;
            }
        }
        return null;
    }

    public void addChild(byte value, int codeWord) {
        children.add(new TrieNode(value, codeWord));
    }

    @Override
    public String toString() {
        return "[" + value + ']';
    }

}
