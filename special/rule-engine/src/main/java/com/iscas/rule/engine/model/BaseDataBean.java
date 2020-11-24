package com.iscas.rule.engine.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 硬件数据的实体
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/11/24 16:21
 * @since jdk1.8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataBean {
    /**参数的key*/
    private String name;

    /**硬件的名字*/
    private String pname;

    /**硬件的类型*/
    private String type;
}
