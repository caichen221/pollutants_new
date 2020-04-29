package com.iscas.biz.mp.test.model;

import com.iscas.biz.mp.test.model.enums.SexEnum;
import lombok.Data;

/**
 * //TODO
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/4/5 15:22
 * @since jdk1.8
 */
@Data
public class TestEntity {
    private String name;

    private SexEnum sex;
}
