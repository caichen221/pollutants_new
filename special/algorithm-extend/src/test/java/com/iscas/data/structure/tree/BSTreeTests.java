package com.iscas.data.structure.tree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * 测试二分搜索树
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/21 17:38
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class BSTreeTests {

    @Test
    public void test1() {

        //测试添加节点
        System.out.println("==============测试添加节点===============");
        BSTree<Integer> bsTree = new BSTree<>();
        bsTree.add(4).add(3).add(-1).add(23).add(0);
        System.out.println(bsTree);

        //测试contains
        System.out.println("==============测试包含===============");
        boolean contains = bsTree.contains(-1);
        System.out.println(contains);

        //测试前序遍历,广度优先，递归方式实现
        System.out.println("==============测试递归前序遍历===============");
        bsTree.preOrder((e) -> {
            System.out.println(e);
        });

        //测试前序遍历，广度优先，非递归（使用栈）方式实现
        System.out.println("==============测试非递归前序遍历===============");
        bsTree.preOrderNR((e) -> {
            System.out.println(e);
        });

        //测试中序遍历
        System.out.println("===============测试中序遍历================");
        bsTree.inOrder((e) -> {
            System.out.println(e);
        });

        //测试后序遍历
        System.out.println("===============测试后序遍历================");
        bsTree.postOrder((e) -> {
            System.out.println(e);
        });

        //测试层序遍历
        System.out.println("================测试层序遍历================");
        bsTree.levelOrder((e) -> {
            System.out.println(e);
        });

        //测试查询树中最小的元素
        System.out.println("====================测试查找树中最小的元素==========================");
        Integer minimum = bsTree.minimum();
        System.out.println(minimum);

        //测试查询树中最大的元素
        System.out.println("==================测试查找树中最大的元素========================");
        Integer maximum = bsTree.maximum();
        System.out.println(maximum);

        //测试删除树中最小值
        System.out.println("==================测试删除树中最小值===================");
        Integer removeMin = bsTree.removeMin();
        System.out.println(removeMin);

        //测试删除树中最大值
        System.out.println("==================测试删除树中最大值===================");
        Integer removeMax = bsTree.removeMax();
        System.out.println(removeMax);

        //删除指定属性的节点
        System.out.println("================删除指定属性的节点==============");
        bsTree.remove(23);
        System.out.println(bsTree);
    }
    
    @Test
    public void test2() {
        ThreadLocalRandom threadRandom = ThreadLocalRandom.current();
        System.out.println("==============二分查找树=================");
        long start = System.currentTimeMillis();
        BSTree<Integer> bsTree = new BSTree<>();
        for (int i = 0; i < 500000 ; i++) {
            bsTree.add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt())
                    .add(threadRandom.nextInt());
        }
        System.out.println("耗时：" + (System.currentTimeMillis()- start) + "ms");

        System.out.println("=============HashMap=================");
        long start2 = System.currentTimeMillis();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 500000 ; i++) {
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());
            set.add(threadRandom.nextInt());

        }
        System.out.println("耗时：" + (System.currentTimeMillis()- start2) + "ms");

        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            bsTree.contains(1);
        }
        System.out.println(System.currentTimeMillis()- start3);
        long start4 = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            set.contains(1);
        }
        System.out.println(System.currentTimeMillis()- start4);

        System.out.println("============测试遍历==================");
        long start5 = System.currentTimeMillis();
        bsTree.preOrder(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {

            }
        });
        System.out.println(System.currentTimeMillis() -start5);

        long start6 = System.currentTimeMillis();
        for (Integer integer : set) {

        }
        System.out.println(System.currentTimeMillis() -start6);

    }
}
