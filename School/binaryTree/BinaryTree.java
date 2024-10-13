// Saginaw Valley - CS 316 - Rahman
// Lukas A. White  - HW 2
// purpose: This class implements a binary search tree 
//          and displays all main types of DFS traversals

import java.util.*;

class BinarySearchTree {
	
	// holds the node 'object'
	class Node {
	    int value;
	    Node left, right;

	    // node constructor 
	    public Node(int number) {
	        value = number;
	        left = right = null; // as 
	    }
	}

    Node root; // As trees have roots :/

    // What main calls to construct the tree.
    void constructBST(int[] values) {
        for (int value : values) {
            root = insert(root, value);
        }
    }
    
    // This inserts a new node into the tree at the correct position
    Node insert(Node node, int data) {
        if (node == null) {
        	// can only add where there is empty space
            node = new Node(data);
            return node;
        }
        
        // go down the correct path
        if (data < node.value)
            node.left = insert(node.left, data);
        else if (data > node.value)
            node.right = insert(node.right, data);
        return node;
    }
    
    // in-order LVR
    void LVR(Node node) {
        if (node != null) {
            LVR(node.left);
            System.out.print(" " + node.value);
            LVR(node.right);
        }
    }

    // in-order RVL
    void RVL(Node node) {
        if (node != null) {
            RVL(node.right);
            System.out.print(" " + node.value);
            RVL(node.left);
        }
    }

    // pre-order VLR
    void VLR(Node node) {
        if (node != null) {
        	System.out.print(" " + node.value);
            VLR(node.left);
            VLR(node.right);
        }
    }

    // pre-order VRL
    void VRL(Node node) {
        if (node != null) {
        	System.out.print(" " + node.value);
            VRL(node.right);
            VRL(node.left);
        }
    }

    // post order LRV
    void LRV(Node node) {
        if (node != null) {
            LRV(node.left);
            LRV(node.right);
            System.out.print(" " + node.value);
        }
    }

    // post order RLV
    void RLV(Node node) {
        if (node != null) {
            RLV(node.right);
            RLV(node.left);
            System.out.print(" " + node.value);
        }
    }
}

// The Main class --- Calls the binTree function and displays traversals
public class Main {
    public static void main(String[] args) {

        int[] nodeValues = {23,10,30,5,15,25,40,1,8};  // values we'll put in the tree
        BinarySearchTree bst = new BinarySearchTree(); // create instance of tree class
        bst.constructBST(nodeValues); // turn the array into a tree

        System.out.print("Depth First Traversal: Inorder, LVR ->");
        bst.LVR(bst.root); // this replaces the [V] with a print call to display the value
        
        System.out.print("\nDepth First Traversal: Inorder, RVL ->");  // the "\n" is awesome 
        bst.RVL(bst.root); 

        System.out.print("\nDepth First Traversal: Preorder, VLR ->");
        bst.VLR(bst.root);

        System.out.print("\nDepth First Traversal: Preorder, VRL ->");
        bst.VRL(bst.root);

        System.out.print("\nDepth First Traversal: Postorder, LRV ->");
        bst.LRV(bst.root);
        
        System.out.print("\nDepth First Traversal: Postorder, RLV ->");
        bst.RLV(bst.root);
    }
}
