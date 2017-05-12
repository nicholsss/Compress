package test;

import fi.yussiv.squash.Huffman;
import fi.yussiv.squash.LZW;
import fi.yussiv.squash.domain.HuffmanTree;
import static fi.yussiv.squash.io.FileIO.readBytesFromFile;
import static fi.yussiv.squash.io.FileIO.readObjectFromFile;
import static fi.yussiv.squash.io.FileIO.writeBytesToFile;
import static fi.yussiv.squash.io.FileIO.writeObjectToFile;
import fi.yussiv.squash.io.HuffmanFile;
import fi.yussiv.squash.io.HuffmanParser;
import fi.yussiv.squash.io.HuffmanWrapper;
import java.io.IOException;
import java.util.Random;

/**
 * Runs a suite of tests
 */
public class Test {

    public static void runTests() throws Exception {
        encodeHuff();
//        encodeHuffOld();
        encodeLZW();
//        lzwTimeTest();
//        huffTimeTest();
    }

    public static void lzwTimeTest() throws IOException {
        String filename = "testing/test.txt";
        byte[] input = readBytesFromFile(filename);

        // dictionary sizes
        printLZWTestHeader();
        for (int i = 9; i <= 16; i++) {
            runAndPrintLZWResult(input, (int) Math.pow(2, i));
        }

        // different input lengths
        printLZWTestHeader();
        for (int i = 9; i <= 21; i++) {
            runAndPrintLZWResult(truncateBytes(input, (int) Math.pow(2, i)), (int) Math.pow(2, 16));
        }

        // different input lengths with random data
        printLZWTestHeader();
        for (int i = 9; i <= 21; i++) {
            runAndPrintLZWResult(generateRandomInput((int) Math.pow(2, i)), (int) Math.pow(2, 16));
        }
    }

    public static void huffTimeTest() throws IOException {
        String filename = "testing/test.txt";
        byte[] input = readBytesFromFile(filename);

        // different input lengths
        printHuffTestHeader();
        for (int i = 9; i <= 21; i++) {
            runAndPrintHuffResult(truncateBytes(input, (int) Math.pow(2, i)));
        }

        // different input lengths with random data
        printHuffTestHeader();
        for (int i = 9; i <= 21; i++) {
            runAndPrintHuffResult(generateRandomInput((int) Math.pow(2, i)));
        }
    }

    public static void printLZWTestHeader() {
        System.out.println("");
        System.out.println("| Dictionary size | input size (bytes) | encoded size (bytes) | encoded / input (%) | encoding time (us) | decoding time (us) |");
        System.out.println("|---|---|---|---|---|---|");
    }

    public static void printHuffTestHeader() {
        System.out.println("");
        System.out.println("| input size (bytes) | encoded size (bytes) | encoded / input (%) | encoding time (us) | decoding time (us) |");
        System.out.println("|---|---|---|---|---|");
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
        long encodeTime = 0;
        long decodeTime = 0;
        int encodedLen = 0;

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            byte[] encoded = LZW.encode(input, dictionarySize);
            long end = System.nanoTime();
            encodeTime += (end - start) / 1000;
            start = System.nanoTime();
            LZW.decode(encoded, dictionarySize);
            end = System.nanoTime();
            decodeTime += (end - start) / 1000;
            encodedLen += encoded.length;
        }

        System.out.printf("| %d | %d | %d | %.1f | %d | %d |\n",
                dictionarySize,
                input.length,
                encodedLen / 10,
                10.0 * encodedLen / input.length,
                encodeTime / 10,
                decodeTime / 10
        );
    }

    public static void runAndPrintHuffResult(byte[] input) {
        long encodeTime = 0;
        long decodeTime = 0;
        int encodedLen = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        long end = System.nanoTime();
        encodeTime += (end - start) / 1000;
        start = System.nanoTime();
        Huffman.decode(encoded, tree);
        end = System.nanoTime();
        decodeTime += (end - start) / 1000;
        encodedLen += encoded.length;
        }
        
        System.out.printf("| %d | %d | %.1f | %d | %d |\n",
                input.length,
                encodedLen / 10,
                10.0 * encodedLen / input.length,
                encodeTime / 10,
                decodeTime / 10
        );
    }

    public static void encodeLZW() throws IOException {
        String filename = "testing/test.txt";
        System.out.println("--LZW--");

        byte[] inputLZW = readBytesFromFile(filename);
        System.out.println("Input size: " + inputLZW.length + " bytes");

        byte[] encodedLZW = LZW.encode(inputLZW);
        System.out.println("LZW encoded size: " + encodedLZW.length + " bytes");

        writeBytesToFile("testing/tmp_encoded.lzw", encodedLZW);
        System.out.println(">> Saved to testing/tmp_encoded.lzw");

        System.out.println("\n<< Reading from testing/tmp_encoded.lzw");
        byte[] readLZW = readBytesFromFile("testing/tmp_encoded.lzw");

        byte[] decodedLZW = LZW.decode(readLZW);
        System.out.println("Decoded size: " + decodedLZW.length + " bytes");

        writeBytesToFile("testing/tmp_decoded.lzw.txt", decodedLZW);
        System.out.println(">> Saved to testing/tmp_decoded.lzw.txt");
    }

    public static void encodeHuff() throws Exception {
        String filename = "testing/test.txt";
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
        writeBytesToFile("testing/tmp_encoded.huff", fileBytes);
        System.out.println(">> Saved to testing/tmp_encoded.huff");

        System.out.println("\n<< Reading from testing/tmp_encoded.huff");
        byte[] encodedFile = readBytesFromFile("testing/tmp_encoded.huff");

        HuffmanParser parser = new HuffmanParser(encodedFile);
        byte[] decoded = Huffman.decode(parser.getData(), parser.getHuffmanTree());
        System.out.println("Decoded size: " + decoded.length + " bytes");

        writeBytesToFile("testing/tmp_decoded.huff.txt", decoded);
        System.out.println(">> Saved to testing/tmp_decoded.huff.txt");

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
