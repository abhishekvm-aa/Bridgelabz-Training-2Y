/** Problem 3: Circular Linked List - Task Scheduler. Only 'tail' is kept; head = tail.next. */
public class Problem3_TaskScheduler {

    static class Task {
        int id, priority; String name, dueDate; Task next;
        Task(int id, String name, int priority, String dueDate) { this.id = id; this.name = name; this.priority = priority; this.dueDate = dueDate; }
        public String toString() { return String.format("Task#%d | %s | Priority=%d | Due=%s", id, name, priority, dueDate); }
    }

    static class TaskList {
        Task tail, current; int size;

        void addFirst(Task n) {
            if (tail == null) { tail = n; n.next = n; current = n; }
            else { n.next = tail.next; tail.next = n; }
            size++;
        }

        void addLast(Task n) { addFirst(n); if (size > 1) tail = n; }   // insert after tail, then move tail

        boolean addAt(int pos, Task n) {                                 // 1-based
            if (pos < 1 || pos > size + 1) return false;
            if (pos == 1) addFirst(n);
            else if (pos == size + 1) addLast(n);
            else {
                Task t = tail.next;
                for (int i = 1; i < pos - 1; i++) t = t.next;
                n.next = t.next; t.next = n; size++;
            }
            return true;
        }

        boolean remove(int id) {
            if (tail == null) return false;
            Task prev = tail, cur = tail.next;
            for (int i = 0; i < size; i++) {                              // at most one full cycle
                if (cur.id == id) {
                    if (size == 1) { tail = null; current = null; }
                    else {
                        if (cur == current) current = cur.next;
                        prev.next = cur.next;
                        if (cur == tail) tail = prev;
                    }
                    cur.next = null; size--;
                    return true;
                }
                prev = cur; cur = cur.next;
            }
            return false;
        }

        void viewCurrentAndMoveNext() {
            if (current == null) { System.out.println("No tasks"); return; }
            System.out.println("Current: " + current);
            current = current.next;
        }

        void display() {
            if (tail == null) { System.out.println("(empty)"); return; }
            Task t = tail.next;
            do { System.out.println("  " + t); t = t.next; } while (t != tail.next);   // stop after one cycle
        }

        void searchByPriority(int p) {
            if (tail == null) return;
            boolean found = false; Task t = tail.next;
            do { if (t.priority == p) { System.out.println("  " + t); found = true; } t = t.next; } while (t != tail.next);
            if (!found) System.out.println("  none found");
        }
    }

    public static void main(String[] args) {
        TaskList l = new TaskList();
        l.addLast(new Task(1, "Write report", 2, "2026-11-10"));
        l.addLast(new Task(2, "Code review", 1, "2026-11-08"));
        l.addFirst(new Task(3, "Team standup", 3, "2026-11-04"));
        l.addAt(3, new Task(4, "Deploy build", 1, "2026-11-12"));
        System.out.println("All tasks:"); l.display();

        System.out.println("\nCycling through tasks (wraps around):");
        for (int i = 0; i < 6; i++) l.viewCurrentAndMoveNext();

        System.out.println("\nSearch priority 1:"); l.searchByPriority(1);
        System.out.println("\nRemove 3 (head): " + l.remove(3));
        System.out.println("Remove 1 (tail): " + l.remove(1));
        System.out.println("Remove 99: " + l.remove(99));
        l.display();
        l.viewCurrentAndMoveNext();
    }
}
