import java.util.*;

/**
 * Problem 1: Recursive directory size calculator.
 *
 * a) Base case      : a plain file -> return its own size.
 *    Recursive case : a directory -> sum of size(child) for every child.
 * c) Time  O(F + D)  (visit every file/dir once). Space O(H) call stack, H = tree height.
 * d) Iterative version uses an explicit Stack (see sizeIterative). Same time, space O(F+D) worst case,
 *    no StackOverflow risk, but code is less natural.
 * e) Symlink cycle -> extra base case: if directory already visited, return 0.
 */
public class Problem1_DirectorySize {

    static class Node {
        String name; long size; boolean isDir; Node linkTarget;
        List<Node> children = new ArrayList<>();
        Node(String name, long size, boolean isDir) { this.name = name; this.size = size; this.isDir = isDir; }
        Node add(Node c) { children.add(c); return this; }
    }

    static Node file(String n, long kb) { return new Node(n, kb, false); }
    static Node dir(String n) { return new Node(n, 0, true); }
    static Node link(String n, Node target) { Node l = new Node(n, 0, false); l.linkTarget = target; return l; }

    // ---------- (a) basic recursive version ----------
    static long size(Node n) {
        if (!n.isDir) return n.size;                 // BASE CASE
        long total = 0;
        for (Node c : n.children) total += size(c);  // RECURSIVE CASE
        return total;
    }

    // ---------- (b) traced version: prints call stack ----------
    static Deque<String> stack = new ArrayDeque<>();

    static long sizeTraced(Node n) {
        stack.addLast(n.name);
        System.out.println("CALL   " + "  ".repeat(stack.size() - 1) + n.name + "   stack=" + stack);
        long total;
        if (!n.isDir) {
            total = n.size;
        } else {
            total = 0;
            for (Node c : n.children) total += sizeTraced(c);
        }
        System.out.println("RETURN " + "  ".repeat(stack.size() - 1) + n.name + " -> " + total + " KB");
        stack.removeLast();
        return total;
    }

    // ---------- (d) iterative version ----------
    static long sizeIterative(Node root) {
        long total = 0;
        Deque<Node> st = new ArrayDeque<>();
        st.push(root);
        while (!st.isEmpty()) {
            Node cur = st.pop();
            if (!cur.isDir) total += cur.size;
            else for (Node c : cur.children) st.push(c);
        }
        return total;
    }

    // ---------- (e) cycle-safe version (symlinks) ----------
    static long sizeSafe(Node n, Set<Node> visited) {
        if (n.linkTarget != null) n = n.linkTarget;      // follow symlink
        if (!n.isDir) return n.size;                     // BASE CASE 1: file
        if (!visited.add(n)) return 0;                   // BASE CASE 2: already visited -> cycle / double count
        long total = 0;
        for (Node c : n.children) total += sizeSafe(c, visited);
        return total;
    }

    public static void main(String[] args) {
        Node project = dir("project");
        Node src = dir("src").add(file("main.java", 100)).add(file("utils.java", 50));
        Node guides = dir("guides").add(file("setup.pdf", 200));
        Node docs = dir("docs").add(file("readme.txt", 10)).add(guides);
        project.add(src).add(docs).add(file("config.xml", 20));

        System.out.println("=== (b) Trace ===");
        long t = sizeTraced(project);
        System.out.println("\nTotal (recursive) = " + t + " KB");
        System.out.println("Total (iterative) = " + sizeIterative(project) + " KB");

        // (e) create cycle: guides/loop -> project
        guides.add(link("loop(symlink->project)", project));
        System.out.println("\n=== (e) With symlink cycle ===");
        System.out.println("Cycle-safe total  = " + sizeSafe(project, new HashSet<>()) + " KB");
        System.out.println("(Plain size() would throw StackOverflowError here.)");
    }
}
