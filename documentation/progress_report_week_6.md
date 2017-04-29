# Progress Report: Week 6

This week I finally changed the implementation of HuffmanCodeWord from using BitSets. I also changed the method of saving the Huffman parse tree to a custom byte representation instead of using the standard Serialized interface, thus saving some space. There is still wasted space, because the coding method uses a whole byte to save one bit of information about the node (leaf node or not), but it is much more compact than previously (90% reduction when encoding the 1.5mb test file).

Time spent: 14h
