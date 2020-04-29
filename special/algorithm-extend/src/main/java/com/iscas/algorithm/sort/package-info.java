/**
 * 一、稳定性:
 *
 * 　   稳定：冒泡排序、插入排序、归并排序和基数排序
 *
 * 　　不稳定：选择排序、快速排序、希尔排序、堆排序
 *
 * 二、平均时间复杂度
 *
 * 　　O(n^2):直接插入排序，简单选择排序，冒泡排序。
 *
 * 　　在数据规模较小时（9W内），直接插入排序，简单选择排序差不多。当数据较大时，冒泡排序算法的时间代价最高。性能为O(n^2)的算法基本上是相邻元素进行比较，基本上都是稳定的。
 *
 * 　　O(nlogn):快速排序，归并排序，希尔排序，堆排序。
 *
 * 　　其中，快排是最好的， 其次是归并和希尔，堆排序在数据量很大时效果明显。
 *
 * 三、排序算法的选择
 *
 * 　　1.数据规模较小
 *
 *   　　（1）待排序列基本序的情况下，可以选择直接插入排序；
 *
 *   　　（2）对稳定性不作要求宜用简单选择排序，对稳定性有要求宜用插入或冒泡
 *
 * 　　2.数据规模不是很大
 *
 * 　　（1）完全可以用内存空间，序列杂乱无序，对稳定性没有要求，快速排序，此时要付出log（N）的额外空间。
 *
 * 　　（2）序列本身可能有序，对稳定性有要求，空间允许下，宜用归并排序
 *
 * 　　3.数据规模很大
 *
 *    　　（1）对稳定性有求，则可考虑归并排序。
 *
 *     　　（2）对稳定性没要求，宜用堆排序
 *
 * 　　4.序列初始基本有序（正序），宜用直接插入，冒泡
 *
 *
 * @vesion 1.0
 * @author zhuquanwen
 * @date 2019/7/2 14:41
 * @since jdk1.8
 */
package com.iscas.algorithm.sort;