package fi.yussiv.squash.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCodeWordTest {

    private HuffmanCodeWord code;

    @Before
    public void setUp() {
        code = new HuffmanCodeWord();
    }

    @Test
    public void testSetBit() {
        assertEquals(0, code.size());
        code.setBit(0, true);
        assertEquals(1, code.size());
        assertTrue(code.getBit(0));

        code.setBit(0, false);
        assertEquals(1, code.size());
        assertFalse(code.getBit(0));

        code.setBit(1, false);
        assertEquals(2, code.size());
        assertFalse(code.getBit(1));
    }

    @Test
    public void testClearBit() {
        code.setBit(0, true);

        code.clearBit(0);
        assertEquals(0, code.size());

        code.setBit(0, false);
        code.setBit(1, false);
        code.clearBit(1);

        assertEquals(1, code.size());
    }

    @Test
    public void testDuplicate() {

        code.setBit(0, false);
        code.setBit(1, true);
        code.setBit(2, true);
        code.setBit(3, false);

        assertFalse(code.getBit(0));
        assertTrue(code.getBit(1));
        assertTrue(code.getBit(2));
        assertFalse(code.getBit(3));

        HuffmanCodeWord duplicate = code.duplicate();
        assertEquals(4, duplicate.size());
        assertFalse(duplicate.getBit(0));
        assertTrue(duplicate.getBit(1));
        assertTrue(duplicate.getBit(2));
        assertFalse(duplicate.getBit(3));
    }

}
