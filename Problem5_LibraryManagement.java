/** Problem 5: Doubly Linked List - Library Management System. */
public class Problem5_LibraryManagement {

    static class Book {
        String title, author, genre; int id; boolean available; Book next, prev;
        Book(int id, String title, String author, String genre, boolean available) {
            this.id = id; this.title = title; this.author = author; this.genre = genre; this.available = available;
        }
        public String toString() { return String.format("ID=%d | %s | %s | %s | %s", id, title, author, genre, available ? "Available" : "Issued"); }
    }

    static class Library {
        Book head, tail; int count;

        void addFirst(Book b) { if (head == null) head = tail = b; else { b.next = head; head.prev = b; head = b; } count++; }
        void addLast(Book b)  { if (tail == null) head = tail = b; else { tail.next = b; b.prev = tail; tail = b; } count++; }
        boolean addAt(int pos, Book b) {
            if (pos < 1 || pos > count + 1) return false;
            if (pos == 1) addFirst(b); else if (pos == count + 1) addLast(b);
            else {
                Book t = head; for (int i = 1; i < pos - 1; i++) t = t.next;
                b.next = t.next; b.prev = t; t.next.prev = b; t.next = b; count++;
            }
            return true;
        }
        boolean remove(int id) {
            for (Book t = head; t != null; t = t.next) if (t.id == id) {
                if (t.prev != null) t.prev.next = t.next; else head = t.next;
                if (t.next != null) t.next.prev = t.prev; else tail = t.prev;
                t.next = t.prev = null; count--; return true;
            }
            return false;
        }
        void searchByTitle(String s) { boolean f = false; for (Book t = head; t != null; t = t.next) if (t.title.equalsIgnoreCase(s)) { System.out.println("  " + t); f = true; } if (!f) System.out.println("  none found"); }
        void searchByAuthor(String s) { boolean f = false; for (Book t = head; t != null; t = t.next) if (t.author.equalsIgnoreCase(s)) { System.out.println("  " + t); f = true; } if (!f) System.out.println("  none found"); }
        boolean updateStatus(int id, boolean status) { for (Book t = head; t != null; t = t.next) if (t.id == id) { t.available = status; return true; } return false; }
        void displayForward() { System.out.println("Forward:"); for (Book t = head; t != null; t = t.next) System.out.println("  " + t); }
        void displayReverse() { System.out.println("Reverse:"); for (Book t = tail; t != null; t = t.prev) System.out.println("  " + t); }
        int countBooks() { return count; }
    }

    public static void main(String[] args) {
        Library lib = new Library();
        lib.addLast(new Book(1, "Clean Code", "Robert Martin", "Programming", true));
        lib.addLast(new Book(2, "The Pragmatic Programmer", "Andrew Hunt", "Programming", true));
        lib.addFirst(new Book(3, "Wings of Fire", "A.P.J. Abdul Kalam", "Biography", true));
        lib.addAt(2, new Book(4, "Refactoring", "Martin Fowler", "Programming", false));
        lib.displayForward(); lib.displayReverse();
        System.out.println("Total books: " + lib.countBooks());

        System.out.println("\nSearch title 'Clean Code':"); lib.searchByTitle("Clean Code");
        System.out.println("Search author 'Martin Fowler':"); lib.searchByAuthor("Martin Fowler");

        lib.updateStatus(4, true); lib.updateStatus(1, false);
        System.out.println("\nRemove 3 (head): " + lib.remove(3) + ", remove 2 (tail): " + lib.remove(2) + ", remove 77: " + lib.remove(77));
        lib.displayForward(); lib.displayReverse();
        System.out.println("Total books: " + lib.countBooks());
    }
}
