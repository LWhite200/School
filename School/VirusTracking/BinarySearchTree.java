package project;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Delta College - CST 283 - Klingler & Gaddis Text        
//Lukas A. White     Project
//This class implements a binary search tree of String data.

public class BinarySearchTree {
	
	
	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
	// below are classes needed for operation 
	
	
    // Node class
    public class Node {
        Patient patient;
        Node left, right;

        // Constructor for leaf nodes
        Node(Patient patient) {
            this.patient = patient;
            left = null;
            right = null;
        }

        // Constructor for non-leaf nodes
        Node(Patient patient, Node leftChild, Node rightChild) {
            this.patient = patient;
            left = leftChild;
            right = rightChild;
        }
    }

    private Node root;

    // Check if the binary tree is empty.
    public boolean isEmpty() {
        return root == null;
    }
    
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Adding elements
    
    public boolean add(Patient patient) {
        root = add(patient, root);
        return true;
    }

    
    private Node add(Patient newPatient, Node bstree) {
        if (bstree == null) {
            return new Node(newPatient);
        }

        // Since the tree is not null
        if (newPatient.getEmail().compareTo(bstree.patient.getEmail()) < 0) {
            // Add newPatient to the left subtree and replace
            // the current left subtree with the result.
            bstree.left = add(newPatient, bstree.left);
        } else {
            // Add newPatient to the right subtree.
            bstree.right = add(newPatient, bstree.right);
        }
        return bstree;
    }
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Removing from the tree

    public boolean remove(Patient patientToRemove) 
    {
        RemovalResult result = remove(root, patientToRemove);
        if (result == null) {
            return false;
        } else {
            root = result.tree;
            return true;
        }
    }

    
    // This remove method removes a patient from a binary search tree
    // and returns the removed node and the remaining tree wrapped in a
    // RemovalResult object.
    private RemovalResult remove(Node bTree, Patient patientToRemove) {
        if (bTree == null) {
            return null;
        }

        // Compares email to find the correct one
        int cmp = patientToRemove.getEmail().compareTo(bTree.patient.getEmail());

        if (cmp < 0) {
            // Removes patient from the left subtree
            RemovalResult result = remove(bTree.left, patientToRemove);
            if (result == null) {
                return null;
            }
            bTree.left = result.tree;
            result.tree = bTree;
            return result;
        } else if (cmp > 0) {
            // Removes patient from the right subtree
            RemovalResult result = remove(bTree.right, patientToRemove);
            if (result == null) {
                return null;
            }
            bTree.right = result.tree;
            result.tree = bTree;
            return result;
        } else {
            // Found the patient to remove in this root node

            // Is it a leaf?
            if (bTree.right == null && bTree.left == null) {
                return new RemovalResult(bTree, null);
            }

            // Does the node have two children?
            if (bTree.right != null && bTree.left != null) {
            	
                // Remove largest node in left subtree and make it the root of the remaining tree.
                RemovalResult remResult = removeLargest(bTree.left);
                Node newRoot = remResult.node;
                newRoot.left = remResult.tree;
                newRoot.right = bTree.right;

                // Prepare the result to be returned.
                bTree.left = null;
                bTree.right = null;
                return new RemovalResult(bTree, newRoot);
            }

            // The node has one child
            Node node = bTree;
            Node tree;
            if (bTree.left != null) {
                tree = bTree.left;
            } else {
                tree = bTree.right;
            }

            node.left = null;
            node.right = null;
            return new RemovalResult(node, tree);
        }
    }   
    
    
    // remember the visualization!!!
    private RemovalResult removeLargest(Node bTree) 
    {
        if (bTree == null) 
            return null;
        
        if (bTree.right == null) 
        {
            // Root is the largest node
            Node tree = bTree.left;
            bTree.left = null;
            return new RemovalResult(bTree, tree);
        } 
        else 
        {
            // Remove the largest node from the right subtree.
            RemovalResult remResult = removeLargest(bTree.right);
            bTree.right = remResult.tree;
            remResult.tree = bTree;
            return remResult;
        }
    }
    
    private class RemovalResult 
    {
        Node node;    // Removed node
        Node tree;    // Remaining tree

        RemovalResult(Node node, Node tree) 
        {
            this.node = node;
            this.tree = tree;
        }
    }

    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Locating functions for various tasks
    
    
    
    // For the people in the specified range
    // ffstrgfd idk if this is what's needed
    // The amount (1 = check dose 1 while 2 does the other one)
    public String[][] findDateRange(String start, String end, int amount) 
    {
        List<String[]> resultList = new ArrayList<>(); // Stores the results temporarily

        // Callss the recursive method
        findDateRange(root, start, end, amount, resultList);

        // To return the people as (email, dose)
        String[][] resultArray = new String[resultList.size()][2];
        for (int i = 0; i < resultList.size(); i++) {
            resultArray[i] = resultList.get(i);
        }

        return resultArray;
    }

    // Recursive helper method to find patients within the date range
    private void findDateRange(Node node, String start, String end, int amount, List<String[]> resultList) 
    {
        if (node != null) 
        {
        	
            // Range Check
            String vaccineDate = (amount == 1) ? node.patient.getDate1() : node.patient.getDate2();
            if (!vaccineDate.equals("0000-00-00") && isDateInRange(vaccineDate, start, end)) {
                String[] patientInfo = {node.patient.getEmail(), vaccineDate};
                resultList.add(patientInfo);
            }
            // Checks the entire tree basically
            findDateRange(node.left, start, end, amount, resultList);
            findDateRange(node.right, start, end, amount, resultList);
        }
    }

    // Checks if the current branch is in the range
    private boolean isDateInRange(String date, String start, String end) {
        return (date.compareTo(start) >= 0) && (date.compareTo(end) <= 0);
    }
    
    
    //=-=-=-=-=-=-=-=-=-=-=-=
    
    
    public Patient findPatientByEmail(String email) {
        return findPatientByEmail(root, email);
    }

    private Patient findPatientByEmail(Node node, String email) {
        if (node == null) {
            return null;
        }

        // email comparison
        int cmp = email.compareTo(node.patient.getEmail());

        if (cmp < 0) {
            return findPatientByEmail(node.left, email); // Search in the left subtree
        } else if (cmp > 0) {
            return findPatientByEmail(node.right, email); // Search in the right subtree
        } else {
            return node.patient; // The patient has been found... Why are they called patients???
        }
    }
    
    
    
    
    // The drop down list so there is less error, like no people or wrong state
    public String findStateCount(String state) {
        // Initialize counters for one shot, no shot, and both shots
        int[] counts = new int[3]; // (oneShot, noShot, bothShot)

        countPatients(root, state, counts);

        // Return a string containing the counts
        return counts[0] + "," + counts[1] + "," + counts[2] + "," + (counts[0] + counts[1] + counts[2]);
    }

    // This function counts the patients and whether or not they got a specific vaccine
    private void countPatients(Node node, String state, int[] counts) {
        if (node != null) {
            
            if (node.patient.getState().equals(state)) // checks the state
            {
                if (!node.patient.getDate1().equals("0000-00-00") && node.patient.getDate2().equals("0000-00-00")) {
                    counts[0]++; // One shot
                } else if (node.patient.getDate1().equals("0000-00-00") && node.patient.getDate2().equals("0000-00-00")) {
                    counts[1]++; // No shot
                } else if (!node.patient.getDate1().equals("0000-00-00") && !node.patient.getDate2().equals("0000-00-00")) {
                    counts[2]++; // Both shots
                }
            }
            countPatients(node.left, state, counts);
            countPatients(node.right, state, counts);
        }
    }
    
    
    
    // The same as above, but uses zip codes, so they may be inputted incorrectly
    public String findZipCount(String state) {
        int[] counts = new int[3]; 
        zipPatients(root, state, counts);
        return counts[0] + "," + counts[1] + "," + counts[2] + "," + (counts[0] + counts[1] + counts[2]);
    }

    private void zipPatients(Node node, String state, int[] counts) {
        if (node != null) {
            if (node.patient.getZip().equals(state)) {
                if (!node.patient.getDate1().equals("0000-00-00") && node.patient.getDate2().equals("0000-00-00")) {
                    counts[0]++; 
                } else if (node.patient.getDate1().equals("0000-00-00") && node.patient.getDate2().equals("0000-00-00")) {
                    counts[1]++; 
                } else if (!node.patient.getDate1().equals("0000-00-00") && !node.patient.getDate2().equals("0000-00-00")) {
                    counts[2]++; 
                }
            }
            zipPatients(node.left, state, counts);
            zipPatients(node.right, state, counts);
        }
    }
    
    
    
    //=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	//=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=--==--==-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=
  	// Other functions 
    
    
    // Writes the tree to the file
    public void quit() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("patients.txt"));
            writePatientsToFile(root, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private void writePatientsToFile(Node node, BufferedWriter writer) throws IOException {
        if (node != null) {
            // Formatting good. Writes back to file.
            String patientInfo = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                   node.patient.getFirstName(),
                   node.patient.getLastName(),
                    node.patient.getAddress(),
                   node.patient.getCity(),
                            node.patient.getState(),           
                         node.patient.getZip(),
                    node.patient.getPhone(),
                   node.patient.getEmail(),
                   node.patient.getDate1(),
                    node.patient.getDate2());
            writer.write(patientInfo);

            writePatientsToFile(node.left, writer);
            writePatientsToFile(node.right, writer);
        }
    }


    // Traverse the binary search tree in order and print patient information.
    public void traverseInOrder() {
        System.out.println("IN-ORDER");
        inorder(root);
        System.out.println("\n\n");
    }

    
    private void inorder(Node btree) {
        if (btree != null) {
            inorder(btree.left);
            System.out.println(btree.patient.getFirstName() + " " + btree.patient.getLastName() + " - " + btree.patient.getEmail()); // Print patient information
            inorder(btree.right);
        }
    }
}
