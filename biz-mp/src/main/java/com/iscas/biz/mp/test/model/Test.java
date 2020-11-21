package com.iscas.biz.mp.test.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *
 * @author zhuquanwen
 * @vesion 1.0
 * @date 2020/3/26 13:46
 * @since jdk1.8
 */
@Data
public class Test {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
}
