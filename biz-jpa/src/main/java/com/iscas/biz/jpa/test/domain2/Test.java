package com.iscas.biz.jpa.test.domain2;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2019/5/16 16:27
 * @since jdk1.8
 */
@Entity
@Table
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
