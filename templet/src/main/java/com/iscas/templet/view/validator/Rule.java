package com.iscas.templet.view.validator;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhuquanwen
 * @date 2018/1/23 10:22
 **/
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
public class Rule implements Serializable {
    /**
     * 是否必须填写值
     */
    protected boolean required = false;
    /**
     * 正则表达式
     */
    protected String reg;
    /**
     * 长度  min,max
     */
    protected Map<String, Integer> length;
    /**
     * 是否要校验重复值
     */
    protected boolean distinct = false;
    /**
     * 是否包含最大值
     */
    protected boolean containsHigh = false;
    /**
     * 是否包含最小值
     */
    protected boolean containsLow = false;
    protected Object highVal;
    protected Object lowVal;
    /**
     * 提示描述
     */
    protected String desc = null;
}
