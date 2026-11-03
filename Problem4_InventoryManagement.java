import java.util.Comparator;

/** Problem 4: Singly Linked List - Inventory Management (merge sort on list). */
public class Problem4_InventoryManagement {

    static class Item {
        String name; int id, qty; double price; Item next;
        Item(String name, int id, int qty, double price) { this.name = name; this.id = id; this.qty = qty; this.price = price; }
        public String toString() { return String.format("ID=%d | %s | Qty=%d | Price=%.2f", id, name, qty, price); }
    }

    static class Inventory {
        Item head; int size;

        void addFirst(Item i) { i.next = head; head = i; size++; }
        void addLast(Item i) {
            if (head == null) head = i; else { Item t = head; while (t.next != null) t = t.next; t.next = i; }
            size++;
        }
        boolean addAt(int pos, Item i) {
            if (pos < 1 || pos > size + 1) return false;
            if (pos == 1) { addFirst(i); return true; }
            Item t = head; for (int k = 1; k < pos - 1; k++) t = t.next;
            i.next = t.next; t.next = i; size++; return true;
        }
        boolean remove(int id) {
            if (head == null) return false;
            if (head.id == id) { head = head.next; size--; return true; }
            Item t = head; while (t.next != null && t.next.id != id) t = t.next;
            if (t.next == null) return false;
            t.next = t.next.next; size--; return true;
        }
        boolean updateQty(int id, int q) { Item i = searchById(id); if (i == null) return false; i.qty = q; return true; }
        Item searchById(int id) { for (Item t = head; t != null; t = t.next) if (t.id == id) return t; return null; }
        Item searchByName(String n) { for (Item t = head; t != null; t = t.next) if (t.name.equalsIgnoreCase(n)) return t; return null; }

        double totalValue() { double sum = 0; for (Item t = head; t != null; t = t.next) sum += t.price * t.qty; return sum; }

        /** field: "name" or "price"; asc=false for descending */
        void sort(String field, boolean asc) {
            Comparator<Item> c = field.equalsIgnoreCase("name")
                    ? Comparator.comparing((Item i) -> i.name.toLowerCase())
                    : Comparator.comparingDouble((Item i) -> i.price);
            if (!asc) c = c.reversed();
            head = mergeSort(head, c);
        }

        private Item mergeSort(Item h, Comparator<Item> c) {
            if (h == null || h.next == null) return h;
            Item slow = h, fast = h.next;                                  // find middle
            while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
            Item right = slow.next; slow.next = null;
            return merge(mergeSort(h, c), mergeSort(right, c), c);
        }

        private Item merge(Item a, Item b, Comparator<Item> c) {
            Item dummy = new Item("", 0, 0, 0), t = dummy;
            while (a != null && b != null) {
                if (c.compare(a, b) <= 0) { t.next = a; a = a.next; } else { t.next = b; b = b.next; }
                t = t.next;
            }
            t.next = (a != null) ? a : b;
            return dummy.next;
        }

        void display() { if (head == null) System.out.println("  (empty)"); for (Item t = head; t != null; t = t.next) System.out.println("  " + t); }
    }

    public static void main(String[] args) {
        Inventory inv = new Inventory();
        inv.addLast(new Item("Keyboard", 101, 10, 1200.50));
        inv.addLast(new Item("Mouse", 102, 25, 450));
        inv.addFirst(new Item("Monitor", 103, 5, 9800));
        inv.addAt(2, new Item("Cable", 104, 100, 80));
        System.out.println("Inventory:"); inv.display();

        System.out.println("\nSearch ID 102: " + inv.searchById(102));
        System.out.println("Search name 'monitor': " + inv.searchByName("monitor"));
        inv.updateQty(102, 30);
        System.out.printf("Total value: %.2f%n", inv.totalValue());

        inv.sort("name", true);   System.out.println("\nSorted by name ASC:");   inv.display();
        inv.sort("name", false);  System.out.println("\nSorted by name DESC:");  inv.display();
        inv.sort("price", true);  System.out.println("\nSorted by price ASC:");  inv.display();
        inv.sort("price", false); System.out.println("\nSorted by price DESC:"); inv.display();

        System.out.println("\nRemove 103: " + inv.remove(103) + ", remove 999: " + inv.remove(999));
        inv.display();
    }
}
