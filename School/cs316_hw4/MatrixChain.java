// Homework 4: Matrix Chain - CS 316
// Lukas A. White - Nov, 23, 2024

package hw4;

public class MatrixChain {

    //--------------------------------------------------
    public static void main(String[] args) {
        
    	// The values from the slide
        int[] p = {30, 35, 15, 5, 10, 20, 25};
        
        int n = p.length - 1;
        
        // java by default fills everything in as zero
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        
        
        // Basically the exact same code from the slide but in java, len is the chain length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
            	
                int j = i + len - 1;
                m[i][j] = Integer.MAX_VALUE;
                
                for (int k = i; k < j; k++) {
                	
                    // Similar to the slide, but the    part below had some adjustments
                    int q = m[i][k] +    m[k + 1][j]    + p[i] * p[k + 1] * p[j + 1];
                    
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                    
                }
            }
        }
        
        // size is for the table format, spacing
        System.out.println("m table:");
        printMat(m, n, 6);

        System.out.println("\ns table:");
        printMat(s, n, 6); 

        System.out.println("\nMatrix Parentheses Order:");
        parenthesis(s, 0, n - 1);
        System.out.println();
    }
    
    //-----------------------------------------
    // Prints matrix and formats it so it does not look disgusting, readable
    public static void printMat(int[][] matrix, int n, int rowSpace) {
    	
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            	
            	// This separates the items of a row by rowSpace, they are right aligned "d" 
            	System.out.printf("%" + rowSpace + "d", matrix[i][j]);
            }
            System.out.println();
        }
    }
    
    //-----------------------------------------
    // Parenthesis.
    public static void parenthesis(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1));
        } else {
            System.out.print("(");
            parenthesis(s, i, s[i][j]);
            parenthesis(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }
    
    
}
