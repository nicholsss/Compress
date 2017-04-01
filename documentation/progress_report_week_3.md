# Progress Report: Week 3

This week I implemented a rough version of the Lempel-Ziv-Welch algorithm using existing Java data stuctures and a self made trie implementation. It is capable of encoding arbitrary byte arrays and decoding them back the original array. The code still needs improvements and I need to replace the LinkedList and ArrayList classes in the current program with my own implementations. The algorithm also needs some way of limiting the size of the dictionary. At the moment it is hard coded to an eight byte maximum codeword after which if will undoubtably crash.

I still need to make a more generic version of the Huffman encoder from last week, so that it too can handle arbritrary byte arrays.

Time spent: 10h
