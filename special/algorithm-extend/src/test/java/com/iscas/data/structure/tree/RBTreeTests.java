package com.iscas.data.structure.tree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/25 10:37
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class RBTreeTests {
    @Test
    public void test() {
        System.out.println("===============插入数据===============");
        RBTree<Integer> rbTree = new RBTree<>();
        rbTree.add(1).add(2).add(-1).add(-102).add(23);

        System.out.println("=================打印红黑树===================");
        rbTree.print();

//        System.out.println("==================清空红黑树=================");
//        rbTree.clear();
//        rbTree.print();

        System.out.println("=====================前序遍历======================");
        rbTree.preOrder((t) -> {
            System.out.println(t);
        });
        System.out.println("=====================中序遍历======================");
        rbTree.inOrder((t) -> {
            System.out.println(t);
        });
        System.out.println("=====================后序遍历======================");
        rbTree.postOrder((t) -> {
            System.out.println(t);
        });

        System.out.println("=====================查找树中某个元素=========================");
        RBTree<Integer>.RBTNode<Integer> node = rbTree.search(1);
        System.out.println(node);

        System.out.println("=====================查找树中某个元素，非递归实现=========================");
        RBTree<Integer>.RBTNode<Integer> node2 = rbTree.iterativeSearch(1);
        System.out.println(node2);

        System.out.println("=====================查找树中最小的元素=========================");
        Integer minimum = rbTree.minimum();
        System.out.println(minimum);

        System.out.println("=====================查找树中最大的元素=========================");
        Integer maximum = rbTree.maximum();
        System.out.println(maximum);

        System.out.println("=====================删除树中的节点=========================");
        rbTree.remove(2);
        rbTree.print();

    }

    @Test
    public void test2() {
        System.out.println("==============测试插入时间1=================");
        BSTree<Integer> bsTree = new BSTree<>();
        ThreadLocalRandom localRandom = ThreadLocalRandom.current();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            bsTree.add(localRandom.nextInt());
        }
        System.out.println("耗时：" + (System.currentTimeMillis()-start) + "ms");

        System.out.println("==============测试插入时间2=================");
        RBTree<Integer> rbTree = new RBTree<>();
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            rbTree.add(localRandom.nextInt());
        }
        System.out.println("耗时：" + (System.currentTimeMillis()-start2) + "ms");

        System.out.println("============测试遍历================");
        long start3 = System.currentTimeMillis();
        bsTree.preOrder((e) -> {

        });
        System.out.println("耗时：" + (System.currentTimeMillis() - start3) + "ms");

        long start4 = System.currentTimeMillis();
        rbTree.preOrder((e) -> {

        });
        System.out.println("耗时：" + (System.currentTimeMillis() - start4) + "ms");

        System.out.println("===============测试查询==============");
        long start5 = System.currentTimeMillis();
        for (int i = 0; i < 5000000 ; i++) {
            bsTree.contains(1);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start5) + "ms");

        long start6 = System.currentTimeMillis();
        for (int i = 0; i < 5000000 ; i++) {
            rbTree.contains(1);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start6) + "ms");

        long start7 = System.currentTimeMillis();
        for (int i = 0; i < 5000000 ; i++) {
            rbTree.iterativeContains(1);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start7) + "ms");

    }
}