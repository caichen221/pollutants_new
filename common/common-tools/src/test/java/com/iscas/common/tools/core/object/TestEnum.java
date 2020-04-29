package com.iscas.common.tools.core.object;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/18 15:53
 * @since jdk1.8
 */
public enum TestEnum {
    A(1, "xxx"),
    B(2, "yyy");
    private int val;
    private String desc;

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
    TestEnum(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }
}
