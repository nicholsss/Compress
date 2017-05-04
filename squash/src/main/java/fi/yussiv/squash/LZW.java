package fi.yussiv.squash;

import fi.yussiv.squash.domain.LZWCodeWord;
import fi.yussiv.squash.domain.LZWTrie;
import fi.yussiv.squash.util.ByteArray;
import fi.yussiv.squash.util.LZWCodeWordArray;

/**
 * Class that performs the Lempel-Ziv-Welch encoding and decoding for byte
 * strings.
 */
public class LZW {
    // maximum dictionary size for 16-bit codeword
    public static final int MAX_DICTIONARY_SIZE = (int) Math.pow(2,16); //65536;

    public static byte[] encode(byte[] input) {
        return encode(input, MAX_DICTIONARY_SIZE);
    }

    /**
     * Encode given byte array with the LZW transform.
     *
     * @param input
     * @param dictionarySize maximum amound of codewords used in encoding
     * @return
     */
    public static byte[] encode(byte[] input, int dictionarySize) {
        LZWTrie dictionary = new LZWTrie();
        LZWCodeWord prefix = new LZWCodeWord();
        ByteArray output = new ByteArray();

        for (int i = 0; i < input.length; i++) {
            if (dictionary.contains(prefix, input[i])) {
                prefix.add(input[i]);
            } else {
                addBytes(output, dictionary.getCodeWord(prefix));
                if (!dictionary.add(prefix, input[i])) {
                    // codeword maximum length reached, reset dictionary
                    dictionary = new LZWTrie();
                }
                prefix.clear();
                prefix.add(input[i]);
            }
            // reset dictionary
            if (dictionary.size() == dictionarySize) {
                dictionary = new LZWTrie();
//                System.err.println("reset");
            }
        }
        addBytes(output, dictionary.getCodeWord(prefix));

        return output.getBytes();
    }

    public static byte[] decode(byte[] input) {
        return decode(input, MAX_DICTIONARY_SIZE);
    }

    /**
     * Decode byte array produced by the encoder back to the original byte
     * array. The dictionary size must match the value the data was encoded
     * with.
     *
     * @param input byte array of encoded data
     * @param dictionarySize maximum amound of codewords used in encoding
     * @return decoded byte array
     */
    public static byte[] decode(byte[] input, int dictionarySize) {
        LZWCodeWordArray dictionary = new LZWCodeWordArray();

        ByteArray output = new ByteArray();

        int index = Byte.toUnsignedInt(input[0]) << 8 | Byte.toUnsignedInt(input[1]);
        int old = index;
        addBytes(output, dictionary.get(index));
        byte postfix;

        for (int j = 2; j < input.length; j += 2) {
            // parse index from two bytes
            index = Byte.toUnsignedInt(input[j]) << 8 | Byte.toUnsignedInt(input[j + 1]);
            if (dictionary.size() > index) {
                addBytes(output, dictionary.get(index));

                postfix = dictionary.get(index).getBytes()[0];
                LZWCodeWord bytes = dictionary.get(old).concatenate(postfix);
                if (bytes != null) {
                    dictionary.add(bytes);
                } else {
                    dictionary.reset();
//                    System.err.println("i am failing");
                }
            } else {
                postfix = dictionary.get(old).getBytes()[0];
                LZWCodeWord bytes = dictionary.get(old).concatenate(postfix);
                dictionary.add(bytes);
                addBytes(output, bytes);
            }
            old = index;
            // reset dictionary to the base set
            if (dictionarySize == dictionary.size()) {
                dictionary.reset();
//                System.err.println("reset dos");
            }
        }
        return output.getBytes();
    }

    private static void addBytes(ByteArray list, LZWCodeWord bytes) {
        for (int i = 0; i < bytes.size(); i++) {
            list.add(bytes.getBytes()[i]);
        }
    }

    private static void addBytes(ByteArray list, int codeWord) {
        int end = codeWord >> 8;
        list.add((byte) end);
        list.add((byte) codeWord);
    }
}
