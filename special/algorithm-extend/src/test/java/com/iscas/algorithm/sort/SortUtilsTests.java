package com.iscas.algorithm.sort;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 排序测试
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 11:06
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class SortUtilsTests {
    private int[] array;
    private List list;

    @Before
    public void getArray() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        array = new int[100];
        for (int i = 0; i < 100; i++) {
            array[i] = -Math.abs(localRandom.nextInt());
        }
    }

    @Before
    public void getList() {
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(Math.abs(localRandom.nextInt()));
        }
    }

    public int[] copyArray(int[] array) {
        int[] newArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public List copyList(List list) {
        List newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    @Test
    public void test() {
        System.out.println("=========直接插入排序-数组===========");
        int[] array1 = copyArray(array);
        long start = System.currentTimeMillis();
        InsertSortUtils.sort(array1, 1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");

//        System.out.println("=========直接插入排序-集合===========");
//        List<Integer> list1 = copyList(list);
//        long start2 = System.currentTimeMillis();
//        InsertSortUtils.sort(list1, 1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start2) + "ms");

        System.out.println("=========希尔排序-数组===========");
        int[] array2 = copyArray(array);
        long start3 = System.currentTimeMillis();
        SheelSortUtils.sort(array2, 1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start3) + "ms");

//        System.out.println("=========希尔排序-集合===========");
//        List<Integer> list2 = copyList(list);
//        long start4 = System.currentTimeMillis();
//        SheelSortUtils.sort(list2, 1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start4) + "ms");

        System.out.println("=========简单选择-数组===========");
        int[] array3 = copyArray(array);
        long start5 = System.currentTimeMillis();
        SelectSortUtils.sort(array3, -1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start5) + "ms");

//        System.out.println("=========简单选择排序-集合===========");
//        List<Integer> list3 = copyList(list);
//        long start6 = System.currentTimeMillis();
//        SheelSortUtils.sort(list3, 1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start6) + "ms");

        System.out.println("=========堆排序-数组===========");
        int[] array4 = copyArray(array);
        long start7 = System.currentTimeMillis();
        HeapSortUtils.sort(array4, -1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start7) + "ms");

//        System.out.println("=========堆排序-集合===========");
//        List<Integer> list4 = copyList(list);
//        long start8 = System.currentTimeMillis();
//        HeapSortUtils.sort(list4, 1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start8) + "ms");

        System.out.println("=========冒泡排序-数组===========");
        int[] array5 = copyArray(array);
        long start9 = System.currentTimeMillis();
        BubbleSortUtils.sort(array5, -1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start9) + "ms");

//        System.out.println("=========冒泡排序-集合===========");
//        List<Integer> list5 = copyList(list);
//        long start10 = System.currentTimeMillis();
//        HeapSortUtils.sort(list5, 1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start10) + "ms");

        System.out.println("=========快速排序-数组===========");
        int[] array6 = copyArray(array);
        long start11 = System.currentTimeMillis();
        QuickSortUtils.sort(array6, 0, array6.length - 1, 1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start11) + "ms");


//        System.out.println("=========快速排序-集合===========");
//        List<Integer> list6 = copyList(list);
//        long start12 = System.currentTimeMillis();
//        QuickSortUtils.sort(list6, 0, list6.size() - 1,  1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start12) + "ms");
//        System.out.println(list6);

        System.out.println("=========归并排序-数组===========");
        int[] array7 = copyArray(array);
        long start13 = System.currentTimeMillis();
        MergeSortUtils.sort(array7, 0, array7.length - 1, 1);
        System.out.println("耗时:" + (System.currentTimeMillis() - start13) + "ms");

//        System.out.println("=========归并排序-集合===========");
//        List<Integer> list7 = copyList(list);
//        long start14 = System.currentTimeMillis();
//        QuickSortUtils.sort(list7, 0, list7.size() - 1, -1);
//        System.out.println("耗时:" + (System.currentTimeMillis() - start14) + "ms");
//        System.out.println(list7);

        System.out.println("=========基数排序-数组===========");
        int[] array8 = copyArray(array);
        long start15 = System.currentTimeMillis();
        BaseSortUtils.sort(array8);
        System.out.println("耗时:" + (System.currentTimeMillis() - start15) + "ms");

        System.out.println("=========基数排序-集合===========");
        List<Integer> list8 = copyList(list);
        long start14 = System.currentTimeMillis();
        BaseSortUtils.sort(list8);
        System.out.println("耗时:" + (System.currentTimeMillis() - start14) + "ms");
    }
}
