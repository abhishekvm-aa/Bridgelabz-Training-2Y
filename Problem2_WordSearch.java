import java.util.*;

/**
 * Problem 2: Word search in 4x4 grid (8 directions, each cell used once) - Backtracking.
 *
 * a) State      : (row, col, index in word) + visited[][] + current path
 *    Choices    : 8 neighbouring cells
 *    Constraints: inside grid, not visited, grid[r][c] == word[index]
 *    Goal       : index == word.length()
 * d) Time  O(N*M * 8 * 7^(L-1)) ~ O(N*M*8^L) worst case. Space O(L) recursion + O(N*M) visited.
 * e) To find ALL ways: don't return early; keep a 'path' list and store a copy in a
 *    List<List<int[]>> whenever the goal is reached.
 */
public class Problem2_WordSearch {

    static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1};
    static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};

    static char[][] grid = {
        {'C','A','T','S'},
        {'O','R','E','A'},
        {'D','E','A','M'},
        {'E','L','L','S'}
    };

    // ---------- (a) existence check ----------
    static boolean exists(String word) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[0].length; c++)
                if (search(word, 0, r, c, visited)) return true;
        return false;
    }

    static boolean search(String w, int idx, int r, int c, boolean[][] vis) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) return false; // out of grid
        if (vis[r][c] || grid[r][c] != w.charAt(idx)) return false;                  // constraint
        if (idx == w.length() - 1) return true;                                      // GOAL

        vis[r][c] = true;                                  // make choice
        for (int d = 0; d < 8; d++)
            if (search(w, idx + 1, r + DR[d], c + DC[d], vis)) { vis[r][c] = false; return true; }
        vis[r][c] = false;                                 // undo choice (backtrack)
        return false;
    }

    // ---------- (b),(c) traced version: prints decision tree ----------
    static boolean searchTraced(String w, int idx, int r, int c, boolean[][] vis, int depth) {
        String pad = "  ".repeat(depth);
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) return false;
        if (vis[r][c]) return false;
        if (grid[r][c] != w.charAt(idx)) {
            System.out.println(pad + "(" + r + "," + c + ") '" + grid[r][c] + "' != '" + w.charAt(idx) + "'  X prune");
            return false;
        }
        System.out.println(pad + "(" + r + "," + c + ") '" + grid[r][c] + "' matches  [choose]");
        if (idx == w.length() - 1) { System.out.println(pad + "GOAL reached  OK"); return true; }

        vis[r][c] = true;
        for (int d = 0; d < 8; d++)
            if (searchTraced(w, idx + 1, r + DR[d], c + DC[d], vis, depth + 1)) return true;
        vis[r][c] = false;
        System.out.println(pad + "(" + r + "," + c + ") dead end  <- BACKTRACK (unmark)");
        return false;
    }

    // ---------- (e) find ALL paths ----------
    static void findAll(String w, int idx, int r, int c, boolean[][] vis,
                        List<int[]> path, List<List<int[]>> out) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) return;
        if (vis[r][c] || grid[r][c] != w.charAt(idx)) return;

        vis[r][c] = true; path.add(new int[]{r, c});
        if (idx == w.length() - 1) out.add(new ArrayList<>(path));   // save copy, keep searching
        else for (int d = 0; d < 8; d++) findAll(w, idx + 1, r + DR[d], c + DC[d], vis, path, out);
        path.remove(path.size() - 1); vis[r][c] = false;             // backtrack
    }

    public static void main(String[] args) {
        String word = "DREAM";
        System.out.println("exists(\"" + word + "\") = " + exists(word));

        System.out.println("\n=== (b)/(c) Decision tree trace (first start cell matching 'D') ===");
        boolean[][] vis = new boolean[4][4];
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                if (grid[r][c] == 'D' && searchTraced(word, 0, r, c, vis, 0)) { r = c = 99; }

        System.out.println("\n=== (e) All paths ===");
        List<List<int[]>> all = new ArrayList<>();
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                findAll(word, 0, r, c, new boolean[4][4], new ArrayList<>(), all);
        System.out.println("Total ways = " + all.size());
        for (List<int[]> p : all) {
            StringBuilder sb = new StringBuilder();
            for (int[] x : p) sb.append("(").append(x[0]).append(",").append(x[1]).append(") ");
            System.out.println(sb);
        }
    }
}
