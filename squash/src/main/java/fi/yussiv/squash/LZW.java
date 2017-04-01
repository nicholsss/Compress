package fi.yussiv.squash;

import fi.yussiv.squash.util.ByteArray;
import fi.yussiv.squash.util.LZWTrie;
import java.util.ArrayList;

/**
 * Class that performs the Lempel-Ziv-Welch encoding and decoding for byte
 * strings.
 */
public class LZW {

    /**
     * Encode given byte array with the LZW transform.
     * http://warp.povusers.org/EfficientLZW/part1.html
     */
    public byte[] encode(byte[] input) {
        LZWTrie dictionary = new LZWTrie();
        ByteArray prefix = new ByteArray();
        ArrayList<Byte> output = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            if (dictionary.contains(prefix, input[i])) {
                prefix.add(input[i]);
            } else {
                addBytes(output, dictionary.getCodeWord(prefix));
                dictionary.add(prefix, input[i]);
                prefix.clear();
                prefix.add(input[i]);
            }
        }
        addBytes(output, dictionary.getCodeWord(prefix));
        byte[] out = new byte[output.size()];
        int i = 0;
        for (Byte b : output) {
            out[i++] = b;
        }
        return out;
    }

    /**
     * Decode byte array produced by the encoder back to the original byte
     * array. 
     * http://warp.povusers.org/EfficientLZW/part5.html
     */
    public byte[] decode(byte[] input) {
        ArrayList<byte[]> dictionary = new ArrayList<>(512);
        byte i = Byte.MIN_VALUE;
        while (true) {
            dictionary.add(new byte[]{i});
            if (i == Byte.MAX_VALUE) {
                break;
            }
            i++;
        }
        ArrayList<Byte> output = new ArrayList<>();

        int index = Byte.toUnsignedInt(input[0]) << 8 | Byte.toUnsignedInt(input[1]);
        int old = index;
        addBytes(output, dictionary.get(index));
        byte postfix;

        for (int j = 2; j < input.length; j += 2) {
            // parse index from two bytes
            index = Byte.toUnsignedInt(input[j]) << 8 | Byte.toUnsignedInt(input[j + 1]);
            if (dictionary.size() > index) {
                addBytes(output, dictionary.get(index));

                postfix = dictionary.get(index)[0];
                byte[] bytes = concatenateBytes(dictionary.get(old), postfix);

                dictionary.add(bytes);
            } else {
                postfix = dictionary.get(old)[0];
                byte[] bytes = concatenateBytes(dictionary.get(old), postfix);

                dictionary.add(bytes);
                addBytes(output, bytes);
            }
            old = index;
        }

        byte[] out = new byte[output.size()];
        int ind = 0;
        for (Byte b : output) {
            out[ind++] = b;
        }
        return out;
    }

    private byte[] concatenateBytes(byte[] bytes, byte addendum) {
        byte[] out = new byte[bytes.length + 1];
        for (int i = 0; i < bytes.length; i++) {
            out[i] = bytes[i];
        }
        out[bytes.length] = addendum;
        return out;
    }

    private void addBytes(ArrayList<Byte> list, byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            list.add(bytes[i]);
        }
    }

    private void addBytes(ArrayList<Byte> list, int codeWord) {
        int shifted = codeWord >> 8;
        list.add((byte) shifted);
        list.add((byte) codeWord);
    }
}
