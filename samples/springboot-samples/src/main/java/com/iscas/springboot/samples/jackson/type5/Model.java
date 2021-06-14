package com.iscas.springboot.samples.jackson.type5;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonInclude(value= JsonInclude.Include.NON_NULL)
    private Integer id;
    private int age;
    private String name;
    private Date createTime;
}
