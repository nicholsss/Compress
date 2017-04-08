package fi.yussiv.squash;

import fi.yussiv.squash.util.HuffmanTree;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("Let's squash some files, shall we?");

        System.out.println("");
        System.out.println("--Huffmann--");
        String str = "abcabaccaap";
        System.out.println("input: " + str);
        byte[] input = str.getBytes();
        Huffman huff = new Huffman();
        
        HuffmanTree tree = huff.generateParseTree(input);
        byte[] encoded = huff.encode(input, tree);

        System.out.println("Encoded as: " + Arrays.toString(encoded));

        byte[] decoded = huff.decode(encoded, tree);
        System.out.println("Decoded as: " + new String(decoded, "UTF-8"));

        //--Huffmann--
        //input: abcabaccaap
        //Encoded as: [5, 69, -123, 13]
        //Decoded as: abcabaccaap

        System.out.println("");
        System.out.println("--LZW--");
        LZW lzw = new LZW();
        str = "aaa-bab-aaa-cabab";
        System.out.println("input: " + str); // + " bytes: " + Arrays.toString(input.getBytes()));
        
        input = str.getBytes();
        byte[] output = lzw.encode(input);
        System.out.print("LZW encoded: ");
        for (int i = 0; i < output.length; i++) {
            System.out.printf("%d ", output[i]);
        }
        System.out.println("");
        byte[] decodedLZW = lzw.decode(output);
        System.out.println("Decoded: " + new String(decodedLZW, "UTF-8"));

        //--LZW--
        //input: aaa-bab-aaa-cabab
        //LZW encoded: 0 -31 1 0 0 -83 0 -30 0 -31 0 -30 0 -83 1 0 0 -31 0 -83 0 -29 1 4 1 4 
        //Decoded: aaa-bab-aaa-cabab
    }

}
