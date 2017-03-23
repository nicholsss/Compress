package fi.yussiv.squash;

public class Main {

    public static void main(String[] args) {
        System.out.println("Let's squash some files, shall we?");
        String str = "abcabaccaap";

        System.out.println("input: " + str);
        Huffman huff = new Huffman();

        HuffmanTree tree = huff.generateParseTree(str);
        String encoded = huff.encode(str, tree);

        System.out.println("Encoded as: " + encoded);

        String decoded = huff.decode(encoded, tree);
        System.out.println("Decoded as: " + decoded);
    }

}
