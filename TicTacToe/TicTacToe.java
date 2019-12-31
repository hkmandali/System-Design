package SystemDesign.TicTacToe;

import java.util.*;


public class TicTacToe {
    static char champ;

    private static class Matrix {
        char[][] graph;
        int n;

        Matrix(int n) {
            this.n = n;
            champ = '-'; // this stores the current champion else -
            graph = new char[n][n];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    graph[i][j] = '-'; // default value , accepted values from input are only x and o's
                }
            }
        }

        int getN() {
            return n;
        }

        void getCurrentState() {
            System.out.println("the current state of the matrix is ");
            ;
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    System.out.print(graph[i][j] + " ");
                }
                System.out.println("\n" + "-------------------");
            }
            return;
        }

        /*
            this function updates the matrix with step provided by user and throws exception if any issue
         */
        void step(char symbol, int row, int column) throws IllegalArgumentException {
            if (row > n || column > n || graph[row][column] != '-' || (symbol != 'x' && symbol != 'o')) {
                throw new IllegalArgumentException("symbol is not entered properly or row or column is greater than n or that cell is already occupied");
            }
            if (champ != '-') {
                System.out.println("game is already decided and the winner is " + champ);
            } else {
                graph[row][column] = symbol;
                switch (winner(symbol, row, column)) {
                    case 'x':
                        System.out.println("winner is x");
                        break;
                    case 'o':
                        System.out.println("winner is o");
                        break;
                    case '-':
                        System.out.println("game is still intact");
                        break;
                }
            }
            return;
        }

        /* this function returns the winner based on the step made , if
            x--> winner is x
            o --> winner is o
            - --> game is still intact
         */
        char winner(char symbol, int row, int col) // used to verify in O(n) time , else we need to parse n x n
        {
            boolean diag = true, revdiag = true, hor = true, ver = true;
            for (int i = 0; i < n; ++i) {
                if (graph[i][i] != symbol) // diagonal
                    diag = false;
                if (graph[i][n - i - 1] != symbol) // reverse diagonal
                    revdiag = false;
                if (graph[row][i] != symbol) // same row
                    hor = false;
                if (graph[i][col] != symbol) // same column
                    ver = false;
            }
            if (diag || revdiag || hor || ver) // this verifies if there is either of horizontal or vertical or diagonal or reverse diagonal
            {
                champ = symbol;
                return symbol;
            }
            return '-';
        }

    }
    /*
        This function is used to update the matrix with x and o's and returns if there is any winner at that step
       return: either x or o
     */


    public static void main(String[] args) {
        System.out.println("finally resolved all the build issues :)");
        Matrix a = new Matrix(3);
        int n = a.getN(); // dimensions of matrix
        int i = 0;
        Scanner sc = new Scanner(System.in);
        while (champ == '-' && i < n*n) {
            System.out.println("enter the sysmcol and row and col you want to insert to");
            char sym = sc.next().charAt(0);
            int row = sc.nextInt();
            int col = sc.nextInt();
            a.step(sym, row, col);
            i++; // to check if the input is at max with num of cells
        }
        if (champ == '-') {
            System.out.println("game is a tie");
        }
        return;
    }
}
