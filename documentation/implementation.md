# Implementation

### Huffman coding
The main datastructure the algorithm uses is a binary tree used in determining the code words. For this I have implemented a simple tree stucture, where the node value is the byte value to be encoded. The same tree is then used in the decoding process by traversing the tree based on the order the encoded bits. Starting from the root, the idea is to choose the left child node if the input bit is zero and the right child node if the input is one. Once we reach a leaf node, the value of that node is outputted and we return to the root. The tree is generated using a minimum heap.

The codewords generated with the parse tree have variable lengths. For this reason there is a dynamically sized codeword class (HuffmanCodeWord) that is used for the encoding process.

In order to decode a Huffman coded file, the original parse tree needs to be saved along with the encoded payload. For this I save a byte representation of the tree from which it can be reconstructed for decoding. Essentially it just traverses through the tree in-order and inserts a 0 or 1 for leaf nodes and non-leaf nodes. Leaf nodes have a value associated with them, so after every 0 the byte value of the node is appended.

The output of the decoding and encoding is saved in a dynamically sized byte array (ByteArray).

### Lempel-Ziv-Welch
The LZW algorithm uses a prefix trie. It is a tree structure where every node has an arbitrary amount of children. The child list of a node is a linked list. The codewords are 24 bytes long, which leads to a maximum dictionary size is about 16 million. This is a bit over the top, but convenient because they can be represented by concatenating three bytes.

### I/O
Everything is done in-memory. All input and output is read and written as byte arrays using Apache IOUtils.

### GUI
The application has a very crude GUI mainly intended for demoing purposes.
