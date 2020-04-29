package com.iscas.algorithm.sort;

import java.util.List;

/**
 * 冒泡排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 17:53
 * @since jdk1.8
 */
public class BubbleSortUtils {
    /**
     * 冒泡排序
     * */
    public static void sort(int[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j] > a[j + 1];
                } else {
                    condition = a[j] <= a[j + 1];
                }
                if(condition){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * */
    public static void sort(float[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j] > a[j + 1];
                } else {
                    condition = a[j] <= a[j + 1];
                }
                if(condition){
                    float temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * */
    public static void sort(double[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j] > a[j + 1];
                } else {
                    condition = a[j] <= a[j + 1];
                }
                if(condition){
                    double temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * */
    public static void sort(byte[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j] > a[j + 1];
                } else {
                    condition = a[j] <= a[j + 1];
                }
                if(condition){
                    byte temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     * 冒泡排序
     * */
    public static void sort(short[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j] > a[j + 1];
                } else {
                    condition = a[j] <= a[j + 1];
                }
                if(condition){
                    short temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

    /**
     *
     * 冒泡排序集合
     * */
    public static <T extends Comparable> void sort(List<T> a, int order){
        int len=a.size();
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a.get(j).compareTo(a.get(j+2))> 0;
                } else {
                    condition = a.get(j).compareTo(a.get(j+2)) <= 0;
                }
                if(condition){
                    T temp=a.get(j);
                    a.set(j, a.get(j + 1));
                    a.set(j + 1, temp);
                }
            }
        }

    }

    /**
     * 冒泡排序
     * */
    public static <T extends Comparable> void sort(T[] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){
            for(int j=0;j<len-i-1;j++){//注意第二重循环的条件
                boolean condition = false;
                if (order <= 0) {
                    condition = a[j].compareTo(a[j + 1]) > 0;
                } else {
                    condition = a[j].compareTo(a[j + 1]) <= 0;
                }
                if(condition){
                    T temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
    }

}
