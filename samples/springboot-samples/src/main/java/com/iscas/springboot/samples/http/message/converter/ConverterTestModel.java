package com.iscas.springboot.samples.http.message.converter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 19:21
 * @since jdk1.8
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConverterTestModel {
    private Integer id;
    private int age;
    private String name;
    private Date createTime;
}
