package fi.yussiv.squash;

import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTest {
    @Test
    public void frequenciesAreCorrect() {
        Huffman huff = new Huffman();
        Map<Character,Long> frequencies = huff.buildFrequencyMap("aaabccbp");
        
        assertEquals(3, (long) frequencies.get('a'));
        assertEquals(2, (long) frequencies.get('b'));
        assertEquals(2, (long) frequencies.get('c'));
        assertEquals(1, (long) frequencies.get('p'));
        assertNull(frequencies.get('d'));
    }
}
