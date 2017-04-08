# Implementation

### Huffman coding
The main datastructure the algorithm uses is a binary tree used in determining the code words. For this I have implemented a simple tree stucture, where the node value is the byte value to be encoded. The same tree is then used in the decoding process by traversing the tree based on the order the encoded bits. Starting from the root, the idea is to choose the left child node if the input bit is zero and the right child node if the input is one. Once we reach a leaf node, the value of that node is outputted and we return to the root.

### Lempel-Ziv-Welch
The LZW algorithm uses several data structures including a priority queue and a prefix trie which in turn makes use of a simple linked list structure.
