package com.iscas.data.structure.tree.bplus;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/6/26 9:44
 * @since jdk1.8
 */
public interface B {
    /**
     * 查询
     * */
    Object get(Comparable key);

    /**
     * 删除
     * */
    void remove(Comparable key);

    /**
     * 插入或者更新，如果已经存在，就更新，否则插入
     * */
    void insertOrUpdate(Comparable key, Object obj);
}
