package fi.yussiv.squash.domain;

/**
 * A minimum heap implementation for Huffman coding.
 */
public class MinHeap {

    private HuffmanTree[] heap;
    private int size;

    public MinHeap() {
        this.heap = new HuffmanTree[257]; // 256 elements using 1 indexing
        this.size = 0;
    }

    /**
     * Returns the element with the lowes value.
     *
     * @return
     */
    public HuffmanTree poll() {
        if (size == 0) {
            return null;
        }

        HuffmanTree first = heap[1];

        heap[1] = heap[size--];

        if (size > 0) {
            heapify(1);
        }

        return first;
    }

    /**
     * Add element to the heap.
     *
     * @param element
     */
    public void add(HuffmanTree element) {
        if (size + 1 == heap.length) {
            // this should never happen, because the alphabet is 8bit
            System.err.println("Heap if full");
        }
        heap[++size] = element;

        int index = size;
        while (index > 1) {
            int parentIndex = index / 2;
            if (heap[parentIndex].getCount() > heap[index].getCount()) {
                swapElements(parentIndex, index);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public int size() {
        return size;
    }

    private void heapify(int index) {
        if (index > size) {
            return;
        }

        int leftIndex = left(index);
        int rightIndex = right(index);
        int smallestIndex = index;

        if (leftIndex <= size && heap[smallestIndex].getCount() > heap[leftIndex].getCount()) {
            smallestIndex = leftIndex;
        }

        if (rightIndex <= size && heap[smallestIndex].getCount() > heap[rightIndex].getCount()) {
            smallestIndex = rightIndex;
        }

        if (index != smallestIndex) {
            swapElements(index, smallestIndex);
            heapify(smallestIndex);
        }
    }

    private int right(int parent) {
        return parent * 2 + 1;
    }

    private int left(int parent) {
        return parent * 2;
    }

    private void swapElements(int indexA, int indexB) {
        if (indexA == indexB) {
            return;
        }

        HuffmanTree tmp = heap[indexA];
        heap[indexA] = heap[indexB];
        heap[indexB] = tmp;
    }

}
