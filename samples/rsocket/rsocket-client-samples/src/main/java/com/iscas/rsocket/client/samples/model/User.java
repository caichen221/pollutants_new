package com.iscas.rsocket.client.samples.model;

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
    private Integer id;
    private String name;
    private int age;
}
