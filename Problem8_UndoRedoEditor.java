/** Problem 8: Doubly Linked List - Undo/Redo for a text editor, history limited to a fixed size. */
public class Problem8_UndoRedoEditor {

    static class State { String text; State next, prev; State(String t) { text = t; } }

    static class Editor {
        State head, tail, current; int size, limit;

        Editor(int limit) { this.limit = limit; perform(""); }          // initial empty state

        /** New action: drop any redo states, append new state, trim to 'limit'. */
        void perform(String newText) {
            State s = new State(newText);
            if (current == null) { head = tail = current = s; size = 1; return; }
            for (State r = current.next; r != null; r = r.next) size--;  // discard redo branch
            current.next = s; s.prev = current; tail = s; current = s; size++;
            while (size > limit) { head = head.next; head.prev = null; size--; }
        }

        void type(String more) { perform(current.text + more); }

        void undo() {
            if (current.prev == null) { System.out.println("  Nothing to undo"); return; }
            current = current.prev;
        }
        void redo() {
            if (current.next == null) { System.out.println("  Nothing to redo"); return; }
            current = current.next;
        }
        void showCurrent() { System.out.println("  Current text: \"" + current.text + "\"   (history size=" + size + ")"); }
        void showHistory() {
            StringBuilder sb = new StringBuilder("  History: ");
            for (State t = head; t != null; t = t.next) sb.append(t == current ? "[" + t.text + "]" : t.text).append(" | ");
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        Editor e = new Editor(5);   // small limit so trimming is visible (spec example: 10)
        e.type("Hello"); e.type(" World"); e.type("!");
        e.showCurrent(); e.showHistory();

        System.out.println("\nUndo x2:"); e.undo(); e.undo(); e.showCurrent();
        System.out.println("Redo x1:"); e.redo(); e.showCurrent();

        System.out.println("\nNew action after undo clears redo branch:");
        e.undo(); e.type(" Java"); e.showCurrent(); e.redo(); e.showHistory();

        System.out.println("\nExceed limit (5 states):");
        e.type("1"); e.type("2"); e.type("3"); e.showCurrent(); e.showHistory();
        for (int i = 0; i < 6; i++) e.undo();
        e.showCurrent();
    }
}
