# Performance testing

Initial testing has achieved almost 50 percent reduction in text documents. Huffman coding fares worse compared to LZW with small filesizes, where the added decoding tree uses up a significant amont of space. As expected jpeg files do not benefit from these compression methods, and in fact the Huffman compression actually led to a larger encoded filesize.
