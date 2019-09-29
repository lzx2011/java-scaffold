package com.lzhenxing.javascaffold.javabase.algorithm.sort;

/**
 * ClassName: QuickSort <br/>
 * Function: QuickSort<br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class QuickSort {


    private static int getMiddle(int[] arr, int low, int high){

        int temp = arr[low];
        while (low < high){
            while (low < high && arr[high] >= temp){
                high--;
            }
            arr[low] = arr[high];
            while (low < high && arr[low] <= temp){
                low++;
            }
            arr[high] = arr[low];
        }
        arr[low] = temp;

        return low;
    }

    private static void quickSort(int[] arr, int low, int high){

        if(low < high){
            int middle = getMiddle(arr, low, high);
            quickSort(arr, 0, middle - 1);
            quickSort(arr, middle + 1, high);
        }
    }


    public static void main(String[] args){

        int[] arr = {2, 5, 1, 4, 8};
        quickSort(arr, 0, arr.length - 1);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
        System.out.println(arr.toString());
        System.out.println(arr);

    }


}
