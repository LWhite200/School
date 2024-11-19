package hw4;

import java.util.Scanner;

public class LCS {
	
    public static void main(String[] args) {
  
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the first string: ");
        String one = scanner.nextLine();
        
        System.out.print("Enter the second string: ");
        String two = scanner.nextLine();
        
        String result = LCS(one.toLowerCase(), two.toLowerCase()); // make lower case 
        
        System.out.println("Longest Common Subsequence: " + result);
    }

    public static String LCS(String one, String two) {
    	
    	// declare lengths for easy reading
        int m = one.length();
        int n = two.length();
        
        // create 2d-cache
        int[][] dp = new int[m + 1][n + 1];
        String[][] dir = new String[m + 1][n + 1]; // u, d, l, r, ul, ur...
        
        // iterate through the two strings
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    dir[i][j] = "ul";
                } else if (dp[i - 1][j] >= dp[i][j - 1]){
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    dir[i][j] = "u";
                }
                else {
                	dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    dir[i][j] = "l";
                }
            }
        }
        

        String lcs = "";        
        int x = m, y = n;
        
        // Must traverse in reverse as the arrows at the end point to the best path
        while (x > 0 && y > 0) {
            if (dir[x][y].equals("ul")) {
                lcs += one.charAt(x - 1);
                x--; 
                y--;
            } else if (dir[x][y].equals("u")) {
                x--;  
            } else {
                y--;
            }
        }
        
        // reverse the string we found, to be in the correct orientation
        String temp = lcs; 
        lcs = ""; 
        for (int i = temp.length() - 1; i >= 0; i--) {
            lcs += temp.charAt(i);
        }

        return lcs;
    }
}
