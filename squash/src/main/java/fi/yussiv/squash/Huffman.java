package fi.yussiv.squash;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {

    /**
     * Generates a Huffman parse tree based on character frequencies.
     *
     * @param input the contents to be encoded
     * @return
     */
    public HuffmanTree generateParseTree(String input) {
        Map<Character, Long> map = buildFrequencyMap(input);
        PriorityQueue<HuffmanTree> nodes = new PriorityQueue<>(map.size(), (a, b) -> a.getCount() <= b.getCount() ? -1 : 1);
        for (Character c : map.keySet()) {
            nodes.add(new HuffmanTree(c, map.get(c), null, null));
        }
        while (nodes.size() > 1) {
            HuffmanTree right = nodes.poll();
            HuffmanTree left = nodes.poll();

            HuffmanTree newRoot = new HuffmanTree(null, left.getCount() + right.getCount(), left, right);
            nodes.add(newRoot);
        }

        return nodes.poll();
    }

    /**
     * Encodes the given input using the given Huffman parse tree.
     *
     * @param input
     * @param tree
     * @return
     */
    public String encode(String input, HuffmanTree tree) {
        Map<Character, String> bitMap = buildBitMap(tree);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            builder.append(bitMap.get(input.charAt(i)));
        }

        return builder.toString();
    }

    /**
     * Decodes the contents using the parse tree that was used to create the
     * encoding.
     *
     * @param contents
     * @param treeRoot
     * @return
     */
    public String decode(String contents, HuffmanTree treeRoot) {
        StringBuilder builder = new StringBuilder();
        HuffmanTree current = treeRoot;

        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) == '0') {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
            if (current.getValue() != null) {
                builder.append(current.getValue());
                current = treeRoot;
            }
        }
        return builder.toString();
    }

    /**
     * Counts the occurances of each character in the input string that is to be
     * encoded.
     *
     * @param str unencoded input string
     * @return
     */
    public Map<Character, Long> buildFrequencyMap(String str) {
        Map<Character, Long> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1L);
            }
        }
        return map;
    }

    /**
     * Builds a map containing bit representations of Huffman codings of each
     * character.
     *
     * @param tree
     * @return
     */
    public Map<Character, String> buildBitMap(HuffmanTree tree) {
        Map<Character, String> map = new HashMap<>();
        walkTree(tree.getLeft(), map, "0");
        walkTree(tree.getRight(), map, "1");
        return map;
    }

    /**
     * Recursive helper method for the building of the bit representations.
     * 
     * @param node the current tree node
     * @param map the map being built
     * @param bits the bit representation built thus far based on edges traversed
     */
    private void walkTree(HuffmanTree node, Map<Character, String> map, String bits) {
        if (node == null) {
            return;
        }
        if (node.getValue() != null) {
            map.put(node.getValue(), bits);
        } else {
            walkTree(node.getLeft(), map, bits + "0");
            walkTree(node.getRight(), map, bits + "1");
        }
    }

    /**
     * A method to roughly visualize a Huffman parse tree.
     * 
     * @param root 
     */
    private void printTree(HuffmanTree root) {
        ArrayDeque<HuffmanTree> current = new ArrayDeque<>();
        ArrayDeque<HuffmanTree> next = new ArrayDeque<>();
        ArrayDeque<HuffmanTree> tmp;

        System.out.println(root.getCount());

        current.add(root.getLeft());
        current.add(root.getRight());

        while (true) {
            while (!current.isEmpty()) {
                HuffmanTree node = current.poll();
                if (node.getValue() != null) {
                    System.out.print(node.getValue() + ":");
                }
                System.out.print(node.getCount() + " ");
                if (node.getLeft() != null) {
                    next.add(node.getLeft());
                }
                if (node.getRight() != null) {
                    next.add(node.getRight());
                }
            }
            System.out.println("");
            if (next.isEmpty()) {
                break;
            }

            tmp = current;
            current = next;
            next = tmp;
        }
    }
}
