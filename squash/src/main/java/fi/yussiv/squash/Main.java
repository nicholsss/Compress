package fi.yussiv.squash;

import fi.yussiv.squash.Huffman.EncodedPair;

public class Main {

    public static void main(String[] args) {
        System.out.println("Let's squash some files, shall we?");
        String str = "abcabaccaap";
        
        System.out.println("input: " + str);
        Huffman huff = new Huffman();
        
        EncodedPair encoded = huff.encode(str);
        System.out.println("Encoded as: " + encoded.contents);
        
        String decoded = huff.decode(encoded.contents, encoded.tree);
        System.out.println("Decoded as: " + decoded);
    }
    
}
