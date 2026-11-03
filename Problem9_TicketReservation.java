/** Problem 9: Circular Linked List - Online Ticket Reservation System (tail pointer, head = tail.next). */
public class Problem9_TicketReservation {

    static class Ticket {
        int id, seat; String customer, movie, time; Ticket next;
        Ticket(int id, String customer, String movie, int seat, String time) { this.id = id; this.customer = customer; this.movie = movie; this.seat = seat; this.time = time; }
        public String toString() { return String.format("Ticket#%d | %s | %s | Seat %d | %s", id, customer, movie, seat, time); }
    }

    static class Booking {
        Ticket tail; int count;

        void add(Ticket t) {                                   // add at end
            if (tail == null) { tail = t; t.next = t; }
            else { t.next = tail.next; tail.next = t; tail = t; }
            count++;
        }

        boolean remove(int id) {
            if (tail == null) return false;
            Ticket prev = tail, cur = tail.next;
            for (int i = 0; i < count; i++) {
                if (cur.id == id) {
                    if (count == 1) tail = null;
                    else { prev.next = cur.next; if (cur == tail) tail = prev; }
                    cur.next = null; count--; return true;
                }
                prev = cur; cur = cur.next;
            }
            return false;
        }

        void display() {
            if (tail == null) { System.out.println("  (no tickets)"); return; }
            Ticket t = tail.next;
            do { System.out.println("  " + t); t = t.next; } while (t != tail.next);
        }

        void searchByCustomer(String c) { search(c, true); }
        void searchByMovie(String m) { search(m, false); }
        private void search(String key, boolean byCustomer) {
            boolean found = false;
            if (tail != null) {
                Ticket t = tail.next;
                do {
                    if ((byCustomer ? t.customer : t.movie).equalsIgnoreCase(key)) { System.out.println("  " + t); found = true; }
                    t = t.next;
                } while (t != tail.next);
            }
            if (!found) System.out.println("  none found");
        }

        int total() { return count; }
    }

    public static void main(String[] args) {
        Booking b = new Booking();
        b.add(new Ticket(1, "Asha", "Inception", 12, "2026-11-03 10:15"));
        b.add(new Ticket(2, "Ravi", "Dangal", 5, "2026-11-03 10:20"));
        b.add(new Ticket(3, "Asha", "Dangal", 6, "2026-11-03 10:22"));
        b.add(new Ticket(4, "Meera", "Inception", 13, "2026-11-03 11:00"));
        System.out.println("Tickets:"); b.display();
        System.out.println("Total booked: " + b.total());

        System.out.println("\nSearch customer 'Asha':"); b.searchByCustomer("Asha");
        System.out.println("Search movie 'Inception':"); b.searchByMovie("Inception");

        System.out.println("\nRemove 1 (head): " + b.remove(1) + ", remove 4 (tail): " + b.remove(4) + ", remove 42: " + b.remove(42));
        b.display();
        System.out.println("Total booked: " + b.total());
    }
}
