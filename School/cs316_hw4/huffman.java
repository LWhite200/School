package hw4;

import java.util.*;

class HuffmanNode {
    char character;
    int frequency;
    HuffmanNode left, right;

    // Constructor
    public HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = this.right = null;
    }
}

class huffman {

    // Function to build the Huffman Tree
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> frequencyMap) {
        // Priority queue (min-heap) to store the nodes based on frequency
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));

        // Build the initial nodes and add them to the priority queue
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        // Build the Huffman Tree
        while (pq.size() > 1) {
            // Remove two nodes with the lowest frequency
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();

            // Create a new node with the combined frequency and add it back to the priority queue
            HuffmanNode merged = new HuffmanNode('\0', left.frequency + right.frequency);
            merged.left = left;
            merged.right = right;
            pq.add(merged);
        }

        // The remaining node is the root of the Huffman Tree
        return pq.poll();
    }

    // Function to generate the Huffman codes
    public static void generateHuffmanCodes(HuffmanNode root, String code, Map<Character, String> huffmanCodes) {
        if (root == null) return;

        // If this is a leaf node, it contains a character
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
            return;
        }

        // Traverse left and right subtrees
        generateHuffmanCodes(root.left, code + "0", huffmanCodes);
        generateHuffmanCodes(root.right, code + "1", huffmanCodes);
    }

    // Function to calculate the compression ratio
    public static double calculateCompressionRatio(Map<Character, Integer> frequencyMap, Map<Character, String> huffmanCodes, int originalSize) {
        int compressedSize = 0;

        // Calculate the total compressed size based on the Huffman codes
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            char character = entry.getKey();
            int frequency = entry.getValue();
            String huffmanCode = huffmanCodes.get(character);
            compressedSize += frequency * huffmanCode.length();
        }

        // Compression ratio = new file size / old file size
        return (double) compressedSize / (originalSize * 8); // Convert original size to bits
    }

    public static void main(String[] args) {
        // Step 1: Read the input string
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine().toLowerCase();  // Convert to lowercase for simplicity

        // Step 2: Calculate frequency of each character
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Step 3: Build Huffman Tree
        HuffmanNode root = buildHuffmanTree(frequencyMap);

        // Step 4: Generate Huffman Codes
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateHuffmanCodes(root, "", huffmanCodes);

        // Step 5: Display Huffman Codes
        System.out.println("Huffman Code Table:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Step 6: Calculate and display Compression Ratio
        int originalSize = input.length();  // Size in characters (each character is 1 byte = 8 bits)
        double compressionRatio = calculateCompressionRatio(frequencyMap, huffmanCodes, originalSize);
        System.out.printf("Compression ratio = %.4f\n", compressionRatio);
    }
}
