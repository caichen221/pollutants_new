package com.iscas.data.structure.tree;

import com.iscas.data.structure.tree.bplus.BPlusTree_old;
import com.iscas.data.structure.tree.bplus.BplusTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

/**
 * B+树测试
 * 与hashmap对比插入数据hashmap要快，快一倍多
 *
 * Hash操作能根据散列值直接定位数据的存储地址，设计良好的hash表能在常数级时间下找到需要的数据，但是更适合于内存中的查找。
 *
 * B+树是一种是一种树状的数据结构，适合做索引，对磁盘数据来说，索引查找是比较高效的
 *
 * STL_Map的内部实现是一颗红黑树，但是只是一颗在内存中建立二叉树树，不能用于磁盘操作，而其内存查找性能也比不上Hash查找。
 *
 * 因此对于内存中数据，查找性能较好的数据结构是Hash_Map，对于磁盘中数据，查找性能较好的数据结构是B+Tree。
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 9:15
 * @since jdk1.8
 */
@RunWith(JUnit4.class)
public class BPlusTreeTests {
    static class User implements Comparable{
        private Integer id;
        private String name;
        private String password;

        public User() {
        }

        public User(Integer id, String name, String password) {
            this.id = id;
            this.name = name;
            this.password = password;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Object o) {
            return this.compareTo(o);
        }
    }

    @Test
    public void test() {
        BPlusTree_old<User, Integer> bPlusTree = new BPlusTree_old<>();
        for (int i = 0; i < 5000; i++) {
            User user = new User(i + 1,"zhangsan" + i, "zs" + i);
            bPlusTree.put(user, i + 1);
        }
        User user = bPlusTree.find(1);
        System.out.println(user);
    }

    @Test
    public void test2() {
        BplusTree tree = new BplusTree(6);
//        Random random = new Random();
//        long current = System.currentTimeMillis();
//        for (int j = 0; j < 100000; j++) {
//            for (int i = 0; i < 100; i++) {
//                int randomNumber = random.nextInt(1000);
//                tree.insertOrUpdate(randomNumber, randomNumber);
//            }
//
//            for (int i = 0; i < 100; i++) {
//                int randomNumber = random.nextInt(1000);
//                tree.remove(randomNumber);
//            }
//        }
//
//        long duration = System.currentTimeMillis() - current;
//        System.out.println("time elpsed for duration: " + duration);
//        int search = 80;
//        System.out.print(tree.get(search));

        for (int i = 0; i < 10000; i++) {
            User user = new User(i + 1,"zhangsan" + i, "zs" + i);
            tree.insertOrUpdate(i + 1, user);
        }
        Object o = tree.get(1);
        System.out.println(o);
        tree.insertOrUpdate(1, new User(1, "lisi", "lisi"));
        Object o2 = tree.get(1);
        System.out.println(o2);
    }

    @Test
    public void test3() {
        System.out.println("===========bplusTree插入============");
        long start = System.currentTimeMillis();
        BplusTree tree = new BplusTree(6);
        for (int i = 0; i < 1000000; i++) {
            User user = new User(i + 1,"zhangsan" + i, "zs" + i);
            tree.insertOrUpdate(i + 1, user);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start) + "ms");

        System.out.println("================hashmap插入===================");
        long start2 = System.currentTimeMillis();
        Map map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            User user = new User(i + 1,"zhangsan" + i, "zs" + i);
            map.put(i + 1, user);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start2) + "ms");


        System.out.println("==============测试B+树查询===============");
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Object o = tree.get(800);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start3) + "ms");

        System.out.println("==============测试HashMap查询===============");
        long start4 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Object o = tree.get(800);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - start4) + "ms");

    }
}
