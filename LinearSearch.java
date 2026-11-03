public class LinearSearch {
    public static int search(int[] arr, int target) {
        for(int i=0; i<arr.length; i++) {
            if(arr[i] == target) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {8, 61, 2, 3, 4, 0};
        System.out.println(search(arr, 8));
        System.out.println(search(arr, 0));
        System.out.println(search(arr, 5));
    }
}
