//Homework 4: Lowest Common Substring - CS 316
// Lukas A. White - Nov,22,2024

package hw4;

import java.util.*;

//-----------------------------------
// We use a binary tree to calculate things (the character itself, frequency in string, it's children.
class treeNode {
    char character;
    int freq;
    treeNode left, right;

    public treeNode(char character, int freq) {
        this.character = character;
        this.freq = freq;
        this.left = this.right = null;
    }
}

class huffman {
	
	//------------------------------
	// Gotta build that tree
	private static treeNode buildTree(Map<Character, Integer> freqMap) {
		
		// Creates the tree using a PQ so that they are in order
		// java weird, compares the nodes based on the node.frequency
		PriorityQueue<treeNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.freq));
		
		for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            pq.add(new treeNode(entry.getKey(), entry.getValue()));
        }
		
		// Since all chars are in the tree, we must make those chars the leaves of their combined values
		// We do this by taking the 2 lowest frequency nodes and then making them child of their combined
        while (pq.size() > 1) {
        	
        	// poll means Pull from the top of queue. IDK why not pull.
            treeNode left = pq.poll();
            treeNode right = pq.poll();

            // Creates new node by combining them. \0 means null or 0
            treeNode combined = new treeNode('\0', left.freq + right.freq);
            combined.left = left;
            combined.right = right;
            pq.add(combined);
        }

        // return the top of the PQ which points to the entire PQ somehow, this is confusing.
        // Since there is one node in the PQ, that node consists of an entire huffman tree.
        return pq.poll();
		
	}
	
	//-----------------------------------------------------
	// Traverse the tree with dfs to generate what the values of each char are in the new binary
	public static void generateBinary(treeNode root, String binary, Map<Character, String> binaryMap) {
        if (root == null) return;

        // If leaf node, add to the map
        if (root.left == null && root.right == null) {
            binaryMap.put(root.character, binary);
            return;
        }

        generateBinary(root.left, binary + "0", binaryMap);
        generateBinary(root.right, binary + "1", binaryMap);
    }
	
	//------------------------------------------------------
	// compression ratio
    public static double calcCompression(Map<Character, Integer> freqMap, Map<Character, String> binaryMap, int original) {
        int compSize = 0; // total of the compressed 

        for (Map.Entry<Character, Integer> letter : freqMap.entrySet()) {
        	
        	// must get key and value separate in Java apparently
            char character = letter.getKey();            
            int frequency = letter.getValue();
            
            String letBinary = binaryMap.get(character);
            
            compSize += frequency * letBinary.length();
        }
        
        original *= 8;
        return (double) compSize / original; 
    }

	//----------------------------------------------------
	//Main function
    public static void main(String[] args) {
    	
        // Get User Input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine().toLowerCase();

        // Finds the frequencies of each letter
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // Builds the tree
        treeNode root = buildTree(freqMap);

        // This generate the binary representation of each letter
        Map<Character, String> huffmanCodes = new HashMap<>();
        generateBinary(root, "", huffmanCodes);

        // Display
        System.out.println("Huffman Code Table:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Compression
        int originalSize = input.length();  // Size in characters (each character is 1 byte = 8 bits)
        double compressionRatio = calcCompression(freqMap, huffmanCodes, originalSize);
        System.out.printf("Compression ratio = %.4f\n", compressionRatio);
    }
}
