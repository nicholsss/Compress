package fi.yussiv.squash;

import static fi.yussiv.squash.io.FileIO.*;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.io.HuffmanWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Let's squash some files, shall we?");

        String filename = "test.txt";
        System.out.println("");
        System.out.println("--Huffman--");
        
        byte[] input = readBytesFromFile(filename);
        System.out.println("Input size: " + input.length + " bytes");
        
        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        System.out.println("Encoded size: " + encoded.length + " bytes");
        
        // save file
        HuffmanWrapper hw = new HuffmanWrapper(encoded, tree);
        writeObjectToFile("out/encoded.huff", hw);
        System.out.println(">> Saved to out/encoded.huff");
        
        System.out.println("\n<< Reading from out/encoded.huss");
        HuffmanWrapper readHw = (HuffmanWrapper) readObjectFromFile("out/encoded.huff");
        
        byte[] decoded = Huffman.decode(readHw.data, readHw.tree);
        System.out.println("Decoded size: " + decoded.length + " bytes");

        writeBytesToFile("out/decoded.huff.txt", decoded);
        System.out.println(">> Saved to out/decoded.huff.txt");
        
        //--Huffmann--
        //input: abcabaccaap
        //Encoded as: [5, 69, -123, 13]
        //Decoded as: abcabaccaap

        System.out.println("");
        System.out.println("--LZW--");
        
        byte[] inputLZW = readBytesFromFile(filename);
        System.out.println("Input size: " + inputLZW.length + " bytes");
        
        byte[] encodedLZW = LZW.encode(inputLZW);
        System.out.println("LZW encoded size: " +  encodedLZW.length + " bytes");
        
        writeBytesToFile("out/encoded.lzw", encodedLZW);
        System.out.println(">> Saved to out/encoded.lzw");
        
        System.out.println("\n<< Reading from out/encoded.lzw");
        byte[] readLZW = readBytesFromFile("out/encoded.lzw");
        
        byte[] decodedLZW = LZW.decode(readLZW);
        System.out.println("Decoded size: " + decodedLZW.length + " bytes");
        
        writeBytesToFile("out/decoded.lzw.txt", decodedLZW);
        System.out.println(">> Saved to out/decoded.lzw.txt");

        //--LZW--
        //input: aaa-bab-aaa-cabab
        //LZW encoded: 0 -31 1 0 0 -83 0 -30 0 -31 0 -30 0 -83 1 0 0 -31 0 -83 0 -29 1 4 1 4 
        //Decoded: aaa-bab-aaa-cabab
    }

}
