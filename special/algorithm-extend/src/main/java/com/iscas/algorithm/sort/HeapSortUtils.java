package com.iscas.algorithm.sort;

import java.util.List;

/**
 * 堆排序
 * 参考https://www.cnblogs.com/10158wsj/p/6782124.html?utm_source=tuicool&utm_medium=referral
 *
 * 所有接口中的order 负数代表正序，正数代表倒序
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 17:53
 * @since jdk1.8
 */
public class HeapSortUtils {
    /**
     * 堆排序
     * */
    public static void sort(int[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static void swap(int[] data, int i, int j) {
        int tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(int[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k]<data[biggerIndex];
                } else {
                    condition = data[k]>=data[biggerIndex];
                }
                //如果k节点的值小于其较大的子节点的值
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 堆排序
     * */
    public static void sort(float[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static void swap(float[] data, int i, int j) {
        float tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(float[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k]<data[biggerIndex];
                } else {
                    condition = data[k]>=data[biggerIndex];
                }
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }


    /**
     * 堆排序
     * */
    public static void sort(double[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static void swap(double[] data, int i, int j) {
        double tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(double[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k]<data[biggerIndex];
                } else {
                    condition = data[k]>=data[biggerIndex];
                }
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 堆排序
     * */
    public static void sort(byte[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static void swap(byte[] data, int i, int j) {
        byte tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(byte[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k]<data[biggerIndex];
                } else {
                    condition = data[k]>=data[biggerIndex];
                }
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 堆排序
     * */
    public static void sort(short[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static void swap(short[] data, int i, int j) {
        short tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(short[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex]<data[biggerIndex+1]){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k]<data[biggerIndex];
                } else {
                    condition = data[k]>=data[biggerIndex];
                }
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     *
     * 简单选择排序集合
     * */
    public static <T extends Comparable> void sort(List<T> a, int order){
        int len=a.size();
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }

    }

    //交换方法
    private static <T extends Comparable> void swap(List<T> data, int i, int j) {
        T tmp=data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
    }
    //对data数组从0到lastIndex建大顶堆
    private static <T extends Comparable> void buildMaxHeap(List<T> data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data.get(biggerIndex).compareTo(data.get(biggerIndex + 1))<0){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                boolean condition = false;
                if (order <= 0) {
                    condition = data.get(k).compareTo(data.get(biggerIndex)) < 0;
                } else {
                    condition = data.get(k).compareTo(data.get(biggerIndex)) >= 0;
                }
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }

    /**
     * 堆排序
     * */
    public static <T extends Comparable> void sort(T[] a, int order){
        int len=a.length;
        //循环建堆
        for(int i=0;i<len-1;i++){
            //建堆
            buildMaxHeap(a,len-1-i, order);
            //交换堆顶和最后一个元素
            swap(a,0,len-1-i);
        }
    }
    //交换方法
    private static <T extends Comparable> void swap(T[] data, int i, int j) {
        T tmp=data[i];
        data[i]=data[j];
        data[j]=tmp;
    }
    //对data数组从0到lastIndex建大顶堆
    private static <T extends Comparable> void buildMaxHeap(T[] data, int lastIndex, int order) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for(int i=(lastIndex-1)/2;i>=0;i--){
            //k保存正在判断的节点
            int k=i;
            //如果当前k节点的子节点存在
            while(k*2+1<=lastIndex){
                //k节点的左子节点的索引
                int biggerIndex=2*k+1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if(biggerIndex<lastIndex){
                    //若果右子节点的值较大
                    if(data[biggerIndex].compareTo(data[biggerIndex+1]) < 0){
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                boolean condition = false;
                if (order <= 0) {
                    condition = data[k].compareTo(data[biggerIndex]) < 0;
                } else {
                    condition = data[k].compareTo(data[biggerIndex]) >= 0 ;
                }
                //如果k节点的值小于其较大的子节点的值
                if(condition){
                    //交换他们
                    swap(data,k,biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k=biggerIndex;
                }else{
                    break;
                }
            }
        }
    }
}
