package fi.yussiv.squash;

import fi.yussiv.squash.domain.HuffmanTree;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTest {

    @Test
    public void encodingCorrect() {
        byte[] input = "abcabaccaap".getBytes();
        byte[] encoded = Huffman.encode(input, Huffman.generateParseTree(input));

        byte[] expected = new byte[]{5, 69, -123, 13};
        assertArrayEquals(expected, encoded);
    }

    @Test
    public void encodedStringCanBeDecoded() {
        byte[] input = "testing testing, how about some non-ascii characters? üäåëéa".getBytes();
        HuffmanTree tree = Huffman.generateParseTree(input);
        byte[] encoded = Huffman.encode(input, tree);
        
        assertNotEquals(input, encoded);
        assertArrayEquals(input, Huffman.decode(encoded, tree));
    }
}
