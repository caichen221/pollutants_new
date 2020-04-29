package com.iscas.algorithm.sort;

import java.util.List;

/**
 * 简单选择排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 17:53
 * @since jdk1.8
 */
public class SelectSortUtils {
    /**
     * 简单选择排序
     * */
    public static void sort(int [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            int value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j] < value;
                } else {
                    conditon = a[j] >= value;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }

    /**
     * 直接选择排序
     * */
    public static void sort(float [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            float value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j] < value;
                } else {
                    conditon = a[j] >= value;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }

    /**
     * 直接选择排序
     * */
    public static void sort(double [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            double value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j] < value;
                } else {
                    conditon = a[j] >= value;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }

    /**
     * 直接选择排序
     * */
    public static void sort(byte [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            byte value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j] < value;
                } else {
                    conditon = a[j] >= value;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }

    /**
     * 直接选择排序
     * */
    public static void sort(short [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            short value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j] < value;
                } else {
                    conditon = a[j] >= value;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }

    /**
     *
     * 简单选择排序集合
     * */
    public static <T extends Comparable> void sort(List<T> a, int order){
        int len=a.size();
        for(int i=0;i<len;i++){//循环次数
            T value=a.get(i);
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a.get(j).compareTo(value) < 0;
                } else {
                    conditon = a.get(j).compareTo(value) >= 0;
                }
                if(conditon){
                    value=a.get(j);
                    position=j;
                }

            }
            a.set(position, a.get(i));//进行交换
            a.set(i, value);
        }

    }

    /**
     * 简单选择排序
     * */
    public static <T extends Comparable> void sort(T [] a, int order){
        int len=a.length;
        for(int i=0;i<len;i++){//循环次数
            T value=a[i];
            int position=i;
            for(int j=i+1;j<len;j++){//找到最小的值和位置
                boolean conditon = false;
                if (order <= 0) {
                    conditon = a[j].compareTo(value) < 0;
                } else {
                    conditon = a[j].compareTo(value) >= 0;
                }
                if(conditon){
                    value=a[j];
                    position=j;
                }

            }
            a[position]=a[i];//进行交换
            a[i]=value;
        }
    }
}
