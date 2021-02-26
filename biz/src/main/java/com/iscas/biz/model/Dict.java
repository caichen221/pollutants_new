package com.iscas.biz.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lirenshen
 * @vesion 1.0
 * @date 2021/2/25 16:09
 * @since jdk1.8
 */
@Data
@Accessors(chain = true)
public class Dict {

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private long id;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

}
