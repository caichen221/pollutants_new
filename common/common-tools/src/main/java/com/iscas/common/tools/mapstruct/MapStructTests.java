package com.iscas.common.tools.mapstruct;

import java.util.Arrays;
import java.util.Date;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/9/9 17:13
 * @since jdk1.8
 */

public class MapStructTests {
    static TestUserDTO testUser = null;

    public static void before() {
        testUser = new TestUserDTO()
                .setId(1)
                .setPassword("123456")
                .setUsername("zhangsan")
                .setEmail("123@456.com")
                .setPhone("18598745145")
                .setCreateTime(new Date())
                .setRealName("张三")
                .setRoles(Arrays.asList(new TestRoleDTO().setId(1).setName("管理员"),
                        new TestRoleDTO().setId(2).setName("普通用户")));
    }

    public static void main(String[] args) {
        before();
        TestUserVO1 testUserVO1 = UserConverter.INSTANCE.toTestUserVO1(testUser);
        System.out.println(testUserVO1);
    }
    public void test() {
        TestUserVO1 testUserVO1 = UserConverter.INSTANCE.toTestUserVO1(testUser);
        System.out.println(testUserVO1);
    }
}
