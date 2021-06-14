package com.iscas.springboot.samples.jackson.type3;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2021/6/14 14:06
 * @since jdk1.8
 */
@Data
@Builder
@JsonPropertyOrder(value={"name", "age"})
public class Model {
    private Integer id;
    private int age;
    private String name;
    private Date createTime;
}
