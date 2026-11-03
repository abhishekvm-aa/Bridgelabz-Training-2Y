import java.util.*;

/**
 * Problem 3: N-Queens - optimized backtracking + variants.
 *
 * (b) Optimization: board[row] = col (1D) and boolean arrays for columns / diagonals
 *     -> isSafe becomes O(1) instead of O(N).
 *       left  diagonal id = row - col + (n-1)
 *       right diagonal id = row + col
 * (d) First solution only: make the recursive function return boolean and stop at first true.
 * (e) Forbidden squares: skip forbidden[row][col]. Worst case still O(N!), but search space
 *     shrinks (fewer choices per row); a row with no allowed squares prunes instantly.
 */
public class Problem3_NQueens {

    static int nodes = 0, solutions = 0;

    // ---------- (b) optimized: all solutions ----------
    static void solveAll(int row, int n, int[] board, boolean[] col, boolean[] d1, boolean[] d2) {
        nodes++;
        if (row == n) { solutions++; return; }
        for (int c = 0; c < n; c++) {
            if (col[c] || d1[row - c + n - 1] || d2[row + c]) continue;   // O(1) check
            board[row] = c; col[c] = d1[row - c + n - 1] = d2[row + c] = true;   // choose
            solveAll(row + 1, n, board, col, d1, d2);
            col[c] = d1[row - c + n - 1] = d2[row + c] = false;                  // un-choose
        }
    }

    // ---------- (c) traced version for N=4 (first solution) ----------
    static boolean solveTraced(int row, int n, int[] board, boolean[] col, boolean[] d1, boolean[] d2) {
        if (row == n) return true;
        for (int c = 0; c < n; c++) {
            if (col[c] || d1[row - c + n - 1] || d2[row + c]) {
                System.out.println("row " + row + " col " + c + " -> unsafe");
                continue;
            }
            board[row] = c; col[c] = d1[row - c + n - 1] = d2[row + c] = true;
            System.out.println("row " + row + " col " + c + " -> PLACE   " + state(board, row, col, d1, d2));
            if (solveTraced(row + 1, n, board, col, d1, d2)) return true;      // (d) stop at first
            col[c] = d1[row - c + n - 1] = d2[row + c] = false;
            board[row] = -1;
            System.out.println("row " + row + " col " + c + " -> BACKTRACK " + state(board, row - 1, col, d1, d2));
        }
        System.out.println("row " + row + " has no safe column  (dead end)");
        return false;
    }

    static String state(int[] b, int upto, boolean[] col, boolean[] d1, boolean[] d2) {
        return "board=" + Arrays.toString(b) + " col=" + bits(col) + " d1=" + bits(d1) + " d2=" + bits(d2);
    }
    static String bits(boolean[] a) { StringBuilder s = new StringBuilder(); for (boolean x : a) s.append(x ? 1 : 0); return s.toString(); }

    // ---------- (d) first solution only (fast) ----------
    static boolean solveFirst(int row, int n, int[] board, boolean[] col, boolean[] d1, boolean[] d2) {
        if (row == n) return true;
        for (int c = 0; c < n; c++) {
            if (col[c] || d1[row - c + n - 1] || d2[row + c]) continue;
            board[row] = c; col[c] = d1[row - c + n - 1] = d2[row + c] = true;
            if (solveFirst(row + 1, n, board, col, d1, d2)) return true;
            col[c] = d1[row - c + n - 1] = d2[row + c] = false;
        }
        return false;
    }

    // ---------- (e) forbidden squares ----------
    static int solveForbidden(int row, int n, boolean[][] forb, boolean[] col, boolean[] d1, boolean[] d2) {
        if (row == n) return 1;
        int count = 0;
        for (int c = 0; c < n; c++) {
            if (forb[row][c] || col[c] || d1[row - c + n - 1] || d2[row + c]) continue;
            col[c] = d1[row - c + n - 1] = d2[row + c] = true;
            count += solveForbidden(row + 1, n, forb, col, d1, d2);
            col[c] = d1[row - c + n - 1] = d2[row + c] = false;
        }
        return count;
    }

    static void print(int[] board) {
        for (int r : board) { StringBuilder s = new StringBuilder(); for (int c = 0; c < board.length; c++) s.append(c == r ? "Q " : ". "); System.out.println(s); }
    }

    public static void main(String[] args) {
        // (a) search space for N=8
        long bf = 1; for (int i = 0; i < 8; i++) bf *= 8;                 // 8^8 (as in lecture)
        long perm = 1; for (int i = 2; i <= 8; i++) perm *= i;            // 8!
        long choose = 1; for (int i = 0; i < 8; i++) choose = choose * (64 - i) / (i + 1);  // C(64,8)
        System.out.println("(a) Brute force N^N      = " + bf);
        System.out.println("    Place 8 of 64 squares = " + choose);
        System.out.println("    One queen/row + col  = 8! = " + perm);
        int n = 8;
        nodes = 0; solutions = 0;
        solveAll(0, n, new int[n], new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1]);
        System.out.println("    Backtracking explores = " + nodes + " nodes, finds " + solutions + " solutions");

        // (c) trace N=4
        System.out.println("\n(c) Trace N=4");
        n = 4;
        int[] b = new int[n]; Arrays.fill(b, -1);
        boolean ok = solveTraced(0, n, b, new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1]);
        System.out.println("Solution found = " + ok + " -> " + Arrays.toString(b));
        print(b);

        // (d) first solution, N=8
        n = 8; b = new int[n];
        solveFirst(0, n, b, new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1]);
        System.out.println("\n(d) First 8-Queens solution: " + Arrays.toString(b));
        print(b);

        // (e) forbidden squares, N=6
        n = 6;
        boolean[][] forb = new boolean[n][n];
        forb[0][1] = forb[1][3] = forb[2][5] = forb[3][0] = true;
        int cnt = solveForbidden(0, n, forb, new boolean[n], new boolean[2 * n - 1], new boolean[2 * n - 1]);
        System.out.println("\n(e) N=6 with 4 forbidden squares -> " + cnt + " solutions (6-Queens normally has 4)");
    }
}
