package hw4;

public class MatrixChain {

    // Main function to test the program
    public static void main(String[] args) {
        
    	// The values from the slide
        int[] p = {30, 35, 15, 5, 10, 20, 25};
        
        int n = p.length - 1;
        
        int[][] m = new int[n][n];
        int[][] s = new int[n][n];
        
        // Chain length
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i < n - len + 1; i++) {
                int j = i + len - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                	
                    // Calculate the cost of the current split (the formula)
                    int q = m[i][k] + m[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        
        System.out.println("m table (minimum number of multiplications):");
        printFormattedMatrix(m, n, 6);

        System.out.println("\ns table (optimal split points):");
        printFormattedMatrix(s, n, 6); 

        // This calls for the parenthesis output.
        System.out.println("\nOptimal Matrix Chain Multiplication Order:");
        printOptimalParenthesization(s, 0, n - 1);
        System.out.println();
    }
    
    // Prints the matrixes with parameters to adjust the size of the output as they're weird.
    public static void printFormattedMatrix(int[][] matrix, int n, int columnSpacing) {
    	
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == Integer.MAX_VALUE) {
                    System.out.printf("%" + columnSpacing + "s", "∞");
                } else {
                    System.out.printf("%" + columnSpacing + "d", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }
    
    // This constructs the parenthesis.
    public static void printOptimalParenthesization(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1));
        } else {
            System.out.print("(");
            printOptimalParenthesization(s, i, s[i][j]);
            printOptimalParenthesization(s, s[i][j] + 1, j);
            System.out.print(")");
        }
    }
    
    
}
