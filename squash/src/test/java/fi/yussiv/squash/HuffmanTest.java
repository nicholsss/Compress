package fi.yussiv.squash;

import java.util.Map;
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
    public void frequenciesAreCorrect() {
        Map<Character, Long> frequencies = huff.buildFrequencyMap("aaabccbp");

        assertEquals(3, (long) frequencies.get('a'));
        assertEquals(2, (long) frequencies.get('b'));
        assertEquals(2, (long) frequencies.get('c'));
        assertEquals(1, (long) frequencies.get('p'));
        assertNull(frequencies.get('d'));
    }

    @Test
    public void encodingCorrect() {
        String str = "abcabaccaap";
        String enc = huff.encode(str, huff.generateParseTree(str));

        assertEquals("10100010101000011011", enc);
    }

    @Test
    public void encodedStringCanBeDecoded() {
        String str = "testing";
        HuffmanTree tree = huff.generateParseTree(str);
        String enc = huff.encode(str, tree);
        
        assertNotEquals(str, enc);
        assertEquals(str, huff.decode(enc, tree));
    }
}
