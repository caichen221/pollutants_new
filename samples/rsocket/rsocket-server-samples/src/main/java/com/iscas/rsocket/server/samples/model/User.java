package com.iscas.rsocket.server.samples.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/2/6 17:53
 * @since jdk1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static List<User> users = new ArrayList<>();
    static {
        users.add(new User(1, "zhangsan", 12));
        users.add(new User(2, "lisi", 22));
        users.add(new User(3, "wangwu", 25));
    }
    private Integer id;
    private String name;
    private int age;
}
