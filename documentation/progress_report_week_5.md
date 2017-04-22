# Progress Report: Week 5

This week was spent mostly on creating self made implementations of the data structures previously done with PriorityQueue and ArrayList. There are now domain specific variable length arrays (ByteArray and LZWCodeWordArray) and a minimum heap for creating the Huffman dictionary. The majority of the time used went to refactoring the code. The BitSet is still being used and should be replaced next week.

I added an option to define the LZW dictionary size, and as expected, small dictionary sizes result in poorer compression ratio. In fact it seems that a dictionary with less than 16k values results in a larger "compressed" file. Additionally with a 64k dictionary, a file less than 32kb in size does not benefit from compression almost at all.

Time spent: 12h
