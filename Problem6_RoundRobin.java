import java.util.ArrayList;
import java.util.List;

/** Problem 6: Circular Linked List - Round Robin CPU Scheduling (all processes arrive at t=0). */
public class Problem6_RoundRobin {

    static class Proc {
        int pid, burst, priority, remaining, waiting, turnaround; Proc next;
        Proc(int pid, int burst, int priority) { this.pid = pid; this.burst = burst; this.priority = priority; this.remaining = burst; }
        public String toString() { return "P" + pid + "(rem=" + remaining + ")"; }
    }

    static class Scheduler {
        Proc tail; int size; int time = 0;
        List<Proc> finished = new ArrayList<>();

        void add(Proc p) {                                    // add at END of circular list
            if (tail == null) { tail = p; p.next = p; }
            else { p.next = tail.next; tail.next = p; tail = p; }
            size++;
        }

        void display() {
            if (tail == null) { System.out.println("  Queue: (empty)"); return; }
            StringBuilder sb = new StringBuilder("  Queue: "); Proc t = tail.next;
            do { sb.append(t).append(" -> "); t = t.next; } while (t != tail.next);
            System.out.println(sb.append("(back to head)"));
        }

        void simulate(int quantum) {
            int round = 1;
            while (size > 0) {
                System.out.println("Round " + round++ + ":");
                Proc prev = tail, cur = tail.next;
                int inRound = size;
                for (int i = 0; i < inRound && size > 0; i++) {
                    int run = Math.min(quantum, cur.remaining);
                    System.out.printf("  t=%d: P%d runs %d%n", time, cur.pid, run);
                    time += run; cur.remaining -= run;
                    if (cur.remaining == 0) {                 // finished -> remove from circle
                        cur.turnaround = time; cur.waiting = time - cur.burst; finished.add(cur);
                        Proc nxt = cur.next;
                        if (size == 1) { tail = null; }
                        else { prev.next = nxt; if (cur == tail) tail = prev; }
                        size--; cur = nxt;
                        System.out.println("    -> P" + finished.get(finished.size() - 1).pid + " completed at t=" + time);
                    } else { prev = cur; cur = cur.next; }
                }
                display();
            }
        }

        void stats() {
            double w = 0, t = 0;
            System.out.println("\nPID  Burst  Waiting  Turnaround");
            for (Proc p : finished) { System.out.printf("P%-3d %-6d %-8d %d%n", p.pid, p.burst, p.waiting, p.turnaround); w += p.waiting; t += p.turnaround; }
            System.out.printf("Average waiting time    = %.2f%n", w / finished.size());
            System.out.printf("Average turnaround time = %.2f%n", t / finished.size());
        }
    }

    public static void main(String[] args) {
        Scheduler s = new Scheduler();
        s.add(new Proc(1, 10, 2)); s.add(new Proc(2, 5, 1)); s.add(new Proc(3, 8, 3));
        System.out.println("Initial:"); s.display();
        int quantum = 3;
        System.out.println("\nTime quantum = " + quantum);
        s.simulate(quantum);
        s.stats();
    }
}
