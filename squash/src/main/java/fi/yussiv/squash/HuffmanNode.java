package fi.yussiv.squash;

public class HuffmanNode {

    private Character value;
    private long count;
    private HuffmanNode right;
    private HuffmanNode left;
    private int depth;

    public HuffmanNode(Character value, long count, HuffmanNode left, HuffmanNode right) {
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

    public HuffmanNode getRight() {
        return right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

}
