package fi.yussiv.squash;

import static fi.yussiv.squash.io.FileIO.*;
import fi.yussiv.squash.domain.HuffmanTree;
import fi.yussiv.squash.io.HuffmanFile;
import fi.yussiv.squash.io.HuffmanParser;
import fi.yussiv.squash.io.HuffmanWrapper;
import java.io.IOException;
import fi.yussiv.squash.ui.GUI;
import static fi.yussiv.squash.ui.GUI.run;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Let's squash some files, shall we?");
        run(new GUI(), 600, 250);
//        encodeHuff();
//        encodeHuffOld();
//        encodeLZW();
//        lzwTimeTest();
//        huffTimeTest();
    }

    public static void lzwTimeTest() throws IOException {
        String filename = "test.txt";
        byte[] input = readBytesFromFile(filename);
        System.out.println("");
        System.out.println("| Dictionary size | input size | encoded size | encoded / input ratio | encoding time (us) |");
        System.out.println("|-----------------|------------|--------------|--------------|--------------------|");
        runAndPrintLZWResult(input, 1024);
        runAndPrintLZWResult(input, 4096);
        runAndPrintLZWResult(input, 16384);
        runAndPrintLZWResult(input, 65536);
        runAndPrintLZWResult(input, 262144);
        runAndPrintLZWResult(input, 1048576);

        System.out.println("");
        System.out.println("| Dictionary size | input size | encoded size | encoded / input ratio | encoding time (us) |");
        System.out.println("|-----------------|------------|--------------|--------------|--------------------|");
        runAndPrintLZWResult(truncateBytes(input, 1024), 65536);
        runAndPrintLZWResult(truncateBytes(input, 2048), 65536);
        runAndPrintLZWResult(truncateBytes(input, 4096), 65536);
        runAndPrintLZWResult(truncateBytes(input, 8192), 65536);
        runAndPrintLZWResult(truncateBytes(input, 16384), 65536);
        runAndPrintLZWResult(truncateBytes(input, 32768), 65536);
        runAndPrintLZWResult(truncateBytes(input, 65536), 65536);
        runAndPrintLZWResult(truncateBytes(input, 131072), 65536);
        runAndPrintLZWResult(truncateBytes(input, 262144), 65536);
        runAndPrintLZWResult(truncateBytes(input, 524288), 65536);
        runAndPrintLZWResult(truncateBytes(input, 1048576), 65536);

        System.out.println("");
        System.out.println("| Dictionary size | input size | encoded size | encoded / input ratio | encoding time (us) |");
        System.out.println("|-----------------|------------|--------------|--------------|--------------------|");
        runAndPrintLZWResult(generateRandomInput(4069), 65536);
        runAndPrintLZWResult(generateRandomInput(8192), 65536);
        runAndPrintLZWResult(generateRandomInput(16384), 65536);
        runAndPrintLZWResult(generateRandomInput(32768), 65536);
        runAndPrintLZWResult(generateRandomInput(65536), 65536);
        runAndPrintLZWResult(generateRandomInput(131072), 65536);
        runAndPrintLZWResult(generateRandomInput(262144), 65536);
        runAndPrintLZWResult(generateRandomInput(524288), 65536);
        runAndPrintLZWResult(generateRandomInput(1048576), 65536);
        runAndPrintLZWResult(generateRandomInput(2097152), 65536);
        runAndPrintLZWResult(generateRandomInput(4194304), 65536);
//
//        System.out.println("");
//        System.out.println("| Dictionary size | input size | encoded size | encoded / input ratio | encoding time (us) | ");
//        System.out.println("|-----------------|------------|--------------|--------------|--------------------|");
//        runAndPrintLZWResult(generateRandomInput(4069), 131072);
//        runAndPrintLZWResult(generateRandomInput(8192), 131072);
//        runAndPrintLZWResult(generateRandomInput(16384), 131072);
//        runAndPrintLZWResult(generateRandomInput(32768), 131072);
//        runAndPrintLZWResult(generateRandomInput(65536), 131072);
//        runAndPrintLZWResult(generateRandomInput(131072), 131072);
//        runAndPrintLZWResult(generateRandomInput(262144), 131072);
//        runAndPrintLZWResult(generateRandomInput(524288), 131072);
//        runAndPrintLZWResult(generateRandomInput(1048576), 131072);
//        runAndPrintLZWResult(generateRandomInput(2097152), 131072);
    }

    public static void huffTimeTest() throws IOException {
        String filename = "test.txt";
        byte[] input = readBytesFromFile(filename);
        System.out.println("");
        System.out.println("| input size | encoded size | encoded / input ratio | encoding time (us) | ");
        System.out.println("|------------|--------------|--------------|--------------------|");
        runAndPrintHuffResult(truncateBytes(input, 4069));
        runAndPrintHuffResult(truncateBytes(input, 8192));
        runAndPrintHuffResult(truncateBytes(input, 16384));
        runAndPrintHuffResult(truncateBytes(input, 32768));
        runAndPrintHuffResult(truncateBytes(input, 65536));
        runAndPrintHuffResult(truncateBytes(input, 131072));
        runAndPrintHuffResult(truncateBytes(input, 262144));
        runAndPrintHuffResult(truncateBytes(input, 524288));
        runAndPrintHuffResult(truncateBytes(input, 1048576));

        System.out.println("");
        System.out.println("| input size | encoded size | encoded / input ratio | encoding time (us) | ");
        System.out.println("|------------|--------------|--------------|--------------------|");
        runAndPrintHuffResult(generateRandomInput(4069));
        runAndPrintHuffResult(generateRandomInput(8192));
        runAndPrintHuffResult(generateRandomInput(16384));
        runAndPrintHuffResult(generateRandomInput(32768));
        runAndPrintHuffResult(generateRandomInput(65536));
        runAndPrintHuffResult(generateRandomInput(131072));
        runAndPrintHuffResult(generateRandomInput(262144));
        runAndPrintHuffResult(generateRandomInput(524288));
        runAndPrintHuffResult(generateRandomInput(1048576));
        runAndPrintHuffResult(generateRandomInput(2097152));
    }

    public static byte[] generateRandomInput(int size) {
        Random rand = new Random(System.nanoTime());
        byte[] array = new byte[size];
        rand.nextBytes(array);
        return array;
    }

    public static byte[] truncateBytes(byte[] bytes, int length) {
        byte[] out = new byte[length];
        for (int i = 0; i < length; i++) {
            out[i] = bytes[i];
        }
        return out;
    }

    public static void runAndPrintLZWResult(byte[] input, int dictionarySize) {

        long start = System.nanoTime();
        byte[] encoded = LZW.encode(input, dictionarySize);
        LZW.decode(encoded, dictionarySize);
        long end = System.nanoTime();
        long duration = (end - start) / 1000;
        System.out.printf("| %d | %d | %d | %.2f | %d |\n",
                dictionarySize,
                input.length,
                encoded.length,
                (double) encoded.length / input.length,
                duration
        //                (input.length * Math.log(input.length)/Math.log(2)) / duration
        );
    }

    public static void runAndPrintHuffResult(byte[] input) {

        long start = System.nanoTime();
        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        Huffman.decode(encoded, tree);
        long end = System.nanoTime();
        long duration = (end - start) / 1000;
        System.out.printf("| %d | %d | %.2f | %d |\n",
                input.length,
                encoded.length,
                (double) encoded.length / input.length,
                duration
        //                (input.length * (Math.log(input.length) / Math.log(2))) / duration
        );
    }

    public static void encodeLZW() throws IOException {
        String filename = "test.txt";
        System.out.println("--LZW--");

        byte[] inputLZW = readBytesFromFile(filename);
        System.out.println("Input size: " + inputLZW.length + " bytes");

        byte[] encodedLZW = LZW.encode(inputLZW);
        System.out.println("LZW encoded size: " + encodedLZW.length + " bytes");

        writeBytesToFile("out/encoded.lzw", encodedLZW);
        System.out.println(">> Saved to out/encoded.lzw");

        System.out.println("\n<< Reading from out/encoded.lzw");
        byte[] readLZW = readBytesFromFile("out/encoded.lzw");

        byte[] decodedLZW = LZW.decode(readLZW);
        System.out.println("Decoded size: " + decodedLZW.length + " bytes");

        writeBytesToFile("out/decoded.lzw.txt", decodedLZW);
        System.out.println(">> Saved to out/decoded.lzw.txt");
    }

    public static void encodeHuff() throws Exception {
        String filename = "test.txt";
        System.out.println("");
        System.out.println("--Huffman--");

        byte[] input = readBytesFromFile(filename);
        System.out.println("Input size: " + input.length + " bytes");

        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        System.out.println("Encoded size: " + encoded.length + " bytes");

        // save file
        byte[] fileBytes = HuffmanFile.getBytes(tree, encoded);
        System.out.println("filebytes: tree=" + (fileBytes.length - encoded.length) + " data=" + encoded.length);
        writeBytesToFile("out/encoded.huff", fileBytes);
        System.out.println(">> Saved to out/encoded.huff");

        System.out.println("\n<< Reading from out/encoded.huff");
        byte[] encodedFile = readBytesFromFile("out/encoded.huff");

        HuffmanParser parser = new HuffmanParser(encodedFile);
        byte[] decoded = Huffman.decode(parser.getData(), parser.getHuffmanTree());
        System.out.println("Decoded size: " + decoded.length + " bytes");

        writeBytesToFile("out/decoded.huff.txt", decoded);
        System.out.println(">> Saved to out/decoded.huff.txt");

    }

    public static void encodeHuffOld() throws Exception {
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

        System.out.println("\n<< Reading from out/encoded.huff");
        HuffmanWrapper readHw = (HuffmanWrapper) readObjectFromFile("out/encoded.huff");

        byte[] decoded = Huffman.decode(readHw.data, readHw.tree);
        System.out.println("Decoded size: " + decoded.length + " bytes");

        writeBytesToFile("out/decoded.huff.txt", decoded);
        System.out.println(">> Saved to out/decoded.huff.txt");

    }
}
