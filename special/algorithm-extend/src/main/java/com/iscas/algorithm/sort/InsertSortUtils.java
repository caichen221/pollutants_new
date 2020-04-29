package com.iscas.algorithm.sort;

import java.util.List;

/**
 * 直接插入排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 11:04
 * @since jdk1.8
 */
public class InsertSortUtils {
    private InsertSortUtils() {}

    /**
     *
     * 插入排序, int数组
     * */
    public static void sort(int[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        int insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j]  > insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j] <= insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }

    /**
     *
     * 插入排序, float数组
     * */
    public static void sort(float[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        float insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j]  > insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j] <= insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }

    /**
     *
     * 插入排序, float数组
     * */
    public static void sort(double[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        double insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j]  > insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j] <= insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }

    /**
     *
     * 插入排序, short数组
     * */
    public static void sort(short[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        short insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j]  > insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j] <= insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }

    /**
     *
     * 插入排序, short数组
     * */
    public static void sort(byte[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        byte insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j]  > insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j] <= insertNum) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }

    /**
     *
     * 插入排序集合
     * */
    public static <T> void sort(List<T> a, int order){

        //单独把数组长度拿出来，提高效率
        int len=a.size();
        //要插入的数
        T insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a.get(i);
            //序列元素个数
            int j=i-1;
            //从后往前循环，将大于insertNum的数向后移动
            if (insertNum instanceof Integer) {
                if (order <= 0) {
                    while(j>=0 && (int) a.get(j) > (int) insertNum){
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                } else {
                    while(j>=0 && (int) a.get(j) <= (int) insertNum){
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                }
            } else if (insertNum instanceof Float) {
                if (order <= 0) {
                    while (j >= 0 && (float) a.get(j) > (float) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                } else {
                    while (j >= 0 && (float) a.get(j) <= (float) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                }
            } else if (insertNum instanceof Double) {
                if (order <= 0) {
                    while (j >= 0 && (double) a.get(j) > (double) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                } else {
                    while (j >= 0 && (double) a.get(j) <= (double) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                }
            } else if (insertNum instanceof Byte) {
                if (order <= 0) {
                    while (j >= 0 && (byte) a.get(j) > (byte) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                } else {
                    while (j >= 0 && (byte) a.get(j) <= (byte) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                }
            } else if (insertNum instanceof Short) {
                if (order <= 0) {
                    while (j >= 0 && (short) a.get(j) > (short) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                } else {
                    while (j >= 0 && (short) a.get(j) <= (short) insertNum) {
                        //元素向后移动
                        a.set(j + 1, a.get(j));
                        j--;
                    }
                }
            }

            //找到位置，插入当前元素
            a.set(j + 1, insertNum);
        }
    }

    /**
     *
     * 插入排序, int数组
     * */
    public static <T extends Comparable> void sort(T[] a, int order){
        //单独把数组长度拿出来，提高效率
        int len=a.length;
        //要插入的数
        T insertNum;
        //因为第一次不用，所以从1开始
        for(int i=1;i<len;i++){
            insertNum=a[i];
            //序列元素个数
            int j=i-1;
            if (order <= 0) {
                //从后往前循环，将大于insertNum的数向后移动
                while (j >= 0 && a[j].compareTo(insertNum) > 0) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            } else {
                while (j >= 0 && a[j].compareTo(insertNum) <= 0) {
                    //元素向后移动
                    a[j + 1] = a[j];
                    j--;
                }
            }

            //找到位置，插入当前元素
            a[j + 1] = insertNum;
        }
    }


}
