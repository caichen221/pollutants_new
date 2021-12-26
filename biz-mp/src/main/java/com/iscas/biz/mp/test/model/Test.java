package com.iscas.biz.mp.test.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/26 13:46
 * @since jdk1.8
 */
@Data
@TableName(value = "test", excludeProperty = {"realName"})
public class Test {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer age;

    private String realName;

}
