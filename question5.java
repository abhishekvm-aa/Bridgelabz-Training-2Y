import java.util.Arrays;

public class MergeSort {
    public static void mergeSort(int[] arr, int l, int r) {
        if(l < r) {
            int mid = (l + r) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid+1, r);
            merge(arr, l, mid, r);
        }
    }

    public static void merge(int[] arr, int l, int mid, int r) {
        int[] left = Arrays.copyOfRange(arr, l, mid+1);
        int[] right = Arrays.copyOfRange(arr, mid+1, r+1);

        int i=0, j=0, k=l;
        while(i < left.length && j < right.length) {
            if(left[i] <= right[j]) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while(i < left.length) arr[k++] = left[i++];
        while(j < right.length) arr[k++] = right[j++];
    }

    public static void main(String[] args) {
        int[] arr = {8, 61, 2, 3, 4, 0};
        mergeSort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));
    }
}
