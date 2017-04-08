# Progress Report: Week 4

This week I converted the Huffman coding algorithm to work with a 8-bit alphabet (i.e. bytes). The codeword building proved a bit tricky even when using the Java BitSet class, because it doesn't count leading zeroes, which are essential in the codewords. I solved it by using two BitSets in order to keep track of the relevant bits. Next week I will replace them with something self-made.

While testing the two algorithm implementations with larger datasets I ran into a interesting bug: The LWZ compression and decompression process converted a text file into ANSI format, but did not mess us the content otherwise. It turned out the culprit was the fact that i limited the prefix size to 8 bits, which conveniently did wery little damage to an english language text. Increasing the value on the other hand did introduce garbled text, which had me scratching my head for a while. The problem went away, when I increased the codeword length over eighteen bits. This in turn led naturally to a reduced compression ratio. The codeword length is a limitation for the dictionary size, so it would probably be a good idea to dump the dictionary at regular intervals rather than just increasing the code word length.

Time spent: 14h
