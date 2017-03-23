package fi.yussiv.squash;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    
    public class EncodedPair {
        HuffmanNode tree;
        String contents;
    }

    public EncodedPair encode(String input) {
        Map<Character, Long> map = buildFrequencyMap(input);
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>(map.size(), (a, b) -> a.getCount() <= b.getCount() ? -1 : 1);
        for (Character c : map.keySet()) {
            nodes.add(new HuffmanNode(c, map.get(c), null, null));
        }
        while (nodes.size() > 1) {
            HuffmanNode right = nodes.poll();
            HuffmanNode left = nodes.poll();

            HuffmanNode newRoot = new HuffmanNode(null, left.getCount() + right.getCount(), left, right);
            nodes.add(newRoot);
        }
        HuffmanNode root = nodes.poll();
        
        Map<Character, String> bitMap = buildBitMap(root);
        StringBuilder builder = new StringBuilder();
        for(int i=0; i < input.length(); i++) {
            builder.append(bitMap.get(input.charAt(i)));
        }
        EncodedPair ret = new EncodedPair();
        ret.contents = builder.toString();
        ret.tree = root;
        return ret;
    }

    public String decode(String contents, HuffmanNode treeRoot) {
        StringBuilder builder = new StringBuilder();
        HuffmanNode current = treeRoot;
        
        for (int i = 0; i < contents.length(); i++) {
            if(contents.charAt(i) == '0') {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
            if(current.getValue() != null) {
                builder.append(current.getValue());
                current = treeRoot;
            }
        }
        return builder.toString();
    }
    
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

    public Map<Character, String> buildBitMap(HuffmanNode tree) {
        Map<Character, String> map = new HashMap<>();
        walkTree(tree.getLeft(), map, "0");
        walkTree(tree.getRight(), map, "1");
        return map;
    }

    private void walkTree(HuffmanNode node, Map<Character, String> map, String bits) {
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

    private void printTree(HuffmanNode root) {
        ArrayDeque<HuffmanNode> current = new ArrayDeque<>();
        ArrayDeque<HuffmanNode> next = new ArrayDeque<>();
        ArrayDeque<HuffmanNode> tmp;

        System.out.println(root.getCount());

        current.add(root.getLeft());
        current.add(root.getRight());

        while (true) {
            while (!current.isEmpty()) {
                HuffmanNode node = current.poll();
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
