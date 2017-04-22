
package fi.yussiv.squash.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class MinHeapTest {
    
    public MinHeapTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void orderIsCorrect() {
        MinHeap heap = new MinHeap();
        
        heap.add(new HuffmanTree(1, null, null));
        heap.add(new HuffmanTree(2, null, null));
        heap.add(new HuffmanTree(1, null, null));
        heap.add(new HuffmanTree(5, null, null));
        heap.add(new HuffmanTree(0, null, null));
        heap.add(new HuffmanTree(10, null, null));
        heap.add(new HuffmanTree(5, null, null));
        
        assertEquals(0, heap.poll().getCount());
        assertEquals(1, heap.poll().getCount());
        assertEquals(1, heap.poll().getCount());
        assertEquals(2, heap.poll().getCount());
        assertEquals(5, heap.poll().getCount());
        assertEquals(5, heap.poll().getCount());
        assertEquals(10, heap.poll().getCount());
    }
}
