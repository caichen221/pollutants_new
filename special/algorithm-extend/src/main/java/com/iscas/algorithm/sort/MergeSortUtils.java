package com.iscas.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 归并排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 17:53
 * @since jdk1.8
 */
public class MergeSortUtils {
    /**
     * 归并排序
     * */
    public static void sort(int[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static void merge(int[] data, int p, int q, int r, int order) {
        int[] B = new int[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s] <= data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s] > data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }

    /**
     * 归并排序
     * */
    public static void sort(float[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static void merge(float[] data, int p, int q, int r, int order) {
        float[] B = new float[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s] <= data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s] > data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }


    /**
     * 归并排序
     * */
    public static void sort(double[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static void merge(double[] data, int p, int q, int r, int order) {
        double[] B = new double[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s] <= data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s] > data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }

    /**
     * 归并排序
     * */
    public static void sort(byte[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static void merge(byte[] data, int p, int q, int r, int order) {
        byte[] B = new byte[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s] <= data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s] > data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }

    /**
     * 归并排序
     * */
    public static void sort(short[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static void merge(short[] data, int p, int q, int r, int order) {
        short[] B = new short[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s] <= data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s] > data[t]) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = B[i];
        }
    }
    /**
     *
     * 冒泡排序集合
     * */
    public static <T extends Comparable> void sort(List<T> a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static <T extends Comparable> void merge(List<T> data, int p, int q, int r, int order) {
        List<T> B = new ArrayList<>(data.size());
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data.get(s).compareTo(data.get(t)) <= 0) {
                    B.set(k, data.get(s));
                    s++;
                } else {
                    B.set(k, data.get(t));
                    t++;
                }
            } else {
                if (data.get(s).compareTo(t) > 0) {
                    B.set(k, data.get(s));
                    s++;
                } else {
                    B.set(k, data.get(t));
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B.set(k++, data.get(t++));
        } else {
            B.set(k++, data.get(s++));
        }
        for (int i = p; i <= r; i++) {
            data.set(i, B.get(i));
        }
    }

    /**
     * 归并排序
     * */
    public static <T extends Comparable> void sort(T[] a, int left, int right, int order){
        int t = 1;// 每组元素个数
        int size = right - left + 1;
        while (t < size) {
            int s = t;// 本次循环每组元素个数
            t = 2 * s;
            int i = left;
            while (i + (t - 1) < size) {
                merge(a, i, i + (s - 1), i + (t - 1), order);
                i += t;
            }
            if (i + (s - 1) < right) {
                merge(a, i, i + (s - 1), right, order);
            }
        }
    }

    private static  <T extends Comparable> void merge(T[] data, int p, int q, int r, int order) {
        Object[] B = new Object[data.length];
        int s = p;
        int t = q + 1;
        int k = p;
        while (s <= q && t <= r) {
            if (order <= 0) {
                if (data[s].compareTo(data[t]) <= 0) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            } else {
                if (data[s].compareTo(data[t]) > 0) {
                    B[k] = data[s];
                    s++;
                } else {
                    B[k] = data[t];
                    t++;
                }
            }

            k++;
        }
        if (s == q + 1) {
            B[k++] = data[t++];
        } else {
            B[k++] = data[s++];
        }
        for (int i = p; i <= r; i++) {
            data[i] = (T) B[i];
        }
    }

}
