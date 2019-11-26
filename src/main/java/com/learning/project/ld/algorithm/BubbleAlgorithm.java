package com.learning.project.ld.algorithm;

/**
 * 冒泡排序算法
 * @author lvdong
 * date 2019-11-26
 */
public class BubbleAlgorithm {
    static int[] arr = new int[]{1,2,4,7,5,3,9};

    public static void main(String[] args){
        bubbleArray();
    }

    public static void bubbleArray(){
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] =temp;
                }
            }
        }

        for(int i:arr){
            System.out.println(i);
        }

    }
}
