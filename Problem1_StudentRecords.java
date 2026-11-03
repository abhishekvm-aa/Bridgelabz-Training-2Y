/** Problem 1: Singly Linked List - Student Record Management. Positions are 1-based. */
public class Problem1_StudentRecords {

    static class Student {
        int roll, age; String name, grade; Student next;
        Student(int roll, String name, int age, String grade) { this.roll = roll; this.name = name; this.age = age; this.grade = grade; }
        public String toString() { return String.format("Roll=%d | %s | Age=%d | Grade=%s", roll, name, age, grade); }
    }

    static class StudentList {
        Student head; int size;

        void addFirst(Student s) { s.next = head; head = s; size++; }

        void addLast(Student s) {
            if (head == null) head = s;
            else { Student t = head; while (t.next != null) t = t.next; t.next = s; }
            size++;
        }

        boolean addAt(int pos, Student s) {
            if (pos < 1 || pos > size + 1) return false;
            if (pos == 1) { addFirst(s); return true; }
            Student t = head;
            for (int i = 1; i < pos - 1; i++) t = t.next;
            s.next = t.next; t.next = s; size++;
            return true;
        }

        boolean delete(int roll) {
            if (head == null) return false;                       // null check
            if (head.roll == roll) { Student d = head; head = head.next; d.next = null; size--; return true; }
            Student t = head;
            while (t.next != null && t.next.roll != roll) t = t.next;
            if (t.next == null) return false;
            Student d = t.next; t.next = d.next; d.next = null; size--;
            return true;
        }

        Student search(int roll) { for (Student t = head; t != null; t = t.next) if (t.roll == roll) return t; return null; }

        boolean updateGrade(int roll, String grade) { Student s = search(roll); if (s == null) return false; s.grade = grade; return true; }

        void display() {
            if (head == null) { System.out.println("(no records)"); return; }
            for (Student t = head; t != null; t = t.next) System.out.println("  " + t);
        }
    }

    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.addLast(new Student(102, "Asha", 20, "A"));
        list.addFirst(new Student(101, "Ravi", 19, "B"));
        list.addLast(new Student(104, "Meera", 21, "A"));
        list.addAt(3, new Student(103, "Karan", 20, "C"));
        System.out.println("After inserts:"); list.display();

        System.out.println("\nSearch 103: " + list.search(103));
        System.out.println("Search 999: " + list.search(999));

        list.updateGrade(103, "A+");
        System.out.println("\nAfter updating grade of 103:"); list.display();

        System.out.println("\nDelete 101 (head): " + list.delete(101));
        System.out.println("Delete 104 (tail): " + list.delete(104));
        System.out.println("Delete 555 (missing): " + list.delete(555));
        list.display();
    }
}
