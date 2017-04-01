package fi.yussiv.squash;

import java.io.UnsupportedEncodingException;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Let's squash some files, shall we?");
        
        System.out.println("");
        System.out.println("--Huffmann--");
        String str = "abcabaccaap";
        System.out.println("input: " + str);
        Huffman huff = new Huffman();
        HuffmanTree tree = huff.generateParseTree(str);
        String encoded = huff.encode(str, tree);

        System.out.println("Encoded as: " + encoded);

        String decoded = huff.decode(encoded, tree);
        System.out.println("Decoded as: " + decoded);

        System.out.println("");
        System.out.println("--LZW--");
        LZW lzw = new LZW();
        String input = "aaa-bab-aaa-cabab";
        System.out.println("input: " + input); // + " bytes: " + Arrays.toString(input.getBytes()));
        byte[] output = lzw.encode(input.getBytes());
        System.out.print("LZW encoded: ");
        for (int i = 0; i < output.length; i++) {
            System.out.printf("%d ", output[i]);
        }
        System.out.println("");
        byte[] decodedLZW = lzw.decode(output);
        System.out.println("Decoded: " + new String(decodedLZW, "UTF-8"));
    }

}
