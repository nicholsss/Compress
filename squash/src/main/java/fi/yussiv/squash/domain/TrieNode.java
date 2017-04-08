package fi.yussiv.squash.domain;

/**
 * Node class for the LZWTrie. Forms a singly-linked list with its siblings.
 */
class TrieNode {

    private final int codeWord;
    private final byte value;
    private TrieNode nextSibling;
    private TrieNode firstChild;

    public TrieNode(byte value, int codeWord) {
        this.codeWord = codeWord;
        this.value = value;
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
        TrieNode child = firstChild;
        while (child != null) {
            if (child.getValue() == value) {
                return child;
            }
            child = child.nextSibling;
        }
        // value not found
        return null;
    }

    /**
     * Add new node to the beginning of the child node list.
     *
     * @param value the byte value of the new node
     * @param codeWord the codeword value of the new node
     */
    public void addChild(byte value, int codeWord) {
        TrieNode child = new TrieNode(value, codeWord);
        if (firstChild != null) {
            child.nextSibling = firstChild;
        }
        firstChild = child;
    }

    @Override
    public String toString() {
        return "[" + value + ']';
    }

}
