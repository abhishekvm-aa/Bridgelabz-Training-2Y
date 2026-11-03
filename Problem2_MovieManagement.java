/** Problem 2: Doubly Linked List - Movie Management System (head + tail maintained). */
public class Problem2_MovieManagement {

    static class Movie {
        String title, director; int year; double rating; Movie next, prev;
        Movie(String t, String d, int y, double r) { title = t; director = d; year = y; rating = r; }
        public String toString() { return String.format("%s | %s | %d | %.1f", title, director, year, rating); }
    }

    static class MovieList {
        Movie head, tail; int size;

        void addFirst(Movie m) {
            if (head == null) head = tail = m;
            else { m.next = head; head.prev = m; head = m; }
            size++;
        }

        void addLast(Movie m) {
            if (tail == null) head = tail = m;
            else { tail.next = m; m.prev = tail; tail = m; }
            size++;
        }

        boolean addAt(int pos, Movie m) {                    // 1-based
            if (pos < 1 || pos > size + 1) return false;
            if (pos == 1) addFirst(m);
            else if (pos == size + 1) addLast(m);
            else {
                Movie t = head;
                for (int i = 1; i < pos - 1; i++) t = t.next;
                m.next = t.next; m.prev = t; t.next.prev = m; t.next = m; size++;
            }
            return true;
        }

        boolean removeByTitle(String title) {
            for (Movie t = head; t != null; t = t.next) {
                if (t.title.equalsIgnoreCase(title)) {
                    if (t.prev != null) t.prev.next = t.next; else head = t.next;
                    if (t.next != null) t.next.prev = t.prev; else tail = t.prev;
                    t.next = t.prev = null; size--;
                    return true;
                }
            }
            return false;
        }

        void searchByDirector(String d) {
            boolean found = false;
            for (Movie t = head; t != null; t = t.next) if (t.director.equalsIgnoreCase(d)) { System.out.println("  " + t); found = true; }
            if (!found) System.out.println("  none found");
        }

        void searchByRating(double r) {
            boolean found = false;
            for (Movie t = head; t != null; t = t.next) if (t.rating == r) { System.out.println("  " + t); found = true; }
            if (!found) System.out.println("  none found");
        }

        boolean updateRating(String title, double r) {
            for (Movie t = head; t != null; t = t.next) if (t.title.equalsIgnoreCase(title)) { t.rating = r; return true; }
            return false;
        }

        void displayForward() { System.out.println("Forward:");  for (Movie t = head; t != null; t = t.next) System.out.println("  " + t); }
        void displayReverse() { System.out.println("Reverse:");  for (Movie t = tail; t != null; t = t.prev) System.out.println("  " + t); }
    }

    public static void main(String[] args) {
        MovieList l = new MovieList();
        l.addLast(new Movie("Inception", "Christopher Nolan", 2010, 8.8));
        l.addFirst(new Movie("Dangal", "Nitesh Tiwari", 2016, 8.3));
        l.addLast(new Movie("Interstellar", "Christopher Nolan", 2014, 8.7));
        l.addAt(2, new Movie("3 Idiots", "Rajkumar Hirani", 2009, 8.4));
        l.displayForward(); l.displayReverse();

        System.out.println("\nSearch director Christopher Nolan:"); l.searchByDirector("Christopher Nolan");
        System.out.println("Search rating 8.4:"); l.searchByRating(8.4);

        l.updateRating("Dangal", 8.6);
        System.out.println("\nRemove 'Dangal' (head): " + l.removeByTitle("Dangal"));
        System.out.println("Remove 'Interstellar' (tail): " + l.removeByTitle("Interstellar"));
        System.out.println("Remove 'Nope': " + l.removeByTitle("Nope"));
        l.displayForward(); l.displayReverse();
    }
}
