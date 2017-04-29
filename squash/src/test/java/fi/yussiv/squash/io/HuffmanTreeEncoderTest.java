/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.yussiv.squash.io;

import fi.yussiv.squash.domain.HuffmanTree;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Heptaurus
 */
public class HuffmanTreeEncoderTest {
    
    HuffmanTreeEncoder encoder = new HuffmanTreeEncoder();
    
    @Before
    public void setUp() {
    }

    @Test
    public void testEncode() {
        HuffmanTree root = new HuffmanTree(
                0, 
                new HuffmanTree((byte) 5),
                new HuffmanTree(
                        0, 
                        new HuffmanTree((byte) 7),
                        new HuffmanTree((byte) 2)
                )
        );
        byte[] encoded = encoder.encode(root);
        assertArrayEquals(new byte[]{1, 0, 5, 1, 0, 7, 0, 2}, encoded);
        
        HuffmanTree decoded = encoder.decode(encoded);
        HuffmanTree left = decoded.getLeft();
        HuffmanTree right = decoded.getRight();
        assertNotNull(left);
        assertNotNull(right);
        assertTrue(left.isLeafNode());
        assertEquals((byte) 5, left.getValue());
        assertFalse(right.isLeafNode());
        assertEquals((byte) 7, right.getLeft().getValue());
        assertEquals((byte) 2, right.getRight().getValue());
    }
    
}
