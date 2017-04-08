package fi.yussiv.squash;

import fi.yussiv.squash.util.HuffmanTree;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class HuffmanTest {

    private Huffman huff;

    @Before
    public void somethingToBeginWith() {
        huff = new Huffman();
    }

    @Test
    public void encodingCorrect() {
        byte[] input = "abcabaccaap".getBytes();
        byte[] encoded = huff.encode(input, huff.generateParseTree(input));

        byte[] expected = new byte[]{3, 69, -123, 13};
        assertArrayEquals(expected, encoded);
    }

    @Test
    public void encodedStringCanBeDecoded() {
        byte[] input = "testing".getBytes();
        HuffmanTree tree = huff.generateParseTree(input);
        byte[] encoded = huff.encode(input, tree);
        
        assertNotEquals(input, encoded);
        assertArrayEquals(input, huff.decode(encoded, tree));
    }
}
