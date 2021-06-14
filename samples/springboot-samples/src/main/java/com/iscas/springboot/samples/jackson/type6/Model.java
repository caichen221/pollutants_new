package com.iscas.springboot.samples.jackson.type6;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Model {
    @JsonIgnore
    private Integer id;
    private int age;
    private String name;
    private Date createTime;
}
