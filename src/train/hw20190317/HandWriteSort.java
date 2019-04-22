package train.hw20190317;

/**
 * @Author: Mr.Xu
 * @Date: Created in 11:05 2019/3/23
 * @Description:
 */
public class HandWriteSort {
    public static void bubbleSort(int[] a){
        for(int i = 0  ; i < a.length-1 ; i++){
            for(int j = 0; j < a.length-1-i; j ++){
                if(a[j] > a[j+1]) swap(a,j,j+1);
            }
        }
    }
    public static void selectSort(int[] a){
        for(int i = 0; i < a.length-1; i++){
            int k = i;
            for(int j = k+1;j < a.length; j++) {
                if (a[j] < a[k]){
                   k = j;
                }
            }
            swap(a,i,k);
        }
    }
    public static void insertionSort(int[] a){
        int j = 0;
        for(int i = 1 ; i < a.length ; i++){
            int temp = a[i];
            for(j = i; j > 0 && a[j-1] > temp; j--){
                a[j] = a[j-1];
            }
            a[j] = temp;
        }
    }
    public static void shellSort(int[] a){
        int j = 0,h = 1, N = a.length;
        while(h < N/3) h = 3 * h + 1;
        while(h >= 1){
            for(int i = h; i < N; i++){
                int tmp = a[i];
                for(j = i; j >= h && a[j-h] >tmp; j -= h){
                    a[j] = a[j-h];
                }
                a[j] = tmp;
            }
            h /= 3;
        }
    }
    public static void heapSort(int[] a){
        int n = a.length;
        for(int i = (n-1)/2; i >= 0; i--) sink(a,i,n);
        while(n > 0){
            swap(a,0,n--);
            sink(a,0,n);
        }
    }
    private static void sink(int[] a, int i, int n){
        while(2 * i <= n-1){
            int j = 2 * i;
            if(j < n-1 && a[j] < a[j-1]) j += 1;
            if(a[i] >= a[j]) break;
            swap(a,i,j);
            i = j;
        }
    }
    private static void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
