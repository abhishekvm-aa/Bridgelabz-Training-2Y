/** Problem 7: Singly Linked List - Social Media Friend Connections. Friend IDs are a nested singly linked list. */
public class Problem7_FriendConnections {

    static class FriendNode { int id; FriendNode next; FriendNode(int id) { this.id = id; } }

    static class User {
        int id, age, friendCount; String name; FriendNode friends; User next;
        User(int id, String name, int age) { this.id = id; this.name = name; this.age = age; }

        boolean hasFriend(int fid) { for (FriendNode f = friends; f != null; f = f.next) if (f.id == fid) return true; return false; }
        boolean addFriendId(int fid) {
            if (hasFriend(fid)) return false;
            FriendNode n = new FriendNode(fid); n.next = friends; friends = n; friendCount++; return true;
        }
        boolean removeFriendId(int fid) {
            if (friends == null) return false;
            if (friends.id == fid) { friends = friends.next; friendCount--; return true; }
            FriendNode t = friends; while (t.next != null && t.next.id != fid) t = t.next;
            if (t.next == null) return false;
            t.next = t.next.next; friendCount--; return true;
        }
    }

    static class Network {
        User head;

        void addUser(User u) { u.next = head; head = u; }
        User findById(int id) { for (User t = head; t != null; t = t.next) if (t.id == id) return t; return null; }
        User findByName(String n) { for (User t = head; t != null; t = t.next) if (t.name.equalsIgnoreCase(n)) return t; return null; }

        boolean addConnection(int a, int b) {
            User x = findById(a), y = findById(b);
            if (x == null || y == null || a == b) return false;
            boolean r1 = x.addFriendId(b), r2 = y.addFriendId(a);
            return r1 || r2;
        }
        boolean removeConnection(int a, int b) {
            User x = findById(a), y = findById(b);
            if (x == null || y == null) return false;
            boolean r1 = x.removeFriendId(b), r2 = y.removeFriendId(a);
            return r1 || r2;
        }
        void mutualFriends(int a, int b) {
            User x = findById(a), y = findById(b);
            if (x == null || y == null) { System.out.println("  user not found"); return; }
            StringBuilder sb = new StringBuilder(); int c = 0;
            for (FriendNode f = x.friends; f != null; f = f.next)
                if (y.hasFriend(f.id)) { sb.append(findById(f.id).name).append(" (").append(f.id).append(") "); c++; }
            System.out.println("  Mutual friends of " + x.name + " & " + y.name + ": " + (c == 0 ? "none" : sb.toString().trim()));
        }
        void displayFriends(int id) {
            User u = findById(id);
            if (u == null) { System.out.println("  user not found"); return; }
            System.out.print("  Friends of " + u.name + ": ");
            if (u.friends == null) System.out.print("none");
            for (FriendNode f = u.friends; f != null; f = f.next) System.out.print(findById(f.id).name + "(" + f.id + ") ");
            System.out.println();
        }
        void countAll() { for (User t = head; t != null; t = t.next) System.out.println("  " + t.name + " (" + t.id + "): " + t.friendCount + " friends"); }
    }

    public static void main(String[] args) {
        Network n = new Network();
        n.addUser(new User(1, "Asha", 21)); n.addUser(new User(2, "Ravi", 22));
        n.addUser(new User(3, "Meera", 20)); n.addUser(new User(4, "Karan", 23));

        n.addConnection(1, 2); n.addConnection(1, 3); n.addConnection(2, 3); n.addConnection(2, 4);
        System.out.println("Friends:"); n.displayFriends(1); n.displayFriends(2); n.displayFriends(4);

        System.out.println("\nMutual friends:"); n.mutualFriends(1, 2); n.mutualFriends(1, 4);

        User s1 = n.findByName("meera"), s2 = n.findById(4);
        System.out.println("\nSearch by name 'meera': " + (s1 == null ? "not found" : s1.name + ", age " + s1.age));
        System.out.println("Search by ID 4: " + (s2 == null ? "not found" : s2.name + ", age " + s2.age));

        System.out.println("\nCounts:"); n.countAll();
        System.out.println("\nRemove 2-3: " + n.removeConnection(2, 3));
        n.mutualFriends(1, 2); n.countAll();
    }
}
