package com.iscas.algorithm.sort;

import java.util.List;

/**
 * 快速排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 17:53
 * @since jdk1.8
 */
public class QuickSortUtils {
    /**
     * 快速排序
     * */
    public static void sort(int[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                int baseNum=a[start];//选基准值
                int midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            int key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j] <= key && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i] >= key && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

    /**
     * 快速排序
     * */
    public static void sort(float[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                float baseNum=a[start];//选基准值
                float midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            float key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j] <= key && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i] >= key && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    float temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

    /**
     * 快速排序
     * */
    public static void sort(double[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                double baseNum=a[start];//选基准值
                double midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            double key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j] <= key && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i] >= key && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    double temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

    /**
     * 快速排序
     * */
    public static void sort(byte[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                byte baseNum=a[start];//选基准值
                byte midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            byte key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j] <= key && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i] >= key && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    byte temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

    /**
     * 快速排序
     * */
    public static void sort(short[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                short baseNum=a[start];//选基准值
                short midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            short key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j] <= key && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i] >= key && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    short temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }



    /**
     *
     * 快速排序集合
     * */
    public static <T extends Comparable> void sort(List<T> a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                T baseNum=a.get(start);//选基准值
                T midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a.get(i).compareTo(baseNum) < 0) && i < end) {
                        i++;
                    }
                    while ((a.get(j).compareTo(baseNum) > 0) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a.get(i);
                        a.set(i, a.get(j));
                        a.set(j, midNum);
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            T key = a.get(start);
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a.get(j).compareTo(key) <= 0 && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a.get(i).compareTo(key) >= 0 && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    T temp = a.get(i);
                    a.set(i, a.get(j));
                    a.set(j, temp);
                }
            }
            a.set(start, a.get(i));
            a.set(i, key);
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

    /**
     * 快速排序
     * */
    public static <T extends Comparable> void sort(T[] a, int start, int end, int order){
        if (order <= 0) {
            if(start<end){
                T baseNum=a[start];//选基准值
                T midNum;//记录中间值
                int i=start;
                int j=end;
                do{
                    while ((a[i].compareTo(baseNum) < 0) && i < end) {
                        i++;
                    }
                    while ((a[j].compareTo(baseNum) > 0) && j > start) {
                        j--;
                    }
                    if(i <= j){
                        midNum=a[i];
                        a[i]=a[j];
                        a[j]=midNum;
                        i++;
                        j--;
                    }
                }while(i<=j);
                if(start<j){
                    sort(a,start,j, order);
                }
                if(end>i){
                    sort(a,i,end, order);
                }
            }
        } else {
            if (start >= end) {
                return;
            }
            T key = a[start];
            int i = start;
            int j = end;
            while (i < j) {
                //j向左移，直到遇到比key大的值
                while (a[j].compareTo(key) <= 0 && i < j) {
                    j--;
                }
                //i向右移，直到遇到比key小的值
                while (a[i].compareTo(key) >= 0 && i < j) {
                    i++;
                }
                if (i < j) {//互换
                    T temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
            a[start] = a[i];
            a[i] = key;
            sort(a, start, i - 1, order);
            sort(a, i + 1, end, order);
        }

    }

}
