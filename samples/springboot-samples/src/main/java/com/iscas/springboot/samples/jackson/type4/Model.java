package com.iscas.springboot.samples.jackson.type4;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Model {
    private Integer id;
    private int age;
    //调整序列化的名称
    @JsonProperty("myName")
    private String name;
    private Date createTime;
}
